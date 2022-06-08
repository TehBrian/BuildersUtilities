package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.UserService;

public final class NoclipCommand extends PaperCloudCommand<CommandSender> {

    private final UserService userService;
    private final LangConfig langConfig;

    @Inject
    public NoclipCommand(
            final @NonNull UserService userService,
            final @NonNull LangConfig langConfig
    ) {
        this.userService = userService;
        this.langConfig = langConfig;
    }

    @Override
    public void register(final @NonNull PaperCommandManager<CommandSender> commandManager) {
        final var main = commandManager.commandBuilder("noclip", "nc")
                .meta(CommandMeta.DESCRIPTION, "Toggles noclip.")
                .permission(Constants.Permissions.NOCLIP)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    if (this.userService.getUser(sender).toggleNoclipEnabled()) {
                        sender.sendMessage(this.langConfig.c(NodePath.path("commands", "noclip", "enabled")));
                    } else {
                        sender.sendMessage(this.langConfig.c(NodePath.path("commands", "noclip", "disabled")));
                    }
                });

        commandManager.command(main);
    }

}
