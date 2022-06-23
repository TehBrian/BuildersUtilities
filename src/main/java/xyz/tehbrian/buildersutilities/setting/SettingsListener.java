package xyz.tehbrian.buildersutilities.setting;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;
import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.util.Permissions;

@SuppressWarnings("ClassCanBeRecord")
public final class SettingsListener implements Listener {

    private final ConfigConfig configConfig;

    @Inject
    public SettingsListener(final @NonNull ConfigConfig configConfig) {
        this.configConfig = configConfig;
    }

    @EventHandler
    public void onSpectatorTeleport(final PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE
                && !event.getPlayer().hasPermission(Permissions.SPECTATE_TELEPORT)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpectate(final PlayerStartSpectatingEntityEvent event) {
        if (event.getNewSpectatorTarget().getType() == EntityType.PLAYER) {
            if (!event.getPlayer().hasPermission(Permissions.SPECTATE_PLAYER)) {
                event.setCancelled(true);
            }
        } else {
            if (!event.getPlayer().hasPermission(Permissions.SPECTATE_ENTITY)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPhysics(final BlockPhysicsEvent event) {
        if (this.configConfig.data().settings().disablePhysics()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (this.configConfig.data().settings().disableEntityExplode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityExplode(final EntityDamageEvent event) {
        if (this.configConfig.data().settings().disableEntityExplode()) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockExplode(final BlockExplodeEvent event) {
        if (this.configConfig.data().settings().disableBlockExplode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByBlockExplode(final EntityDamageEvent event) {
        if (this.configConfig.data().settings().disableBlockExplode()
                && event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeavesDecay(final LeavesDecayEvent event) {
        if (this.configConfig.data().settings().disableLeavesDecay()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFarmlandTrample(final PlayerInteractEvent event) {
        if (this.configConfig.data().settings().disableFarmlandTrample()
                && event.getAction() == Action.PHYSICAL && event.getClickedBlock() != null
                && event.getClickedBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDragonEggTeleport(final BlockFromToEvent event) {
        if (this.configConfig.data().settings().disableDragonEggTeleport()
                && event.getBlock().getType() == Material.DRAGON_EGG) {
            event.setCancelled(true);
        }
    }

}
