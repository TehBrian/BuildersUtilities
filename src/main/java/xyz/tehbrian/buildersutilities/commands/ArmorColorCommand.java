package xyz.tehbrian.buildersutilities.commands;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInventoryProvider;

public final class ArmorColorCommand implements CommandExecutor {

    private final ArmorColorInventoryProvider armorColorInventoryProvider;

    @Inject
    public ArmorColorCommand(
            final @NonNull ArmorColorInventoryProvider armorColorInventoryProvider
    ) {
        this.armorColorInventoryProvider = armorColorInventoryProvider;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.openInventory(this.armorColorInventoryProvider.generate());
        }
        return true;
    }
}
