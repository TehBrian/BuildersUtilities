package xyz.tehbrian.buildersutilities;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import xyz.tehbrian.buildersutilities.player.User;

/*
    TODO: Clean up this code.
    Isn't checking like 10 blocks per player
    player each tick super resource intensive?
    I feel like a lot of the code is redundant.
 */
public class NoClipManager {

    private final BuildersUtilities main;

    public NoClipManager(final BuildersUtilities main) {
        this.main = main;
        Bukkit.getScheduler().runTaskTimer(main, this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (User user : this.main.getUserManager().getUserMap().values()) {
            if (!user.hasNoClipEnabled()) {
                continue;
            }

            Player p = user.getPlayer();
            if (!p.isOnline()) {
                continue;
            }

            boolean noClip;
            boolean tp = false;
            if (p.getGameMode() == GameMode.CREATIVE) {
                if (p.isOnGround() && p.isSneaking()) {
                    noClip = true;
                } else {
                    noClip = this.isNoClip(p);
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
                    noClip = this.isNoClip(p);
                }

                if (!noClip) {
                    p.setGameMode(GameMode.CREATIVE);
                }
            }
        }
    }

    private boolean isNoClip(final Player p) {
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
