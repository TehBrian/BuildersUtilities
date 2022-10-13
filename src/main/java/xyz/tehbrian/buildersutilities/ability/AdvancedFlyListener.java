package xyz.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.buildersutilities.util.Permissions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
    TODO: clean up/refactor this.
    I'm going to be honest here, I don't have a clue
    on how to make this better. It looks like some kind of
    black magic to me. If it ain't broke don't fix it, right?
 */
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
    final Location to = Objects.requireNonNull(event.getTo()).clone();

    final Double speed = from.add(0, -event.getFrom().getY(), 0)
        .distance(to.add(0, -event.getTo().getY(), 0));

    if (Math.abs(event.getFrom().getYaw() - event.getTo().getYaw()) > 5
        || Math.abs(event.getFrom().getPitch() - event.getTo().getPitch()) > 5) {
      return;
    }

    if (this.lastVelocity.containsKey(player)) {
      final Double lastSpeed = this.lastVelocity.get(player);
      if (speed * 1.3 < lastSpeed) {
        if (this.slower.contains(player)) {
          if (this.slower2.contains(player)) {
            final Vector v = player.getVelocity().clone();
            v.setX(0);
            v.setZ(0);
            player.setVelocity(v);
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
