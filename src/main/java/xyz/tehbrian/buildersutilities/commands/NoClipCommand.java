package xyz.tehbrian.buildersutilities.commands;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.user.UserManager;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public final class NoClipCommand implements CommandExecutor {

    private final UserManager userManager;

    @Inject
    public NoClipCommand(final @NonNull UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (this.userManager.getUser(player).toggleNoClipEnabled()) {
                player.sendMessage(MessageUtils.getMessage("messages.commands.no_clip.enabled"));
            } else {
                player.sendMessage(MessageUtils.getMessage("messages.commands.no_clip.disabled"));
            }
        }
        return true;
    }
}
