package dev.tehbrian.buildersutilities.command;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.NodePath;

public final class NightVisionCommand extends PaperCloudCommand<CommandSender> {

  private final UserService userService;
  private final LangConfig langConfig;

  @Inject
  public NightVisionCommand(
      final UserService userService,
      final LangConfig langConfig
  ) {
    this.userService = userService;
    this.langConfig = langConfig;
  }

  @Override
  public void register(final PaperCommandManager<CommandSender> commandManager) {
    final var root = commandManager.commandBuilder("nightvision", "nv")
        .meta(CommandMeta.DESCRIPTION, "Toggles night vision.")
        .permission(Permissions.NIGHT_VISION)
        .senderType(Player.class);

    final var on = root.literal("on", ArgumentDescription.of("Enables night vision."))
        .handler(c -> {
          final var sender = (Player) c.getSender();
          this.userService.getUser(sender).nightVisionEnabled(true);
          sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "enabled")));
        });

    final var off = root.literal("off", ArgumentDescription.of("Disables night vision."))
        .handler(c -> {
          final var sender = (Player) c.getSender();
          this.userService.getUser(sender).nightVisionEnabled(false);
          sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "disabled")));
        });

    final var toggle = root
        .handler(c -> {
          final var sender = (Player) c.getSender();
          if (this.userService.getUser(sender).toggleNightVisionEnabled()) {
            sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "enabled")));
          } else {
            sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "disabled")));
          }
        });

    commandManager.command(on)
        .command(off)
        .command(toggle);
  }

}
