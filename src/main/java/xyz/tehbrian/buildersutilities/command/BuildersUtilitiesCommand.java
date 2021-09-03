package xyz.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import org.bukkit.Chunk;
import org.bukkit.World;
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

import java.util.*;

public final class BuildersUtilitiesCommand implements CommandExecutor, TabCompleter {

    private final BuildersUtilities buildersUtilities;
    private final UserService userService;
    private final LangConfig lang;
    private final OptionsInventoryProvider optionsInventoryProvider;

    @Inject
    public BuildersUtilitiesCommand(
            final @NonNull BuildersUtilities buildersUtilities,
            final @NonNull UserService userService,
            final @NonNull LangConfig lang,
            final @NonNull OptionsInventoryProvider optionsInventoryProvider
    ) {
        this.buildersUtilities = buildersUtilities;
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
            this.buildersUtilities.loadConfigs();
            sender.sendMessage(this.lang.c(NodePath.path("commands", "reload")));
            return true;
        }

        if (args.length >= 1
                && "reloadchunks".equals(args[0].toLowerCase(Locale.ROOT))
                && sender instanceof Player player) {

            final Collection<Chunk> chunksToReload = this.around(player.getLocation().getChunk(), player.getClientViewDistance());

            /*
            PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
            for (Chunk chunk : chunksToReload) {
                PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), 65535);
                playerConnection.sendPacket(packet);
            }
             */

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

    // From https://www.spigotmc.org/threads/getting-chunks-around-a-center-chunk-within-a-specific-radius.422279/
    public Collection<Chunk> around(final Chunk origin, final int radius) {
        final World world = origin.getWorld();

        final int length = (radius * 2) + 1;
        final Set<Chunk> chunks = new HashSet<>(length * length);

        final int cX = origin.getX();
        final int cZ = origin.getZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                chunks.add(world.getChunkAt(cX + x, cZ + z));
            }
        }
        return chunks;
    }

}
