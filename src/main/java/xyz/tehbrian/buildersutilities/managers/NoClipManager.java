package xyz.tehbrian.buildersutilities.managers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.Objects;
import java.util.UUID;

/*
    TODO: Clean up this code.
    Isn't checking like 10 blocks per player
    player each tick super resource intensive?
    I feel like a lot of the code is redundant.
 */
public class NoClipManager {

    public NoClipManager(BuildersUtilities main) {
        Bukkit.getScheduler().runTaskTimer(main, this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (UUID uuid : PlayerOptionsManager.getEnabledNoClipList()) {
            Player p = Objects.requireNonNull(Bukkit.getPlayer(uuid));
            if (!p.isOnline()) return;

            boolean noClip;
            boolean tp = false;
            if (p.getGameMode() == GameMode.CREATIVE) {
                if (p.isOnGround() && p.isSneaking()) {
                    noClip = true;
                } else {
                    noClip = isNoClip(p);
                    if (p.isOnGround()) {
                        tp = true;
                    }
                }

                if (noClip) {
                    p.setGameMode(GameMode.SPECTATOR);
                    if (tp) {
                        p.teleport(p.getLocation());
                    }
                }
            } else if (p.getGameMode() == GameMode.SPECTATOR) {
                if (p.isOnGround()) {
                    noClip = true;
                } else {
                    noClip = isNoClip(p);
                }

                if (!noClip) {
                    p.setGameMode(GameMode.CREATIVE);
                }
            }
        }
    }

    private boolean isNoClip(Player p) {
        return p.getLocation().add(+0.4, 0, 0).getBlock().getType().isSolid()
                || p.getLocation().add(-0.4, 0, 0).getBlock().getType().isSolid()
                || p.getLocation().add(0, 0, +0.4).getBlock().getType().isSolid()
                || p.getLocation().add(0, 0, -0.4).getBlock().getType().isSolid()
                || p.getLocation().add(+0.4, 1, 0).getBlock().getType().isSolid()
                || p.getLocation().add(-0.4, 1, 0).getBlock().getType().isSolid()
                || p.getLocation().add(0, 1, +0.4).getBlock().getType().isSolid()
                || p.getLocation().add(0, 1, -0.4).getBlock().getType().isSolid()
                || p.getLocation().add(0, +1.9, 0).getBlock().getType().isSolid();
    }
}
