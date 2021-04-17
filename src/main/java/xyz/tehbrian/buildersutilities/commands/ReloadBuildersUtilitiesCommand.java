package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public final class ReloadBuildersUtilitiesCommand implements CommandExecutor {

    private final BuildersUtilities main;

    public ReloadBuildersUtilitiesCommand(final BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        this.main.reloadConfig();
        this.main.setPermissionMessages();
        sender.sendMessage(MessageUtils.getMessage("messages.commands.reload"));
        return true;
    }
}
