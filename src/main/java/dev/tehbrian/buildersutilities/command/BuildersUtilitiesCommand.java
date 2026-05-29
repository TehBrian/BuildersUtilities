package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.BuildersUtilities;
import dev.tehbrian.buildersutilities.ability.AbilityMenuProvider;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.config.SpecialConfig;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.spongepowered.configurate.NodePath;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.incendo.cloud.description.Description.description;

public final class BuildersUtilitiesCommand {

	private final BuildersUtilities buildersUtilities;
	private final UserService userService;
	private final LangConfig langConfig;
	private final AbilityMenuProvider abilityMenuProvider;
	private final SpecialConfig specialConfig;

	@Inject
	public BuildersUtilitiesCommand(
			final BuildersUtilities buildersUtilities,
			final UserService userService,
			final LangConfig langConfig,
			final AbilityMenuProvider abilityMenuProvider,
			final SpecialConfig specialConfig
	) {
		this.buildersUtilities = buildersUtilities;
		this.userService = userService;
		this.langConfig = langConfig;
		this.abilityMenuProvider = abilityMenuProvider;
		this.specialConfig = specialConfig;
	}

	private static int min(final int a, final int b, final int c) {
		return Math.min(Math.min(a, b), c);
	}

	// https://www.spigotmc.org/threads/getting-chunks-around-a-center-chunk-within-a-specific-radius.422279/
	private static Collection<Chunk> around(final Chunk origin, final int radius) {
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

	public void register(final PaperCommandManager<Source> commandManager) {
		final var root = commandManager.commandBuilder("buildersutilities", "butils", "bu");

		final var ability = root
				.commandDescription(description("Opens the ability menu."))
				.permission(Permissions.ABILITY)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					sender.openInventory(this.abilityMenuProvider.generate(this.userService.getUser(sender)));
				});

		final var rc = root.literal("rc", description("Reloads the chunks around you."))
				.permission(Permissions.RC)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();

					final Collection<Chunk> chunksToReload = around(
							sender.getLocation().getChunk(),
							min(8, sender.getViewDistance(), sender.getServer().getViewDistance())
					);

					final ServerPlayer nmsPlayer = ((CraftPlayer) sender).getHandle();
					final ServerLevel nmsLevel = ((CraftWorld) sender.getWorld()).getHandle();
					for (final Chunk chunk : chunksToReload) {
						final ChunkPos nmsChunkPos = new ChunkPos(chunk.getX(), chunk.getZ());
						final ChunkHolder nmsChunk = nmsLevel.getChunkSource().chunkMap.getVisibleChunkIfPresent(nmsChunkPos.longKey());
						if (nmsChunk == null) {
							// chunk isn't loaded. no need to worry about it.
							continue;
						}
						final LevelChunk nmsChunkToSend = nmsChunk.getChunkToSend();
						if (nmsChunkToSend == null) {
							continue;
						}

						final var packet = new ClientboundLevelChunkWithLightPacket(
								nmsChunkToSend,
								nmsLevel.getLightEngine(),
								null, null, false
						);
						nmsPlayer.connection.send(packet);
					}

					sender.sendMessage(this.langConfig.c(NodePath.path("commands", "rc")));
				});

		final var reload = root.literal("reload", description("Reloads the config."))
				.permission(Permissions.RELOAD)
				.handler(c -> {
					if (this.buildersUtilities.loadConfiguration()) {
						c.sender().source().sendMessage(this.langConfig.c(NodePath.path("commands", "reload", "success")));
					} else {
						c.sender().source().sendMessage(this.langConfig.c(NodePath.path("commands", "reload", "failure")));
					}
				});

		final var special = root.literal("special", description("Opens the special items menu."))
				.permission(Permissions.SPECIAL)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();

					final int slotsRequired = (int) Math.ceil(this.specialConfig.items().size() / 9.0) * 9;
					final Inventory inv = Bukkit.createInventory(
							null,
							Math.max(Math.min(slotsRequired, 54), 9),
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

}
