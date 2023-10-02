package dev.tehbrian.buildersutilities;

import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.minecraft.extras.AudienceProvider;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.tehbrian.buildersutilities.ability.AbilityMenuListener;
import dev.tehbrian.buildersutilities.ability.AdvancedFlyListener;
import dev.tehbrian.buildersutilities.ability.DoubleSlabListener;
import dev.tehbrian.buildersutilities.ability.GlazedTerracottaListener;
import dev.tehbrian.buildersutilities.ability.IronDoorListener;
import dev.tehbrian.buildersutilities.ability.NoclipManager;
import dev.tehbrian.buildersutilities.armorcolor.ArmorColorMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.BaseMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.ColorMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.DoneMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.PatternMenuListener;
import dev.tehbrian.buildersutilities.command.AdvancedFlyCommand;
import dev.tehbrian.buildersutilities.command.ArmorColorCommand;
import dev.tehbrian.buildersutilities.command.BannerCommand;
import dev.tehbrian.buildersutilities.command.BuildersUtilitiesCommand;
import dev.tehbrian.buildersutilities.command.NightVisionCommand;
import dev.tehbrian.buildersutilities.command.NoclipCommand;
import dev.tehbrian.buildersutilities.command.WorldEditAliases;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.config.SpecialConfig;
import dev.tehbrian.buildersutilities.inject.PluginModule;
import dev.tehbrian.buildersutilities.inject.SingletonModule;
import dev.tehbrian.buildersutilities.setting.SettingsListener;
import dev.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;
import dev.tehbrian.restrictionhelper.spigot.SpigotRestrictionLoader;
import dev.tehbrian.restrictionhelper.spigot.restrictions.R_PlotSquared_6_7;
import dev.tehbrian.restrictionhelper.spigot.restrictions.R_WorldGuard_7;
import dev.tehbrian.tehlib.configurate.Config;
import dev.tehbrian.tehlib.paper.TehPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.NodePath;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * The main class for the BuildersUtilities plugin.
 */
public final class BuildersUtilities extends TehPlugin {

  private @MonotonicNonNull PaperCommandManager<CommandSender> commandManager;
  private @MonotonicNonNull Injector injector;

  @Override
  public void onEnable() {
    try {
      this.injector = Guice.createInjector(
          new PluginModule(this),
          new SingletonModule()
      );
    } catch (final Exception e) {
      this.getSLF4JLogger().error("An error occurred while creating the Guice injector.");
      this.getSLF4JLogger().error("Disabling plugin.");
      this.disableSelf();
      this.getSLF4JLogger().error("Printing stack trace. Please send this to the developers:", e);
      return;
    }

    if (!this.loadConfiguration()) {
      this.disableSelf();
      return;
    }
    if (!this.initCommands()) {
      this.disableSelf();
      return;
    }
    this.initListeners();
    this.initRestrictions();

    this.injector.getInstance(NoclipManager.class).start();
  }

  /**
   * Loads the plugin's configuration.
   * <p>
   * If there is an error while loading a config file, the exception is logged
   * and the file is skipped.
   *
   * @return whether all config files were successfully loaded
   */
  public boolean loadConfiguration() {
    this.saveResourceSilently("config.yml");
    this.saveResourceSilently("lang.yml");
    this.saveResourceSilently("special.yml");

    final List<Config> configsToLoad = List.of(
        this.injector.getInstance(ConfigConfig.class),
        this.injector.getInstance(LangConfig.class),
        this.injector.getInstance(SpecialConfig.class)
    );

    boolean wasSuccessful = true;
    for (final Config config : configsToLoad) {
      try {
        config.load();
      } catch (final ConfigurateException e) {
        this.getSLF4JLogger().error(
            "An error occurred while loading config file {}. Please ensure that the file is valid.",
            config.configurateWrapper().filePath()
        );
        this.getSLF4JLogger().error("Printing stack trace:", e);
        wasSuccessful = false;
      }
    }

    if (wasSuccessful) {
      this.getSLF4JLogger().info("Successfully loaded configuration.");
    }
    return wasSuccessful;
  }

