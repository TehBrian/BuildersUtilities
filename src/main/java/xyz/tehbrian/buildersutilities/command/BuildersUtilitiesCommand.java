package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudCommand;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.ability.AbilityMenuProvider;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.config.SpecialConfig;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.buildersutilities.util.ChestSize;
import xyz.tehbrian.buildersutilities.util.Permissions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class BuildersUtilitiesCommand extends PaperCloudCommand<CommandSender> {

    private final BuildersUtilities buildersUtilities;
    private final UserService userService;
    private final LangConfig langConfig;
    private final AbilityMenuProvider abilityMenuProvider;
    private final SpecialConfig specialConfig;

    @Inject
    public BuildersUtilitiesCommand(
            final @NonNull BuildersUtilities buildersUtilities,
            final @NonNull UserService userService,
            final @NonNull LangConfig langConfig,
            final @NonNull AbilityMenuProvider abilityMenuProvider,
            final @NonNull SpecialConfig specialConfig
    ) {
        this.buildersUtilities = buildersUtilities;
        this.userService = userService;
        this.langConfig = langConfig;
        this.abilityMenuProvider = abilityMenuProvider;
        this.specialConfig = specialConfig;
    }

    @Override
    public void register(final @NonNull PaperCommandManager<CommandSender> commandManager) {
        final var root = commandManager.commandBuilder("buildersutilities", "butils", "bu");

        final var ability = root
                .meta(CommandMeta.DESCRIPTION, "Opens the ability menu.")
                .permission(Permissions.ABILITY)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    sender.openInventory(this.abilityMenuProvider.generate(this.userService.getUser(sender)));
                });

        final var rc = root.literal("rc", ArgumentDescription.of("Reloads the chunks around you."))
                .permission(Permissions.RC)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    final Collection<Chunk> chunksToReload = this.around(sender.getLocation().getChunk(), sender.getClientViewDistance());

                    // ChunkMap#playerLoadedChunk was an invaluable resource in porting this to 1.18
                    final ServerPlayer nmsPlayer = ((CraftPlayer) sender).getHandle();
                    for (final Chunk chunk : chunksToReload) {
                        final var nmsChunk = ((CraftChunk) chunk).getHandle();
                        final var packet = new ClientboundLevelChunkWithLightPacket(
                                nmsChunk, nmsChunk.getLevel().getLightEngine(),
                                null, null, true, false
                        );
                        nmsPlayer.trackChunk(nmsChunk.getPos(), packet);
                    }

                    sender.sendMessage(this.langConfig.c(NodePath.path("commands", "rc")));
                });

        final var reload = root.literal("reload", ArgumentDescription.of("Reloads the config."))
                .permission(Permissions.RELOAD)
                .handler(c -> {
                    if (this.buildersUtilities.loadConfiguration()) {
                        c.getSender().sendMessage(this.langConfig.c(NodePath.path("commands", "reload", "success")));
                    } else {
                        c.getSender().sendMessage(this.langConfig.c(NodePath.path("commands", "reload", "failure")));
                    }
                });

        final var special = root.literal("special", ArgumentDescription.of("Opens the special items menu."))
                .permission(Permissions.SPECIAL)
                .senderType(Player.class)
                .handler(c -> {
                    final var sender = (Player) c.getSender();

                    final Inventory inv = Bukkit.createInventory(
                            null,
                            ChestSize.SINGLE,
                            this.langConfig.c(NodePath.path("menus", "special", "inventory-name"))
                    );

                    for (final ItemStack item : this.specialConfig.items()) {
                        inv.addItem(item);
                    }

                    sender.openInventory(inv);
                });

        commandManager.command(ability);
        commandManager.command(rc);
        commandManager.command(reload);
        commandManager.command(special);
    }

    // https://www.spigotmc.org/threads/getting-chunks-around-a-center-chunk-within-a-specific-radius.422279/
    private Collection<Chunk> around(final Chunk origin, final int radius) {
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
