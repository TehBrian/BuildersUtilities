package dev.tehbrian.buildersutilities.command;

import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.PlayerSessions;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class BannerCommand {

  private final PlayerSessions playerSessions;

  @Inject
  public BannerCommand(
      final PlayerSessions playerSessions
  ) {
    this.playerSessions = playerSessions;
  }

  public void register(final PaperCommandManager<CommandSender> commandManager) {
    final var main = commandManager.commandBuilder("banner", "bc")
        .meta(CommandMeta.DESCRIPTION, "Opens the banner creator.")
        .permission(Permissions.BANNER)
        .senderType(Player.class)
        .handler(c -> {
          final var sender = (Player) c.getSender();
          this.playerSessions.get(sender).showInterface(sender);
        });

    commandManager.command(main);
  }

}