  /**
   * @return whether it was successful
   */
  private boolean initCommands() {
    if (this.commandManager != null) {
      throw new IllegalStateException("The CommandManager is already instantiated.");
    }

    try {
      this.commandManager = new PaperCommandManager<>(
          this,
          CommandExecutionCoordinator.simpleCoordinator(),
          Function.identity(),
          Function.identity()
      );
    } catch (final Exception e) {
      this.getSLF4JLogger().error("An error occurred while creating the command manager.");
      this.getSLF4JLogger().error("Printing stack trace. Please send this to the developers:", e);
      return false;
    }

    final Component noPermMessage = this.injector.getInstance(LangConfig.class)
        .c(NodePath.path("commands", "no-permission"));

    final Function<Exception, Component> noPermHandler;
    if (PlainTextComponentSerializer.plainText().serialize(noPermMessage).isEmpty()) {
      noPermHandler = e -> this.getServer().permissionMessage();
    } else {
      noPermHandler = e -> noPermMessage;
    }

    new MinecraftExceptionHandler<CommandSender>()
        .withArgumentParsingHandler()
        .withInvalidSenderHandler()
        .withInvalidSyntaxHandler()
        .withHandler(MinecraftExceptionHandler.ExceptionType.NO_PERMISSION, noPermHandler)
        .withCommandExecutionHandler()
        .apply(this.commandManager, AudienceProvider.nativeAudience());

    this.injector.getInstance(AdvancedFlyCommand.class).register(this.commandManager);
    this.injector.getInstance(ArmorColorCommand.class).register(this.commandManager);
    this.injector.getInstance(BannerCommand.class).register(this.commandManager);
    this.injector.getInstance(BuildersUtilitiesCommand.class).register(this.commandManager);
    this.injector.getInstance(NightVisionCommand.class).register(this.commandManager);
    this.injector.getInstance(NoclipCommand.class).register(this.commandManager);

    if (this.injector.getInstance(ConfigConfig.class).data().worldEditAliases()) {
      final PluginManager pm = this.getServer().getPluginManager();
      if (pm.isPluginEnabled("WorldEdit")) {
        this.injector.getInstance(WorldEditAliases.class).register(this.commandManager, false);
      } else if (pm.isPluginEnabled("FastAsyncWorldEdit")) {
        this.injector.getInstance(WorldEditAliases.class).register(this.commandManager, true);
      } else {
        this.getSLF4JLogger().error("worledit-aliases is enabled in config.yml, but WorldEdit isn't present "
            + "on this server. WorldEdit aliases will not be registered.");
      }
    }

    return true;
  }

  private void initListeners() {
    this.registerListeners(
        this.injector.getInstance(AbilityMenuListener.class),
        this.injector.getInstance(ArmorColorMenuListener.class),

        this.injector.getInstance(BaseMenuListener.class),
        this.injector.getInstance(ColorMenuListener.class),
        this.injector.getInstance(PatternMenuListener.class),
        this.injector.getInstance(DoneMenuListener.class),

        this.injector.getInstance(AdvancedFlyListener.class),
        this.injector.getInstance(DoubleSlabListener.class),
        this.injector.getInstance(GlazedTerracottaListener.class),
        this.injector.getInstance(IronDoorListener.class),
        this.injector.getInstance(SettingsListener.class)
    );
  }

  private void initRestrictions() {
    final var loader = new SpigotRestrictionLoader(
        this.getSLF4JLogger(),
        Arrays.asList(this.getServer().getPluginManager().getPlugins()),
        List.of(R_PlotSquared_6_7.class, R_WorldGuard_7.class)
    );

    loader.load(this.injector.getInstance(SpigotRestrictionHelper.class));
  }

}
