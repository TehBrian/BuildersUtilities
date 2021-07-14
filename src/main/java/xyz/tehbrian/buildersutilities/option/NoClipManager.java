package xyz.tehbrian.buildersutilities.option;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.user.User;
import xyz.tehbrian.buildersutilities.user.UserManager;
import xyz.tehbrian.buildersutilities.util.Permissions;

/*
    TODO: Clean up this code.
    Isn't checking like 10 blocks per player
    player each tick super resource intensive?
    I feel like a lot of the code is redundant.
 */
public final class NoClipManager {

    private final BuildersUtilities main;
    private final UserManager userManager;

    @Inject
    public NoClipManager(
            final @NonNull BuildersUtilities main,
            final @NonNull UserManager userManager
    ) {
        this.main = main;
        this.userManager = userManager;
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimer(this.main, this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (User user : this.userManager.getUserMap().values()) {
            if (!user.hasNoClipEnabled()) {
                continue;
            }

            Player p = user.getPlayer();
            if (p == null || !p.isOnline() || !p.hasPermission(Permissions.NO_CLIP)) {
                continue;
            }

            boolean noClip;
            boolean tp = false;
            if (p.getGameMode() == GameMode.CREATIVE) {
                if (p.isOnGround() && p.isSneaking()) {
                    noClip = true;
                } else {
                    noClip = this.shouldNoClip(p);
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
                    noClip = this.shouldNoClip(p);
                }

                if (!noClip) {
                    p.setGameMode(GameMode.CREATIVE);
                }
            }
        }
    }

    private boolean shouldNoClip(final Player p) {
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
