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

public final class NightVisionCommand implements CommandExecutor {

    private final UserService userManager;
    private final LangConfig lang;

    @Inject
    public NightVisionCommand(
            final @NonNull UserService userManager,
            final @NonNull LangConfig lang
    ) {
        this.userManager = userManager;
        this.lang = lang;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player player) {

            if (this.userManager.getUser(player).toggleNightVisionEnabled()) {
                player.sendMessage(this.lang.c(NodePath.path("messages.commands.night_vision.enabled")));
            } else {
                player.sendMessage(this.lang.c(NodePath.path("messages.commands.night_vision.disabled")));
            }
        }
        return true;
    }

}
