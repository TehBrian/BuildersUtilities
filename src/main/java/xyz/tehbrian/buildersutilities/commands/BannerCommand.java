package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.banner.provider.BannerBaseInventoryProvider;

public final class BannerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.openInventory(BannerBaseInventoryProvider.generate());
        }
        return true;
    }
}
