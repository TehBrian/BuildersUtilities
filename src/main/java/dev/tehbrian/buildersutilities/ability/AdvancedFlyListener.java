package dev.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class AdvancedFlyListener implements Listener {

  private final UserService userService;

  private final Set<Player> slower = new HashSet<>();
  private final Set<Player> slower2 = new HashSet<>();
  private final HashMap<Player, Double> lastVelocity = new HashMap<>();

  @Inject
  public AdvancedFlyListener(
      final UserService userService
  ) {
    this.userService = userService;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerMove(final PlayerMoveEvent event) {
    final Player player = event.getPlayer();
    if (!this.userService.getUser(player).advancedFlyEnabled()
        || !player.hasPermission(Permissions.ADVANCED_FLY)
        || !player.isFlying()) {
      return;
    }

    final Location from = event.getFrom().clone();
    final Location to = event.getTo().clone();

    if (Math.abs(from.getYaw() - to.getYaw()) > 5
        || Math.abs(from.getPitch() - to.getPitch()) > 5) {
      return;
    }

    from.setY(0);
    to.setY(0);
    final Double speed = from.distance(to);

    if (this.lastVelocity.containsKey(player)) {
      final Double lastSpeed = this.lastVelocity.get(player);
      if (speed * 1.3 < lastSpeed) {
        if (this.slower.contains(player)) {
          if (this.slower2.contains(player)) {
            player.setVelocity(player.getVelocity().setX(0).setZ(0));
            this.lastVelocity.put(player, 0.0);
            this.slower.remove(player);
            this.slower2.remove(player);
          } else {
            this.slower2.add(player);
          }
        } else {
          this.slower.add(player);
        }
      } else if (speed > lastSpeed) {
        this.lastVelocity.put(player, speed);
        this.slower.remove(player);
        this.slower2.remove(player);
      }
    } else {
      this.lastVelocity.put(player, speed);
      this.slower.remove(player);
    }
  }

}
