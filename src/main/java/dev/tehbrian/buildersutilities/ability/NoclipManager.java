package dev.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.BuildersUtilities;
import dev.tehbrian.buildersutilities.user.User;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public final class NoclipManager {

  private final BuildersUtilities buildersUtilities;
  private final UserService userService;

  @Inject
  public NoclipManager(
      final BuildersUtilities buildersUtilities,
      final UserService userService
  ) {
    this.buildersUtilities = buildersUtilities;
    this.userService = userService;
  }

  public void start() {
    Bukkit.getScheduler().runTaskTimer(this.buildersUtilities, this::checkForBlocks, 1L, 1L);
  }

  @SuppressWarnings("deprecation") // no alternative to Player#isOnGround
  private void checkForBlocks() {
    for (final User user : this.userService.getUserMap().values()) {
      if (!user.noclipEnabled()) {
        continue;
      }

      final Player p = user.getPlayer();
      if (p == null || !p.isOnline() || !p.hasPermission(Permissions.NOCLIP)) {
        continue;
      }

      final boolean noclip;
      boolean tp = false;
      if (p.getGameMode() == GameMode.CREATIVE) {
        if (p.isOnGround() && p.isSneaking()) {
          noclip = true;
        } else {
          noclip = this.shouldNoclip(p);
          if (p.isOnGround()) {
            tp = true;
          }
        }

        if (noclip) {
          p.setGameMode(GameMode.SPECTATOR);
          if (tp) {
            p.teleport(p.getLocation());
          }
        }
      } else if (p.getGameMode() == GameMode.SPECTATOR) {
        if (p.isOnGround()) {
          noclip = true;
        } else {
          noclip = this.shouldNoclip(p);
        }

        if (!noclip) {
          p.setGameMode(GameMode.CREATIVE);
        }
      }
    }
  }

  private boolean shouldNoclip(final Player p) {
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
