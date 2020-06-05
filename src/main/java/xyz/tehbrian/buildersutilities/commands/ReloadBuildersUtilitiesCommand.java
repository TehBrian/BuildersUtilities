package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public class ReloadBuildersUtilitiesCommand implements CommandExecutor {

    private final BuildersUtilities main;

    public ReloadBuildersUtilitiesCommand(BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        main.reloadConfig();
        main.setPermissionMessages();
        sender.sendMessage(MessageUtils.getMessage("messages.commands.reload"));
        return true;
    }
}
