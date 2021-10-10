package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import net.minecraft.network.protocol.game.PacketPlayOutMapChunk;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.option.OptionsInventoryProvider;
import xyz.tehbrian.buildersutilities.user.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class BuildersUtilitiesCommand extends PaperCloudCommand<CommandSender> {

    private final BuildersUtilities buildersUtilities;
    private final UserService userService;
    private final LangConfig langConfig;
    private final OptionsInventoryProvider optionsInventoryProvider;

    @Inject
    public BuildersUtilitiesCommand(
            final @NonNull BuildersUtilities buildersUtilities,
            final @NonNull UserService userService,
            final @NonNull LangConfig langConfig,
            final @NonNull OptionsInventoryProvider optionsInventoryProvider
    ) {
        this.buildersUtilities = buildersUtilities;
        this.userService = userService;
        this.langConfig = langConfig;
        this.optionsInventoryProvider = optionsInventoryProvider;
    }

    /**
     * Register the command.
     *
     * @param commandManager the command manager
     */
    @Override
    public void register(final @NonNull PaperCommandManager<CommandSender> commandManager) {
        final var main = commandManager.commandBuilder("buildersutilities", "butils", "bu");

        final var menu = main
                .meta(CommandMeta.DESCRIPTION, "Opens the options menu.")
                .permission(Constants.Permissions.BUILDERS_UTILITIES)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    sender.openInventory(this.optionsInventoryProvider.generate(this.userService.getUser(sender)));
                });

        final var rc = main.literal("rc", ArgumentDescription.of("Reloads the chunks around you."))
                .permission(Constants.Permissions.RC)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    final Collection<Chunk> chunksToReload = this.around(sender.getLocation().getChunk(), sender.getClientViewDistance());

                    final PlayerConnection playerConnection = ((CraftPlayer) sender).getHandle().b;
                    for (final Chunk chunk : chunksToReload) {
                        final PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), false);
                        playerConnection.sendPacket(packet);
                    }

                    sender.sendMessage(this.langConfig.c(NodePath.path("commands", "rc")));
                });

        final var reload = main.literal("reload", ArgumentDescription.of("Reloads the config."))
                .permission(Constants.Permissions.RELOAD)
                .handler(c -> {
                    if (this.buildersUtilities.loadConfiguration()) {
                        c.getSender().sendMessage(this.langConfig.c(NodePath.path("commands", "reload", "successful")));
                    } else {
                        c.getSender().sendMessage(this.langConfig.c(NodePath.path("commands", "reload", "unsuccessful")));
                    }
                });

        commandManager.command(menu);
        commandManager.command(rc);
        commandManager.command(reload);
    }

    // https://www.spigotmc.org/threads/getting-chunks-around-a-center-chunk-within-a-specific-radius.422279/
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
