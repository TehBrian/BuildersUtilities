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
        final Location eyeLoc = player.getEyeLocation();
        final Location ray = eyeLoc.clone();
        final Vector march = eyeLoc.getDirection().multiply(MARCH_AMOUNT);

        while (!ray.getBlock().equals(block) && ray.distanceSquared(eyeLoc) < MAX_REACH_SQUARED) {
            ray.add(march);
        }

        final double y = ray.getY();
        return Math.round(y) > y;
    }

}
