package xyz.tehbrian.buildersutilities.commands;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.UserService;

public final class NoClipCommand implements CommandExecutor {

    private final UserService userService;
    private final LangConfig lang;

    @Inject
    public NoClipCommand(
            final @NonNull UserService userService,
            final @NonNull LangConfig lang
    ) {
        this.userService = userService;
        this.lang = lang;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player player) {

            if (this.userService.getUser(player).toggleNoClipEnabled()) {
                player.sendMessage(this.lang.c(NodePath.path("messages.commands.no_clip.enabled")));
            } else {
                player.sendMessage(this.lang.c(NodePath.path("messages.commands.no_clip.disabled")));
            }
        }
        return true;
    }

}
