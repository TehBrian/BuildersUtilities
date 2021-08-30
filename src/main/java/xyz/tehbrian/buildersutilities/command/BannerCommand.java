package xyz.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import xyz.tehbrian.buildersutilities.banner.provider.BannerBaseInventoryProvider;

public final class BannerCommand implements CommandExecutor {

    private final BannerBaseInventoryProvider bannerBaseInventoryProvider;

    @Inject
    public BannerCommand(
            final @NonNull BannerBaseInventoryProvider bannerBaseInventoryProvider
    ) {
        this.bannerBaseInventoryProvider = bannerBaseInventoryProvider;
    }

    @Override
    public boolean onCommand(
            final @NotNull CommandSender sender,
            final @NotNull Command cmd,
            final @NotNull String label,
            final String[] args
    ) {
        if (sender instanceof Player player) {
            player.openInventory(this.bannerBaseInventoryProvider.generate());
        }
        return true;
    }

}
