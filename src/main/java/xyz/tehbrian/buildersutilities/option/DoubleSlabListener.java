package xyz.tehbrian.buildersutilities.option;

import com.google.inject.Inject;
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
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.user.UserService;

@SuppressWarnings("unused")
public final class DoubleSlabListener implements Listener {

    private final UserService userService;

    @Inject
    public DoubleSlabListener(
            final @NonNull UserService userService
    ) {
        this.userService = userService;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDoubleSlabBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        if (!this.userService.getUser(player).doubleSlabBreakEnabled()
                || !player.hasPermission(Constants.Permissions.DOUBLE_SLAB_BREAK)
                || !Tag.SLABS.isTagged(player.getInventory().getItemInMainHand().getType())
                || player.getGameMode() != GameMode.CREATIVE
                || !Tag.SLABS.isTagged(block.getType())) {
            return;
        }

        final Slab blockData = (Slab) block.getBlockData();
        if (blockData.getType() != Slab.Type.DOUBLE) {
            return;
        }

        if (this.isTop(player, block)) {
            blockData.setType(Slab.Type.BOTTOM);
        } else {
            blockData.setType(Slab.Type.TOP);
        }

        block.setBlockData(blockData, true);
        event.setCancelled(true);
    }

    private boolean isTop(final Player player, final Block block) {
        final Location ray = player.getEyeLocation().clone();
        final Vector marchAmount = player.getEyeLocation().getDirection().multiply(0.05D);
        while (!ray.getBlock().equals(block) && ray.distance(player.getEyeLocation()) < 6.0D) {
            ray.add(marchAmount);
        }
        return ray.getY() % 1.0D > 0.5D;
    }

}
