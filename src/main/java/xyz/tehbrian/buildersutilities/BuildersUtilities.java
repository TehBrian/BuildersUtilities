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
import org.slf4j.Logger;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerBaseInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerColorInventoryListener;
import xyz.tehbrian.buildersutilities.banner.listener.BannerPatternInventoryListener;
import xyz.tehbrian.buildersutilities.commands.AdvancedFlyCommand;
import xyz.tehbrian.buildersutilities.commands.ArmorColorCommand;
import xyz.tehbrian.buildersutilities.commands.BannerCommand;
import xyz.tehbrian.buildersutilities.commands.BuildersUtilitiesCommand;
import xyz.tehbrian.buildersutilities.commands.EmptyTabCompleter;
import xyz.tehbrian.buildersutilities.commands.NightVisionCommand;
import xyz.tehbrian.buildersutilities.commands.NoClipCommand;
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
import xyz.tehbrian.restrictionhelper.bukkit.BukkitRestrictionHelper;
import xyz.tehbrian.restrictionhelper.bukkit.BukkitRestrictionLoader;
import xyz.tehbrian.restrictionhelper.bukkit.restrictions.PlotSquaredRestriction;
import xyz.tehbrian.restrictionhelper.bukkit.restrictions.WorldGuardRestriction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BuildersUtilities extends JavaPlugin {

    private static BuildersUtilities instance;

    @MonotonicNonNull
    private Injector injector;

    public BuildersUtilities() {
        instance = this;
    }

    public static BuildersUtilities getInstance() {
        return instance;
    }

    public void onEnable() {
        this.injector = Guice.createInjector(
                new ArmorColorModule(),
                new BannerModule(),
                new ConfigModule(),
                new OptionsModule(),
                new PluginModule(this),
                new RestrictionHelperModule(),
                new UserModule()
        );

        this.setupConfig();
        this.setupEvents();
        this.setupCommands();
        this.setupRestrictions();

        this.injector.getInstance(NoClipManager.class).start();
    }

    private void setupConfig() {
        this.saveDefaultConfig();
    }

    private void setupEvents() {
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
        PluginManager pm = this.getServer().getPluginManager();

        for (Key<? extends Listener> listener : listeners) {
            Listener instance = this.injector.getInstance(listener);
            pm.registerEvents(instance, this);
        }
    }

    private void setupCommands() {
        Map<String, Key<? extends CommandExecutor>> toRegister = new HashMap<>();

        toRegister.put("advancedfly", Key.get(AdvancedFlyCommand.class));
        toRegister.put("armorcolor", Key.get(ArmorColorCommand.class));
        toRegister.put("banner", Key.get(BannerCommand.class));
        toRegister.put("nightvision", Key.get(NightVisionCommand.class));
        toRegister.put("noclip", Key.get(NoClipCommand.class));

        this.registerCommandsWithEmptyTabCompleter(toRegister);

        var buildersUtilitiesCommand = this.injector.getInstance(BuildersUtilitiesCommand.class);
        this.getCommand("buildersutilities").setExecutor(buildersUtilitiesCommand);
        this.getCommand("buildersutilities").setTabCompleter(buildersUtilitiesCommand);
    }

    private void registerCommandsWithEmptyTabCompleter(final Map<String, Key<? extends CommandExecutor>> commands) {
        EmptyTabCompleter emptyTabCompleter = new EmptyTabCompleter();

        for (String commandName : commands.keySet()) {
            PluginCommand command = this.getCommand(commandName);

            CommandExecutor instance = this.injector.getInstance(commands.get(commandName));
            command.setExecutor(instance);
            command.setTabCompleter(emptyTabCompleter);
        }
    }

    private void setupRestrictions() {
        var restrictionHelper = this.injector.getInstance(BukkitRestrictionHelper.class);

        PluginManager pm = this.getServer().getPluginManager();

        Logger logger = this.injector.getInstance(Logger.class);
        var loader = new BukkitRestrictionLoader(logger,
                Arrays.asList(pm.getPlugins()),
                List.of(PlotSquaredRestriction.class, WorldGuardRestriction.class));

        loader.load(restrictionHelper);
    }

}
