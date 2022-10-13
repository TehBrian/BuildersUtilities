package xyz.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.buildersutilities.util.Permissions;
import xyz.tehbrian.restrictionhelper.core.ActionType;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;

import java.util.Objects;

/*
    TODO: change iron door toggle behavior?
    Perhaps we could make it more similar to how wooden trapdoors work.
    For now I'm keeping it like how it is in the original BuildersUtilities
    because that's what people are used to, and change is scary.
 */
public final class IronDoorListener implements Listener {

  private final BuildersUtilities buildersUtilities;
  private final UserService userService;
  private final SpigotRestrictionHelper restrictionHelper;

  @Inject
  public IronDoorListener(
      final BuildersUtilities buildersUtilities,
      final UserService userService,
      final SpigotRestrictionHelper restrictionHelper
  ) {
    this.buildersUtilities = buildersUtilities;
    this.userService = userService;
    this.restrictionHelper = restrictionHelper;
  }

  @EventHandler(ignoreCancelled = true)
  public void onIronDoorInteract(final PlayerInteractEvent event) {
    final Player player = event.getPlayer();

    if (!this.userService.getUser(player).ironDoorToggleEnabled()
        || !player.hasPermission(Permissions.IRON_DOOR_TOGGLE)) {
      return;
    }

    final Block block = Objects.requireNonNull(event.getClickedBlock());

    if (block.getType() != Material.IRON_DOOR
        || player.getInventory().getItemInMainHand().getType() != Material.AIR
        || event.getAction() != Action.RIGHT_CLICK_BLOCK
        || event.getHand() != EquipmentSlot.HAND
        || player.getGameMode() != GameMode.CREATIVE
        || player.isSneaking()
        || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.BREAK)
        || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
      return;
    }

    Bukkit.getScheduler().runTask(this.buildersUtilities, () -> {
      final Door door = (Door) block.getBlockData();
      final boolean newState = !door.isOpen();

      door.setOpen(newState);
      block.setBlockData(door);

      block.getWorld().playSound(
          block.getLocation(),
          newState ? Sound.BLOCK_IRON_DOOR_OPEN : Sound.BLOCK_IRON_DOOR_CLOSE,
          SoundCategory.BLOCKS,
          1F, 1F
      );
    });

    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true)
  public void onIronTrapDoorInteract(final PlayerInteractEvent event) {
    final Player player = event.getPlayer();

    if (!this.userService.getUser(player).ironDoorToggleEnabled()
        || !player.hasPermission(Permissions.IRON_DOOR_TOGGLE)) {
      return;
    }

    final Block block = Objects.requireNonNull(event.getClickedBlock());

    if (block.getType() != Material.IRON_TRAPDOOR
        || player.getInventory().getItemInMainHand().getType() != Material.AIR
        || event.getAction() != Action.RIGHT_CLICK_BLOCK
        || event.getHand() != EquipmentSlot.HAND
        || player.getGameMode() != GameMode.CREATIVE
        || player.isSneaking()
        || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.BREAK)
        || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
      return;
    }

    Bukkit.getScheduler().runTask(this.buildersUtilities, () -> {
      final TrapDoor trapDoor = (TrapDoor) block.getBlockData();
      final boolean newState = !trapDoor.isOpen();

      trapDoor.setOpen(newState);
      block.setBlockData(trapDoor);

      block.getWorld().playSound(
          block.getLocation(),
          newState ? Sound.BLOCK_IRON_TRAPDOOR_OPEN : Sound.BLOCK_IRON_TRAPDOOR_CLOSE,
          SoundCategory.BLOCKS,
          1F, 1F
      );
    });

    event.setCancelled(true);
  }

}
