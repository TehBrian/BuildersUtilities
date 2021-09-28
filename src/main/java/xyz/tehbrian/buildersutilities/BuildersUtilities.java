package xyz.tehbrian.buildersutilities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.tehbrian.tehlib.paper.TehPlugin;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerBaseInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerColorInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerPatternInventoryListener;
import xyz.tehbrian.buildersutilities.command.AdvancedFlyCommand;
import xyz.tehbrian.buildersutilities.command.ArmorColorCommand;
import xyz.tehbrian.buildersutilities.command.BannerCommand;
import xyz.tehbrian.buildersutilities.command.BuildersUtilitiesCommand;
import xyz.tehbrian.buildersutilities.command.CommandService;
import xyz.tehbrian.buildersutilities.command.NightVisionCommand;
import xyz.tehbrian.buildersutilities.command.NoClipCommand;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.inject.ArmorColorModule;
import xyz.tehbrian.buildersutilities.inject.BannerModule;
import xyz.tehbrian.buildersutilities.inject.ConfigModule;
import xyz.tehbrian.buildersutilities.inject.OptionsModule;
import xyz.tehbrian.buildersutilities.inject.PluginModule;
import xyz.tehbrian.buildersutilities.inject.RestrictionHelperModule;
import xyz.tehbrian.buildersutilities.inject.UserModule;
import xyz.tehbrian.buildersutilities.option.AdvancedFlyListener;
import xyz.tehbrian.buildersutilities.option.DoubleSlabListener;
import xyz.tehbrian.buildersutilities.option.GlazedTerracottaListener;
import xyz.tehbrian.buildersutilities.option.IronDoorListener;
import xyz.tehbrian.buildersutilities.option.NoClipManager;
import xyz.tehbrian.buildersutilities.option.OptionsInventoryListener;
import xyz.tehbrian.buildersutilities.setting.SettingsListener;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionLoader;
import xyz.tehbrian.restrictionhelper.spigot.restrictions.R_PlotSquared_6_1;
import xyz.tehbrian.restrictionhelper.spigot.restrictions.R_WorldGuard_7_0;

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
                    new ArmorColorModule(),
                    new BannerModule(),
                    new ConfigModule(),
                    new OptionsModule(),
                    new PluginModule(this),
                    new RestrictionHelperModule(),
                    new UserModule()
            );
        } catch (final Exception e) {
            this.getLog4JLogger().error("Something went wrong while creating the Guice injector.");
            this.getLog4JLogger().error("Disabling plugin.");
            this.disableSelf();
            this.getLog4JLogger().error("Printing stack trace, please send this to the developers:", e);
            return;
        }

        this.loadConfigs();
        this.setupListeners();
        this.setupCommands();
        this.setupRestrictions();

        this.injector.getInstance(NoClipManager.class).start();
    }

    /**
     * Loads the various plugin config files.
     */
    public void loadConfigs() {
        this.saveResourceSilently("config.yml");
        this.saveResourceSilently("lang.yml");

        this.injector.getInstance(ConfigConfig.class).load();
        this.injector.getInstance(LangConfig.class).load();
    }

    private void setupListeners() {
        this.registerListeners(
                this.injector.getInstance(BannerBaseInventoryListener.class),
                this.injector.getInstance(BannerColorInventoryListener.class),
                this.injector.getInstance(BannerPatternInventoryListener.class),
                this.injector.getInstance(ArmorColorInventoryListener.class),
                this.injector.getInstance(OptionsInventoryListener.class),
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
        this.injector.getInstance(NoClipCommand.class).register(commandManager);
    }

    private void setupRestrictions() {
        final var loader = new SpigotRestrictionLoader(
                this.getLog4JLogger(),
                Arrays.asList(this.getServer().getPluginManager().getPlugins()),
                List.of(R_PlotSquared_6_1.class, R_WorldGuard_7_0.class)
        );

        loader.load(this.injector.getInstance(SpigotRestrictionHelper.class));
    }

}
