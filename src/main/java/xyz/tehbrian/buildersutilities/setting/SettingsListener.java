package xyz.tehbrian.buildersutilities.setting;

import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;

@SuppressWarnings("unused")
public final class SettingsListener implements Listener {

    private final ConfigConfig config;

    @Inject
    public SettingsListener(final @NonNull ConfigConfig config) {
        this.config = config;
    }

    @EventHandler
    public void onSpectatorTeleport(final PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
            if (!event.getPlayer().hasPermission(Constants.Permissions.TPGM3)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPhysics(final BlockPhysicsEvent event) {
        if (this.config.settings().disablePhysics()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (this.config.settings().disableEntityExplode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityExplode(final EntityDamageEvent event) {
        if (this.config.settings().disableEntityExplode()) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockExplode(final BlockExplodeEvent event) {
        if (this.config.settings().disableBlockExplode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByBlockExplode(final EntityDamageEvent event) {
        if (this.config.settings().disableBlockExplode()) {
            if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeavesDecay(final LeavesDecayEvent event) {
        if (this.config.settings().disableLeavesDecay()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFarmlandTrample(final PlayerInteractEvent event) {
        if (this.config.settings().disableFarmlandTrample()) {
            if (event.getAction() == Action.PHYSICAL) {
                if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FARMLAND) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDragonEggTeleport(final BlockFromToEvent event) {
        if (this.config.settings().disableDragonEggTeleport()) {
            if (event.getBlock().getType() == Material.DRAGON_EGG) {
                event.setCancelled(true);
            }
        }
    }

}
