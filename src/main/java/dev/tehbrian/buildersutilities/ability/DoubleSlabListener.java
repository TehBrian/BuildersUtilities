package dev.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.GameMode;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public final class DoubleSlabListener implements Listener {

  private final UserService userService;

  @Inject
  public DoubleSlabListener(
      final UserService userService
  ) {
    this.userService = userService;
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

    if (BlockUtil.getBlockHalfPlayerFacing(player, block) == Half.TOP) {
      blockData.setType(Slab.Type.BOTTOM);
    } else {
      blockData.setType(Slab.Type.TOP);
    }

    block.setBlockData(blockData, true);
    event.setCancelled(true);
  }

}
