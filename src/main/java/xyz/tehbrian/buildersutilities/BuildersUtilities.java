package xyz.tehbrian.buildersutilities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerBaseInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerColorInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerPatternInventoryListener;
import xyz.tehbrian.buildersutilities.command.AdvancedFlyCommand;
import xyz.tehbrian.buildersutilities.command.ArmorColorCommand;
import xyz.tehbrian.buildersutilities.command.BannerCommand;
import xyz.tehbrian.buildersutilities.command.BuildersUtilitiesCommand;
import xyz.tehbrian.buildersutilities.command.EmptyTabCompleter;
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
import xyz.tehbrian.restrictionhelper.spigot.restrictions.PlotSquaredRestriction;
import xyz.tehbrian.restrictionhelper.spigot.restrictions.WorldGuardRestriction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public final class BuildersUtilities extends JavaPlugin {

    @MonotonicNonNull
    private Injector injector;

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
            this.getLogger().severe("Something went wrong while creating the Guice injector.");
            this.getLogger().severe("Disabling plugin.");
            this.getLogger().severe("Printing stack trace, please send this to the developers:");
            this.getLogger().log(Level.SEVERE, e.getMessage(), e);
            this.disableSelf();
            return;
        }

        this.setupConfig();
        this.setupListeners();
        this.setupCommands();
        this.setupRestrictions();

        this.injector.getInstance(NoClipManager.class).start();
    }

    private void setupConfig() {
        this.saveResource("config.yml", false);
        this.saveResource("lang.yml", false);

        this.injector.getInstance(ConfigConfig.class).load();
        this.injector.getInstance(LangConfig.class).load();
    }

    private void setupListeners() {
        registerEventListeners(
                Key.get(BannerBaseInventoryListener.class),
                Key.get(BannerColorInventoryListener.class),
                Key.get(BannerPatternInventoryListener.class),
                Key.get(ArmorColorInventoryListener.class),
                Key.get(OptionsInventoryListener.class),
                Key.get(AdvancedFlyListener.class),
                Key.get(DoubleSlabListener.class),
                Key.get(GlazedTerracottaListener.class),
                Key.get(IronDoorListener.class),
                Key.get(SettingsListener.class)
        );
    }

    @SafeVarargs
    private void registerEventListeners(final Key<? extends Listener>... listeners) {
        final PluginManager pm = this.getServer().getPluginManager();

        for (final Key<? extends Listener> listener : listeners) {
            final Listener instance = this.injector.getInstance(listener);
            pm.registerEvents(instance, this);
        }
    }

    private void setupCommands() {
        final Map<String, Key<? extends CommandExecutor>> toRegister = new HashMap<>();

        toRegister.put("advancedfly", Key.get(AdvancedFlyCommand.class));
        toRegister.put("armorcolor", Key.get(ArmorColorCommand.class));
        toRegister.put("banner", Key.get(BannerCommand.class));
        toRegister.put("nightvision", Key.get(NightVisionCommand.class));
        toRegister.put("noclip", Key.get(NoClipCommand.class));

        this.registerCommandsWithEmptyTabCompleter(toRegister);

        final var buildersUtilitiesCommand = this.injector.getInstance(BuildersUtilitiesCommand.class);
        this.getCommand("buildersutilities").setExecutor(buildersUtilitiesCommand);
        this.getCommand("buildersutilities").setTabCompleter(buildersUtilitiesCommand);
    }

    private void registerCommandsWithEmptyTabCompleter(final Map<String, Key<? extends CommandExecutor>> commands) {
        final EmptyTabCompleter emptyTabCompleter = new EmptyTabCompleter();

        for (final String commandName : commands.keySet()) {
            final PluginCommand command = this.getCommand(commandName);

            final CommandExecutor instance = this.injector.getInstance(commands.get(commandName));
            command.setExecutor(instance);
            command.setTabCompleter(emptyTabCompleter);
        }
    }

    private void setupRestrictions() {
        final var restrictionHelper = this.injector.getInstance(SpigotRestrictionHelper.class);

        final PluginManager pm = this.getServer().getPluginManager();

        final var loader = new SpigotRestrictionLoader(
                this.getLog4JLogger(),
                Arrays.asList(pm.getPlugins()),
                List.of(PlotSquaredRestriction.class, WorldGuardRestriction.class)
        );

        loader.load(restrictionHelper);
    }

    /**
     * Disables this plugin.
     */
    public void disableSelf() {
        this.getServer().getPluginManager().disablePlugin(this);
    }

}
