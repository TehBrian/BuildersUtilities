package xyz.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.option.OptionsInventoryProvider;
import xyz.tehbrian.buildersutilities.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class BuildersUtilitiesCommand implements CommandExecutor, TabCompleter {

    private final BuildersUtilities main;
    private final UserService userService;
    private final LangConfig lang;
    private final OptionsInventoryProvider optionsInventoryProvider;

    @Inject
    public BuildersUtilitiesCommand(
            final @NonNull BuildersUtilities main,
            final @NonNull UserService userService,
            final @NonNull LangConfig lang,
            final @NonNull OptionsInventoryProvider optionsInventoryProvider
    ) {
        this.main = main;
        this.userService = userService;
        this.lang = lang;
        this.optionsInventoryProvider = optionsInventoryProvider;
    }

    @Override
    public boolean onCommand(
            final @NotNull CommandSender sender,
            final @NotNull Command cmd,
            final @NotNull String label,
            final String[] args
    ) {
        if (args.length >= 1
                && "reload".equals(args[0].toLowerCase(Locale.ROOT))
                && sender.hasPermission(Constants.Permissions.RELOAD)) {
            this.main.reloadConfig();
            sender.sendMessage(this.lang.c(NodePath.path("commands", "reload")));
            return true;
        }

        if (sender instanceof Player player) {
            player.openInventory(this.optionsInventoryProvider.generate(this.userService.getUser(player)));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(
            final @NotNull CommandSender sender,
            final @NotNull Command command,
            final @NotNull String alias,
            final String[] args
    ) {
        final List<String> suggestions = new ArrayList<>();

        if (args.length == 1
                && sender.hasPermission(Constants.Permissions.RELOAD)) {
            suggestions.add("reload");
        }

        return suggestions;
    }

}
