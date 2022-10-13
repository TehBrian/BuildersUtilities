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

public final class AdvancedFlyCommand extends PaperCloudCommand<CommandSender> {

  private final UserService userService;
  private final LangConfig langConfig;

  @Inject
  public AdvancedFlyCommand(
      final UserService userService,
      final LangConfig langConfig
  ) {
    this.userService = userService;
    this.langConfig = langConfig;
  }

  @Override
  public void register(final PaperCommandManager<CommandSender> commandManager) {
    final var root = commandManager.commandBuilder("advancedfly", "advfly", "af")
        .meta(CommandMeta.DESCRIPTION, "Toggles advanced fly.")
        .permission(Permissions.ADVANCED_FLY)
        .senderType(Player.class);

    final var on = root.literal("on", ArgumentDescription.of("Enables advanced fly."))
        .handler(c -> {
          final var sender = (Player) c.getSender();
          this.userService.getUser(sender).advancedFlyEnabled(true);
          sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "enabled")));
        });

    final var off = root.literal("off", ArgumentDescription.of("Disables advanced fly."))
        .handler(c -> {
          final var sender = (Player) c.getSender();
          this.userService.getUser(sender).advancedFlyEnabled(false);
          sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "disabled")));
        });

    final var toggle = root
        .handler(c -> {
          final var sender = (Player) c.getSender();
          if (this.userService.getUser(sender).toggleAdvancedFlyEnabled()) {
            sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "enabled")));
          } else {
            sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "disabled")));
          }
        });

    commandManager.command(on)
        .command(off)
        .command(toggle);
  }

}
