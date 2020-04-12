package xyz.tehbrian.buildersutilities;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tehbrian.buildersutilities.commands.*;
import xyz.tehbrian.buildersutilities.listeners.AdvancedFlyListener;
import xyz.tehbrian.buildersutilities.listeners.BuildingUtilitiesListener;
import xyz.tehbrian.buildersutilities.listeners.OptionsListener;
import xyz.tehbrian.buildersutilities.listeners.inventory.ArmorColorInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventory.BannerInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventory.OptionsInventoryListener;
import xyz.tehbrian.buildersutilities.managers.NoClipManager;
import xyz.tehbrian.buildersutilities.managers.PlayerDataManager;

public class BuildersUtilities extends JavaPlugin {

    private static BuildersUtilities instance;

    private PlayerDataManager playerDataManager;

    public BuildersUtilities() {
        instance = this;
    }

    public static BuildersUtilities getInstance() {
        return instance;
    }

    public void onEnable() {
        setupConfig();
        setupEvents();
        setupCommands();

        new NoClipManager(this);
    }

    private void setupConfig() {
        this.saveDefaultConfig();
    }

    private void setupEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new ArmorColorInventoryListener(), this);
        pm.registerEvents(new BannerInventoryListener(), this);
        pm.registerEvents(new OptionsInventoryListener(this), this);
        pm.registerEvents(new AdvancedFlyListener(this), this);
        pm.registerEvents(new BuildingUtilitiesListener(this), this);
        pm.registerEvents(new OptionsListener(this), this);
    }

    private void setupCommands() {
        getCommand("advancedfly").setExecutor(new AdvancedFlyCommand(this));
        getCommand("armorcolor").setExecutor(new ArmorColorCommand());
        getCommand("banner").setExecutor(new BannerCommand());
        getCommand("buildersutilities").setExecutor(new BuildersUtilitiesCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand(this));
        getCommand("noclip").setExecutor(new NoClipCommand(this));
        getCommand("reloadbuildersutilities").setExecutor(new ReloadBuildersUtilitiesCommand(this));
    }

    /*
        TODO: What is the performance hit of checking if null very often?
        Could we possibly initialize in onEnable? Would that be safe?
     */
    public PlayerDataManager getPlayerDataManager() {
        if (playerDataManager == null) {
            playerDataManager = new PlayerDataManager();
        }
        return playerDataManager;
    }
}
