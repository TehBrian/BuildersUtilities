package xyz.tehbrian.buildersutilities.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import xyz.tehbrian.buildersutilities.managers.PlayerOptionsManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
    TODO: Clean up this code.
    I'm going to be honest here, I don't have a clue
    on how to make this better. It looks like some kind of
    black magic to me. If it ain't broke don't fix it, right?
 */
@SuppressWarnings("unused")
public class AdvancedFlyListener implements Listener {

    private final Set<Player> slower = new HashSet<>();
    private final Set<Player> slower2 = new HashSet<>();
    private final HashMap<Player, Double> lastVelocity = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!PlayerOptionsManager.getEnabledAdvancedFly(player)) return;
        if (!player.isFlying()) return;

        Double speed = event.getFrom().clone().add(0, -event.getFrom().getY(), 0).distance(Objects.requireNonNull(event.getTo()).clone().add(0, -event.getTo().getY(), 0));

        if (Math.abs(event.getFrom().getYaw() - event.getTo().getYaw()) > 5) return;
        if (Math.abs(event.getFrom().getPitch() - event.getTo().getPitch()) > 5) return;

        if (lastVelocity.containsKey(player)) {
            Double lastSpeed = lastVelocity.get(player);
            if (speed * 1.3 < lastSpeed) {
                if (slower.contains(player)) {
                    if (slower2.contains(player)) {
                        Vector v = player.getVelocity().clone();
                        v.setX(0);
                        v.setZ(0);
                        player.setVelocity(v);
                        lastVelocity.put(player, 0.0);
                        slower.remove(player);
                        slower2.remove(player);
                    } else {
                        slower2.add(player);
                    }
                } else {
                    slower.add(player);
                }
            } else if (speed > lastSpeed) {
                lastVelocity.put(player, speed);
                slower.remove(player);
                slower2.remove(player);
            }
        } else {
            lastVelocity.put(player, speed);
            slower.remove(player);
        }
    }
}
