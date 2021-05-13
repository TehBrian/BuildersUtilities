package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public final class AdvancedFlyCommand implements CommandExecutor {

    private final BuildersUtilities main;

    public AdvancedFlyCommand(final BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (this.main.getUserManager().getUserData(player).toggleAdvancedFlyEnabled()) {
                player.sendMessage(MessageUtils.getMessage("messages.commands.advanced_fly.enabled"));
            } else {
                player.sendMessage(MessageUtils.getMessage("messages.commands.advanced_fly.disabled"));
            }
        }
        return true;
    }
}
