package xyz.tehbrian.buildersutilities.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BlockUtil {

    private static final double MARCH_AMOUNT = 0.05D;
    private static final double MAX_REACH = 6.0D;
    private static final double MAX_REACH_SQUARED = Math.pow(MAX_REACH, 2);

    public static boolean isTop(final Player player, final Block block) {
        final Location ray = player.getEyeLocation().clone();
        final Vector march = player.getEyeLocation().getDirection().multiply(MARCH_AMOUNT);
        while (!ray.getBlock().equals(block) && ray.distanceSquared(player.getEyeLocation()) < MAX_REACH_SQUARED) {
            ray.add(march);
        }
        return ray.getY() % 1.0D > 0.5D;
    }

    /*
     final double y = ray.getY();
     final double internalY = Math.abs(y % 1.0D);

     if (y > 0) {
     return internalY > 0.5D;
     } else {
     return internalY < 0.5D;
     }
     */
}
