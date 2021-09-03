package xyz.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.UserService;

public final class NightVisionCommand implements CommandExecutor {

    private final UserService userService;
    private final LangConfig lang;

    @Inject
    public NightVisionCommand(
            final @NonNull UserService userService,
            final @NonNull LangConfig lang
    ) {
        this.userService = userService;
        this.lang = lang;
    }

    @Override
    public boolean onCommand(
            final @NotNull CommandSender sender,
            final @NotNull Command cmd,
            final @NotNull String label,
            final String[] args
    ) {
        if (sender instanceof Player player) {
            if (this.userService.getUser(player).toggleNightVisionEnabled()) {
                player.sendMessage(this.lang.c(NodePath.path("commands", "night-vision", "enabled")));
            } else {
                player.sendMessage(this.lang.c(NodePath.path("commands", "night-vision", "disabled")));
            }
        }
        return true;
    }

}
