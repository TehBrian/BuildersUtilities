package xyz.tehbrian.buildersutilities.listeners;

import org.bukkit.Material;
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
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.Objects;

@SuppressWarnings("unused")
public final class SettingsListener implements Listener {

    private final BuildersUtilities main;

    public SettingsListener(final BuildersUtilities main) {
        this.main = main;
    }

    @EventHandler
    public void onSpectatorTeleport(final PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
            if (!event.getPlayer().hasPermission("buildersutilities.tpgm3")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPhysics(final BlockPhysicsEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_physics")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_entity_explode")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityExplode(final EntityDamageEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_entity_explode")) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockExplode(final BlockExplodeEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_block_explode")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByBlockExplode(final EntityDamageEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_block_explode")) {
            if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeavesDecay(final LeavesDecayEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_leaves_decay")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFarmlandTrample(final PlayerInteractEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_farmland_trample")) {
            if (event.getAction() == Action.PHYSICAL) {
                if (Objects.requireNonNull(event.getClickedBlock()).getType() == Material.FARMLAND) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDragonEggTeleport(final BlockFromToEvent event) {
        if (this.main.getConfig().getBoolean("settings.disable_dragon_egg_teleport")) {
            if (event.getBlock().getType() == Material.DRAGON_EGG) {
                event.setCancelled(true);
            }
        }
    }
}
