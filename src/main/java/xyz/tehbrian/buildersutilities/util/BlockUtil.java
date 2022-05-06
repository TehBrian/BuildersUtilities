package xyz.tehbrian.buildersutilities.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BlockUtil {

    private static final double MARCH_AMOUNT = 0.05D;
    private static final double MAX_REACH = 6.0D;
    private static final double MAX_REACH_SQUARED = Math.pow(MAX_REACH, 2);

    private BlockUtil() {
    }

    /**
     * Calculates which half of the block the player is facing.
     *
     * @param player the player
     * @param block  the block
     * @return the half of the block
     */
    public static @NonNull Half getBlockHalfPlayerFacing(final Player player, final Block block) {
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

}
