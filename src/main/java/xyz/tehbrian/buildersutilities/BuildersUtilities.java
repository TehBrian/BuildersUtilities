package xyz.tehbrian.buildersutilities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.tehbrian.tehlib.core.configurate.Config;
import dev.tehbrian.tehlib.paper.TehPlugin;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurateException;
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
import xyz.tehbrian.buildersutilities.ability.AdvancedFlyListener;
import xyz.tehbrian.buildersutilities.ability.DoubleSlabListener;
import xyz.tehbrian.buildersutilities.ability.GlazedTerracottaListener;
import xyz.tehbrian.buildersutilities.ability.IronDoorListener;
import xyz.tehbrian.buildersutilities.ability.NoclipManager;
import xyz.tehbrian.buildersutilities.ability.AbilityMenuListener;
import xyz.tehbrian.buildersutilities.setting.SettingsListener;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionLoader;
import xyz.tehbrian.restrictionhelper.spigot.restrictions.R_PlotSquared_6;
import xyz.tehbrian.restrictionhelper.spigot.restrictions.R_WorldGuard_7;

import java.util.Arrays;
import java.util.List;

/**
 * The main class for the BuildersUtilities plugin.
 */
public final class BuildersUtilities extends TehPlugin {

    /**
     * The Guice injector.
     */
    private @MonotonicNonNull Injector injector;

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        try {
            this.injector = Guice.createInjector(
                    new SingletonModule(),
                    new PluginModule(this)
            );
        } catch (final Exception e) {
            this.getLog4JLogger().error("Something went wrong while creating the Guice injector.");
            this.getLog4JLogger().error("Disabling plugin.");
            this.disableSelf();
            this.getLog4JLogger().error("Printing stack trace, please send this to the developers:", e);
            return;
        }

        if (!this.loadConfiguration()) {
            this.disableSelf();
            return;
        }
        this.setupListeners();
        this.setupCommands();
        this.setupRestrictions();

        this.injector.getInstance(NoclipManager.class).start();
    }

    /**
     * Loads the plugin's configuration. If an exception is caught, logs the
     * error and returns false.
     *
     * @return whether the loading was successful
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
                this.getLog4JLogger().error("Exception caught during config load for {}", config.configurateWrapper().filePath());
                this.getLog4JLogger().error("Please check your config.");
                this.getLog4JLogger().error("Printing stack trace:", e);
                return false;
            }
        }

        this.getLog4JLogger().info("Successfully loaded configuration.");
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

    private void setupCommands() {
        final @NonNull CommandService commandService = this.injector.getInstance(CommandService.class);
        commandService.init();

        final cloud.commandframework.paper.@Nullable PaperCommandManager<CommandSender> commandManager = commandService.get();
        if (commandManager == null) {
            this.getLog4JLogger().error("The CommandService was null after initialization!");
            this.getLog4JLogger().error("Disabling plugin.");
            this.disableSelf();
            return;
        }

        this.injector.getInstance(AdvancedFlyCommand.class).register(commandManager);
        this.injector.getInstance(ArmorColorCommand.class).register(commandManager);
        this.injector.getInstance(BannerCommand.class).register(commandManager);
        this.injector.getInstance(BuildersUtilitiesCommand.class).register(commandManager);
        this.injector.getInstance(NightVisionCommand.class).register(commandManager);
        this.injector.getInstance(NoclipCommand.class).register(commandManager);
    }

    private void setupRestrictions() {
        final var loader = new SpigotRestrictionLoader(
                this.getLog4JLogger(),
                Arrays.asList(this.getServer().getPluginManager().getPlugins()),
                List.of(R_PlotSquared_6.class, R_WorldGuard_7.class)
        );

        loader.load(this.injector.getInstance(SpigotRestrictionHelper.class));
    }

}
