package xyz.tehbrian.buildersutilities.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BlockUtil {

    public static boolean isTop(final Player player, final Block block) {
        final Location ray = player.getEyeLocation().clone();
        final Vector marchAmount = player.getEyeLocation().getDirection().multiply(0.05D);
        while (!ray.getBlock().equals(block) && ray.distance(player.getEyeLocation()) < 6.0D) {
            ray.add(marchAmount);
        }
        return ray.getY() % 1.0D > 0.5D;
    }

}
