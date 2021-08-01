package xyz.tehbrian.buildersutilities.commands;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.paper.PlayerViewer;
import org.jetbrains.annotations.NotNull;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInterfaceProvider;

public final class ArmorColorCommand implements CommandExecutor {

    private final ArmorColorInterfaceProvider armorColorInterfaceProvider;

    @Inject
    public ArmorColorCommand(
            final @NonNull ArmorColorInterfaceProvider armorColorInterfaceProvider
    ) {
        this.armorColorInterfaceProvider = armorColorInterfaceProvider;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command cmd, final @NotNull String label, final String[] args) {
        if (sender instanceof Player player) {
            this.armorColorInterfaceProvider.generate().open(PlayerViewer.of(player));
        }
        return true;
    }

}
