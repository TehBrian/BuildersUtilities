package xyz.tehbrian.buildersutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.inventories.OptionsInventoryProvider;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class BuildersUtilitiesCommand implements CommandExecutor, TabCompleter {

    private final BuildersUtilities main;

    public BuildersUtilitiesCommand(final BuildersUtilities main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length >= 1
                && "reload".equals(args[0].toLowerCase(Locale.ROOT))
                && sender.hasPermission("buildersutilities.reload")) {
            this.main.reloadConfig();
            this.main.setPermissionMessages();
            sender.sendMessage(MessageUtils.getMessage("messages.commands.reload"));
            return true;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.openInventory(OptionsInventoryProvider.generate(player));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1
                && sender.hasPermission("buildersutilities.reload")) {
            suggestions.add("reload");
        }

        return suggestions;
    }
}
