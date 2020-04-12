package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public class AdvancedFlyCommand implements CommandExecutor {

    private final BuildersUtilities main;

    public AdvancedFlyCommand(BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (main.getPlayerDataManager().getPlayerData(player).toggleAdvancedFlyEnabled()) {
                player.sendMessage(MessageUtils.getMessage("msg.advanced_fly.enabled"));
            } else {
                player.sendMessage(MessageUtils.getMessage("msg.advanced_fly.disabled"));
            }
        }
        return true;
    }
}
