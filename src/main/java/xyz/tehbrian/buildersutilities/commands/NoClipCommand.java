package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public class NoClipCommand implements CommandExecutor {

    private final BuildersUtilities main;

    public NoClipCommand(BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (main.getPlayerDataManager().getPlayerData(player).toggleNoClipEnabled()) {
                player.sendMessage(MessageUtils.getMessage("msg.no_clip.enabled"));
            } else {
                player.sendMessage(MessageUtils.getMessage("msg.no_clip.disabled"));
            }
        }
        return true;
    }
}
