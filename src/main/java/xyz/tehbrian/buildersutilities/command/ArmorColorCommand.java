package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInventoryProvider;

public final class ArmorColorCommand extends PaperCloudCommand<CommandSender> {

    private final ArmorColorInventoryProvider armorColorInventoryProvider;

    @Inject
    public ArmorColorCommand(
            final @NonNull ArmorColorInventoryProvider armorColorInventoryProvider
    ) {
        this.armorColorInventoryProvider = armorColorInventoryProvider;
    }

    /**
     * Register the command.
     *
     * @param commandManager the command manager
     */
    @Override
    public void register(@NonNull final PaperCommandManager<CommandSender> commandManager) {
        final var main = commandManager.commandBuilder("armorcolor", "acc")
                .meta(CommandMeta.DESCRIPTION, "Opens the armor color creator.")
                .permission(Constants.Permissions.ARMOR_COLOR)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    sender.openInventory(this.armorColorInventoryProvider.generate());
                });

        commandManager.command(main);
    }

}
