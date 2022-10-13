package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.ArgumentDescription;
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

public final class NoclipCommand extends PaperCloudCommand<CommandSender> {

  private final UserService userService;
  private final LangConfig langConfig;

  @Inject
  public NoclipCommand(
      final UserService userService,
      final LangConfig langConfig
  ) {
    this.userService = userService;
    this.langConfig = langConfig;
  }

  @Override
  public void register(final PaperCommandManager<CommandSender> commandManager) {
    final var root = commandManager.commandBuilder("noclip", "nc")
        .meta(CommandMeta.DESCRIPTION, "Toggles noclip.")
        .permission(Permissions.NOCLIP)
        .senderType(Player.class);

    final var on = root.literal("on", ArgumentDescription.of("Enables noclip."))
        .handler(c -> {
          final var sender = (Player) c.getSender();
          this.userService.getUser(sender).noclipEnabled(true);
          sender.sendMessage(this.langConfig.c(NodePath.path("commands", "noclip", "enabled")));
        });

    final var off = root.literal("off", ArgumentDescription.of("Disables noclip."))
        .handler(c -> {
          final var sender = (Player) c.getSender();
          this.userService.getUser(sender).noclipEnabled(false);
          sender.sendMessage(this.langConfig.c(NodePath.path("commands", "noclip", "disabled")));
        });

    final var toggle = root
        .handler(c -> {
          final var sender = (Player) c.getSender();
          if (this.userService.getUser(sender).toggleNoclipEnabled()) {
            sender.sendMessage(this.langConfig.c(NodePath.path("commands", "noclip", "enabled")));
          } else {
            sender.sendMessage(this.langConfig.c(NodePath.path("commands", "noclip", "disabled")));
          }
        });

    commandManager.command(on)
        .command(off)
        .command(toggle);
  }

}
