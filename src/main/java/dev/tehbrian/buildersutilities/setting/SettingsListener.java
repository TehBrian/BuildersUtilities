package dev.tehbrian.buildersutilities.setting;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;
import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
import org.slf4j.Logger;

public final class SettingsListener implements Listener {

  private final ConfigConfig configConfig;
  private final Logger logger;

  @Inject
  public SettingsListener(
      final ConfigConfig configConfig,
      final Logger logger
  ) {
    this.configConfig = configConfig;
    this.logger = logger;
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
    final Block block = event.getBlock();

    if (event.getSourceBlock().getType() == Material.AIR
        && event.getChangedType() == Material.AIR
        && block.getLocation().getBlockY() > 0
        && block.getLocation().add(0, -1, 0).getBlock().getType().name().toLowerCase().contains("grass_block")) {
      return;
    }

    if (event.getSourceBlock().getType().name().toLowerCase().contains("snow")
        && block.getLocation().getBlockY() > 0
        && block.getLocation().add(0, -1, 0).getBlock().getType().name().toLowerCase().contains("grass_block")) {
      return;
    }

    final String lowerName = event.getChangedType().name().toLowerCase();

    if (lowerName.contains("chest") ||
        lowerName.contains("stair") ||
        lowerName.contains("fence") ||
        lowerName.contains("pane") ||
        lowerName.contains("wall") ||
        lowerName.contains("bar") ||
        lowerName.contains("door")) {
      return;
    }

    if (!this.configConfig.data().settings().disableRedstone()) {
      if (lowerName.contains("redstone") ||
          lowerName.contains("daylight") ||
          lowerName.contains("diode") ||
          lowerName.contains("note") ||
          lowerName.contains("lever") ||
          lowerName.contains("button") ||
          lowerName.contains("command") ||
          lowerName.contains("tripwire") ||
          lowerName.contains("plate") ||
          lowerName.contains("string") ||
          lowerName.contains("piston") ||
          lowerName.contains("observer")) {
        if (!block.getType().name().contains("air")) {
          return;
        }
      }
    }

    if (event.getChangedType().hasGravity()) {
      if (this.configConfig.data().settings().disableGravityPhysics()) {
        event.setCancelled(true);
        this.logger.debug("Gravity physics were cancelled because disable-gravity-physics: true");
      }
    } else {
      if (this.configConfig.data().settings().disablePhysics()) {
        event.setCancelled(true);
        this.logger.debug("Physics were cancelled because disable-physics: true");
      }
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
