package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorMenuProvider;
import xyz.tehbrian.buildersutilities.util.Permissions;

public final class ArmorColorCommand extends PaperCloudCommand<CommandSender> {

  private final ArmorColorMenuProvider armorColorMenuProvider;

  @Inject
  public ArmorColorCommand(
      final @NonNull ArmorColorMenuProvider armorColorMenuProvider
  ) {
    this.armorColorMenuProvider = armorColorMenuProvider;
  }

  @Override
  public void register(final @NonNull PaperCommandManager<CommandSender> commandManager) {
    final var main = commandManager.commandBuilder("armorcolor", "acc")
        .meta(CommandMeta.DESCRIPTION, "Opens the armor color creator.")
        .permission(Permissions.ARMOR_COLOR)
        .senderType(Player.class)
        .handler(c -> {
          final var sender = (Player) c.getSender();
          sender.openInventory(this.armorColorMenuProvider.generate());
        });

    commandManager.command(main);
  }

}
