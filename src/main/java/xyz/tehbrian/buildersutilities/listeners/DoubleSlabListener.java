package xyz.tehbrian.buildersutilities.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

@SuppressWarnings("unused")
public final class DoubleSlabListener implements Listener {

    private final BuildersUtilities main;

    public DoubleSlabListener(final BuildersUtilities main) {
        this.main = main;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDoubleSlabBreak(final BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!this.main.getUserManager().getUserData(player).hasDoubleSlabBreakEnabled()
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
