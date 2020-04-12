package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public class NightVisionCommand implements CommandExecutor {

    private final BuildersUtilities main;

    public NightVisionCommand(BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (main.getPlayerDataManager().getPlayerData(player).toggleNightVisionEnabled()) {
                player.sendMessage(MessageUtils.getMessage("msg.night_vision.enabled"));
            } else {
                player.sendMessage(MessageUtils.getMessage("msg.night_vision.disabled"));
            }
        }
        return true;
    }
}
