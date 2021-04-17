package xyz.tehbrian.buildersutilities;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tehbrian.buildersutilities.commands.AdvancedFlyCommand;
import xyz.tehbrian.buildersutilities.commands.ArmorColorCommand;
import xyz.tehbrian.buildersutilities.commands.BannerCommand;
import xyz.tehbrian.buildersutilities.commands.BuildersUtilitiesCommand;
import xyz.tehbrian.buildersutilities.commands.NightVisionCommand;
import xyz.tehbrian.buildersutilities.commands.NoClipCommand;
import xyz.tehbrian.buildersutilities.listeners.AdvancedFlyListener;
import xyz.tehbrian.buildersutilities.listeners.BuildingUtilitiesListener;
import xyz.tehbrian.buildersutilities.listeners.SettingsListener;
import xyz.tehbrian.buildersutilities.listeners.inventories.ArmorColorInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventories.OptionsInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventories.banner.BannerBaseInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventories.banner.BannerColorInventoryListener;
import xyz.tehbrian.buildersutilities.listeners.inventories.banner.BannerPatternInventoryListener;
import xyz.tehbrian.buildersutilities.player.PlayerDataManager;
import xyz.tehbrian.buildersutilities.util.MessageUtils;
import xyz.tehbrian.restrictionhelper.DebugLogger;
import xyz.tehbrian.restrictionhelper.RestrictionHelper;
import xyz.tehbrian.restrictionhelper.restrictions.PlotSquaredRestriction;
import xyz.tehbrian.restrictionhelper.restrictions.WorldGuardRestriction;

public final class BuildersUtilities extends JavaPlugin {

    private static BuildersUtilities instance;

    private PlayerDataManager playerDataManager;
    private RestrictionHelper restrictionHelper;

    public BuildersUtilities() {
        instance = this;
    }

    public static BuildersUtilities getInstance() {
        return instance;
    }

    public void onEnable() {
        this.setupConfig();
        this.setupEvents();
        this.setupCommands();
        this.setupRestrictions();

        new NoClipManager(this);
    }

    private void setupConfig() {
        this.saveDefaultConfig();
    }

    private void setupEvents() {
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new BannerBaseInventoryListener(), this);
        pm.registerEvents(new BannerColorInventoryListener(), this);
        pm.registerEvents(new BannerPatternInventoryListener(), this);

        pm.registerEvents(new ArmorColorInventoryListener(), this);
        pm.registerEvents(new OptionsInventoryListener(this), this);

        pm.registerEvents(new AdvancedFlyListener(this), this);
        pm.registerEvents(new BuildingUtilitiesListener(this), this);
        pm.registerEvents(new SettingsListener(this), this);
    }

    private void setupCommands() {
        this.getCommand("advancedfly").setExecutor(new AdvancedFlyCommand(this));
        this.getCommand("armorcolor").setExecutor(new ArmorColorCommand());
        this.getCommand("banner").setExecutor(new BannerCommand());

        var buildersUtilitiesCommand = new BuildersUtilitiesCommand(this);
        this.getCommand("buildersutilities").setExecutor(buildersUtilitiesCommand);
        this.getCommand("buildersutilities").setTabCompleter(buildersUtilitiesCommand);

        this.getCommand("nightvision").setExecutor(new NightVisionCommand(this));
        this.getCommand("noclip").setExecutor(new NoClipCommand(this));

        this.setPermissionMessages();
    }

    private void setupRestrictions() {
        this.restrictionHelper = new RestrictionHelper(this.getLogger());
        DebugLogger debugLogger = this.restrictionHelper.getDebugLogger();

        PluginManager pm = this.getServer().getPluginManager();
        if (pm.getPlugin("PlotSquared") != null) {
            this.getLogger().info("PlotSquared detected. Registering permission validator..");
            this.restrictionHelper.registerRestriction(new PlotSquaredRestriction(debugLogger));
            this.getLogger().info("PlotSquared validator registered successfully!");
        }

        if (pm.getPlugin("WorldGuard") != null) {
            this.getLogger().info("WorldGuard detected. Registering permission validator..");
            this.restrictionHelper.registerRestriction(new WorldGuardRestriction(debugLogger));
            this.getLogger().info("WorldGuard validator registered successfully!");
        }
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

        for (String commandName : this.getDescription().getCommands().keySet()) {
            PluginCommand command = this.getCommand(commandName);
            command.setPermissionMessage(permissionMessage);
        }
    }

    public PlayerDataManager getPlayerDataManager() {
        if (this.playerDataManager == null) {
            this.playerDataManager = new PlayerDataManager();
        }
        return this.playerDataManager;
    }

    public RestrictionHelper getRestrictionHelper() {
        return this.restrictionHelper;
    }
}
