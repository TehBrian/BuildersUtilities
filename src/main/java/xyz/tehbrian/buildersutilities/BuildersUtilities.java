package xyz.tehbrian.buildersutilities;

import cloud.commandframework.minecraft.extras.AudienceProvider;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.tehbrian.tehlib.configurate.Config;
import dev.tehbrian.tehlib.paper.TehPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.ability.AbilityMenuListener;
import xyz.tehbrian.buildersutilities.ability.AdvancedFlyListener;
import xyz.tehbrian.buildersutilities.ability.DoubleSlabListener;
import xyz.tehbrian.buildersutilities.ability.GlazedTerracottaListener;
import xyz.tehbrian.buildersutilities.ability.IronDoorListener;
import xyz.tehbrian.buildersutilities.ability.NoclipManager;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorMenuListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerBaseMenuListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerColorMenuListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerPatternMenuListener;
import xyz.tehbrian.buildersutilities.command.AdvancedFlyCommand;
import xyz.tehbrian.buildersutilities.command.ArmorColorCommand;
import xyz.tehbrian.buildersutilities.command.BannerCommand;
import xyz.tehbrian.buildersutilities.command.BuildersUtilitiesCommand;
import xyz.tehbrian.buildersutilities.command.CommandService;
import xyz.tehbrian.buildersutilities.command.NightVisionCommand;
import xyz.tehbrian.buildersutilities.command.NoclipCommand;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.config.SpecialConfig;
import xyz.tehbrian.buildersutilities.inject.PluginModule;
import xyz.tehbrian.buildersutilities.inject.SingletonModule;
import xyz.tehbrian.buildersutilities.setting.SettingsListener;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionLoader;
import xyz.tehbrian.restrictionhelper.spigot.restrictions.R_PlotSquared_6;
import xyz.tehbrian.restrictionhelper.spigot.restrictions.R_WorldGuard_7;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * The main class for the BuildersUtilities plugin.
 */
public final class BuildersUtilities extends TehPlugin {

  /**
   * The Guice injector.
   */
  private @MonotonicNonNull Injector injector;

  @Override
  public void onEnable() {
    try {
      this.injector = Guice.createInjector(
          new PluginModule(this),
          new SingletonModule()
      );
    } catch (final Exception e) {
      this.getSLF4JLogger().error("Something went wrong while creating the Guice injector.");
      this.getSLF4JLogger().error("Disabling plugin.");
      this.disableSelf();
      this.getSLF4JLogger().error("Printing stack trace, please send this to the developers:", e);
      return;
    }

    if (!this.loadConfiguration()) {
      this.disableSelf();
      return;
    }
    if (!this.setupCommands()) {
      this.disableSelf();
      return;
    }
    this.setupListeners();
    this.setupRestrictions();

    this.injector.getInstance(NoclipManager.class).start();
  }

  /**
   * Loads the plugin's configuration. If an exception is caught, logs the
   * error and returns false.
   *
   * @return whether it was successful
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

    for (final Config config : configsToLoad) {
      try {
        config.load();
      } catch (final ConfigurateException e) {
        this.getSLF4JLogger().error(
            "Exception caught during config load for {}",
            config.configurateWrapper().filePath()
        );
        this.getSLF4JLogger().error("Please check your config.");
        this.getSLF4JLogger().error("Printing stack trace:", e);
        return false;
      }
    }

    this.getSLF4JLogger().info("Successfully loaded configuration.");
    return true;
  }

  private void setupListeners() {
    this.registerListeners(
        this.injector.getInstance(AbilityMenuListener.class),
        this.injector.getInstance(ArmorColorMenuListener.class),
        this.injector.getInstance(BannerBaseMenuListener.class),
        this.injector.getInstance(BannerColorMenuListener.class),
        this.injector.getInstance(BannerPatternMenuListener.class),

        this.injector.getInstance(AdvancedFlyListener.class),
        this.injector.getInstance(DoubleSlabListener.class),
        this.injector.getInstance(GlazedTerracottaListener.class),
        this.injector.getInstance(IronDoorListener.class),
        this.injector.getInstance(SettingsListener.class)
    );
  }

  /**
   * @return whether it was successful
   */
  private boolean setupCommands() {
    final @NonNull CommandService commandService = this.injector.getInstance(CommandService.class);
    try {
      commandService.init();
    } catch (final Exception e) {
      this.getSLF4JLogger().error("Failed to create the CommandManager.");
      this.getSLF4JLogger().error("Printing stack trace, please send this to the developers:", e);
      return false;
    }

    final @Nullable PaperCommandManager<CommandSender> commandManager = commandService.get();
    if (commandManager == null) {
      this.getSLF4JLogger().error("The CommandService was null after initialization!");
      return false;
    }

    final @NonNull Function<@NonNull Exception, @NonNull Component> noPermissionHandler;

    final @NonNull Component configNoPermissionMessage = this.injector.getInstance(LangConfig.class)
        .c(NodePath.path("commands", "no-permission"));

    if (PlainTextComponentSerializer.plainText().serialize(configNoPermissionMessage).isEmpty()) {
      noPermissionHandler = e -> this.getServer().permissionMessage();
    } else {
      noPermissionHandler = e -> configNoPermissionMessage;
    }

    new MinecraftExceptionHandler<CommandSender>()
        .withArgumentParsingHandler()
        .withInvalidSenderHandler()
        .withInvalidSyntaxHandler()
        .withHandler(MinecraftExceptionHandler.ExceptionType.NO_PERMISSION, noPermissionHandler)
        .withCommandExecutionHandler()
        .apply(commandManager, AudienceProvider.nativeAudience());

    this.injector.getInstance(AdvancedFlyCommand.class).register(commandManager);
    this.injector.getInstance(ArmorColorCommand.class).register(commandManager);
    this.injector.getInstance(BannerCommand.class).register(commandManager);
    this.injector.getInstance(BuildersUtilitiesCommand.class).register(commandManager);
    this.injector.getInstance(NightVisionCommand.class).register(commandManager);
    this.injector.getInstance(NoclipCommand.class).register(commandManager);

    return true;
  }

  private void setupRestrictions() {
    final var loader = new SpigotRestrictionLoader(
        this.getSLF4JLogger(),
        Arrays.asList(this.getServer().getPluginManager().getPlugins()),
        List.of(R_PlotSquared_6.class, R_WorldGuard_7.class)
    );

    loader.load(this.injector.getInstance(SpigotRestrictionHelper.class));
  }

}
