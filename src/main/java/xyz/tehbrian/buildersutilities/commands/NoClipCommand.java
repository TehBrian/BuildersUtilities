package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public final class NoClipCommand implements CommandExecutor {

    private final BuildersUtilities main;

    public NoClipCommand(final BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (this.main.getPlayerDataManager().getPlayerData(player).toggleNoClipEnabled()) {
                player.sendMessage(MessageUtils.getMessage("messages.commands.no_clip.enabled"));
            } else {
                player.sendMessage(MessageUtils.getMessage("messages.commands.no_clip.disabled"));
            }
        }
        return true;
    }
}
