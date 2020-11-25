package xyz.tehbrian.buildersutilities;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tehbrian.buildersutilities.commands.*;
import xyz.tehbrian.buildersutilities.listeners.AdvancedFlyListener;
import xyz.tehbrian.buildersutilities.listeners.BuildingUtilitiesListener;
import xyz.tehbrian.buildersutilities.listeners.SettingsListener;
import xyz.tehbrian.buildersutilities.listeners.inventory.ArmorColorInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventory.BannerInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventory.OptionsInventoryListener;
import xyz.tehbrian.buildersutilities.managers.NoClipManager;
import xyz.tehbrian.buildersutilities.managers.PlayerDataManager;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

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
        pm.registerEvents(new SettingsListener(this), this);
    }

    private void setupCommands() {
        getCommand("advancedfly").setExecutor(new AdvancedFlyCommand(this));
        getCommand("armorcolor").setExecutor(new ArmorColorCommand());
        getCommand("banner").setExecutor(new BannerCommand());
        getCommand("buildersutilities").setExecutor(new BuildersUtilitiesCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand(this));
        getCommand("noclip").setExecutor(new NoClipCommand(this));
        getCommand("reloadbuildersutilities").setExecutor(new ReloadBuildersUtilitiesCommand(this));

        setPermissionMessages();
    }

    /*
        Unfortunately, only Paper allows you to set the
        default permission message, so to allow those who
        still use Spigot (or till Spigot gets their stuff
        together and allows people to customize messages)
        this will have to do.

        Side note: Since we return prematurely when the permissionMessage
        is null or empty rather than setting it to the server default,
        if the user would like to change the permission message *back* to
        the default during runtime, they must reload the entire server
        rather than just using the plugin's reload command. This can
        be fixed if we set the permission message before we return, but
        the issue is *how* do we get the default permission message?
     */
    public void setPermissionMessages() {
        String permissionMessage = MessageUtils.getMessage("messages.commands.no_permission");
        if (permissionMessage == null || permissionMessage.isEmpty()) {
            return;
        }

        for (String commandName : getDescription().getCommands().keySet()) {
            PluginCommand command = getCommand(commandName);
            command.setPermissionMessage(permissionMessage);
        }
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
