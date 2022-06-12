package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.buildersutilities.util.Permissions;

public final class NightVisionCommand extends PaperCloudCommand<CommandSender> {

    private final UserService userService;
    private final LangConfig langConfig;

    @Inject
    public NightVisionCommand(
            final @NonNull UserService userService,
            final @NonNull LangConfig langConfig
    ) {
        this.userService = userService;
        this.langConfig = langConfig;
    }

    @Override
    public void register(final @NonNull PaperCommandManager<CommandSender> commandManager) {
        final var main = commandManager.commandBuilder("nightvision", "nv")
                .meta(CommandMeta.DESCRIPTION, "Toggles night vision.")
                .permission(Permissions.NIGHT_VISION)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    if (this.userService.getUser(sender).toggleNightVisionEnabled()) {
                        sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "enabled")));
                    } else {
                        sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "disabled")));
                    }
                });

        commandManager.command(main);
    }

}
