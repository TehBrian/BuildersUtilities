package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.banner.provider.BannerBaseInventoryProvider;

public final class BannerCommand extends PaperCloudCommand<CommandSender> {

    private final BannerBaseInventoryProvider bannerBaseInventoryProvider;

    @Inject
    public BannerCommand(
            final @NonNull BannerBaseInventoryProvider bannerBaseInventoryProvider
    ) {
        this.bannerBaseInventoryProvider = bannerBaseInventoryProvider;
    }

    /**
     * Register the command.
     *
     * @param commandManager the command manager
     */
    @Override
    public void register(@NonNull final PaperCommandManager<CommandSender> commandManager) {
        final var main = commandManager.commandBuilder("banner", "bc")
                .meta(CommandMeta.DESCRIPTION, "Opens the banner creator.")
                .permission(Constants.Permissions.BANNER)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    sender.openInventory(this.bannerBaseInventoryProvider.generate());
                });

        commandManager.command(main);
    }

}
