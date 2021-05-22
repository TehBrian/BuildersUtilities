package xyz.tehbrian.buildersutilities.listeners;

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
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.user.UserManager;

@SuppressWarnings("unused")
public final class DoubleSlabListener implements Listener {

    private final UserManager userManager;

    @Inject
    public DoubleSlabListener(
            final @NonNull UserManager userManager
    ) {
        this.userManager = userManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDoubleSlabBreak(final BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!this.userManager.getUser(player).hasDoubleSlabBreakEnabled()
                || !player.hasPermission("buildersutilities.doubleslabbreak")
                || !Tag.SLABS.isTagged(player.getInventory().getItemInMainHand().getType())
                || player.getGameMode() != GameMode.CREATIVE
                || !Tag.SLABS.isTagged(event.getBlock().getType())) {
            return;
        }

        Slab blockData = (Slab) event.getBlock().getBlockData();
        if (blockData.getType() != Slab.Type.DOUBLE) {
            return;
        }

        if (this.isTop(player, event.getBlock())) {
            blockData.setType(Slab.Type.BOTTOM);
        } else {
            blockData.setType(Slab.Type.TOP);
        }

        event.getBlock().setBlockData(blockData, true);
        event.setCancelled(true);
    }

    private boolean isTop(final Player player, final Block block) {
        Location start = player.getEyeLocation().clone();
        while (!start.getBlock().equals(block) && start.distance(player.getEyeLocation()) < 6.0D) {
            start.add(player.getEyeLocation().getDirection().multiply(0.05D));
        }
        return start.getY() % 1.0D > 0.5D;
    }
}
