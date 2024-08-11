package dev.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

public final class DoubleSlabListener implements Listener {

	private static final double MARCH_AMOUNT = 0.05D;
	private static final double MAX_REACH = 6.0D;
	private static final double MAX_REACH_SQUARED = Math.pow(MAX_REACH, 2);

	private final UserService userService;

	@Inject
	public DoubleSlabListener(
			final UserService userService
	) {
		this.userService = userService;
	}

	private static Half getBlockHalfPlayerFacing(final Player player, final Block block) {
		final Location eyeLoc = player.getEyeLocation();
		final Location ray = eyeLoc.clone();
		final Vector march = eyeLoc.getDirection().multiply(MARCH_AMOUNT);

		while (!ray.getBlock().equals(block) && ray.distanceSquared(eyeLoc) < MAX_REACH_SQUARED) {
			ray.add(march);
		}

		final double y = ray.getY();
		if (Math.round(y) > y) {
			return Half.TOP;
		} else {
			return Half.BOTTOM;
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onDoubleSlabBreak(final BlockBreakEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getBlock();

		if (!this.userService.getUser(player).doubleSlabBreakEnabled()
				|| !player.hasPermission(Permissions.DOUBLE_SLAB_BREAK)
				|| !Tag.SLABS.isTagged(player.getInventory().getItemInMainHand().getType())
				|| player.getGameMode() != GameMode.CREATIVE
				|| !Tag.SLABS.isTagged(block.getType())) {
			return;
		}

		final Slab blockData = (Slab) block.getBlockData();
		if (blockData.getType() != Slab.Type.DOUBLE) {
			return;
		}

		if (getBlockHalfPlayerFacing(player, block) == Half.TOP) {
			blockData.setType(Slab.Type.BOTTOM);
		} else {
			blockData.setType(Slab.Type.TOP);
		}

		block.setBlockData(blockData, true);
		event.setCancelled(true);
	}

	public enum Half {
		TOP,
		BOTTOM,
	}

}
