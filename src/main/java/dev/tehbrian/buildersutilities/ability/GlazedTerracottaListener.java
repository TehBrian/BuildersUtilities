package dev.tehbrian.buildersutilities.ability;

import com.destroystokyo.paper.MaterialTags;
import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import dev.tehbrian.restrictionhelper.core.ActionType;
import dev.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public final class GlazedTerracottaListener implements Listener {

  private final UserService userService;
  private final SpigotRestrictionHelper restrictionHelper;

  @Inject
  public GlazedTerracottaListener(
      final UserService userService,
      final SpigotRestrictionHelper restrictionHelper
  ) {
    this.userService = userService;
    this.restrictionHelper = restrictionHelper;
  }

  @EventHandler(ignoreCancelled = true)
  public void onGlazedTerracottaInteract(final PlayerInteractEvent event) {
    final Player player = event.getPlayer();

    if (!this.userService.getUser(player).glazedTerracottaRotateEnabled()
        || !player.hasPermission(Permissions.GLAZED_TERRACOTTA_ROTATE)) {
      return;
    }

    final Block block = Objects.requireNonNull(event.getClickedBlock());

    if (!MaterialTags.GLAZED_TERRACOTTA.isTagged(block)
        || player.getInventory().getItemInMainHand().getType() != Material.AIR
        || event.getAction() != Action.RIGHT_CLICK_BLOCK
        || event.getHand() != EquipmentSlot.HAND
        || player.getGameMode() != GameMode.CREATIVE
        || !player.isSneaking()
        || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.BREAK)
        || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
      return;
    }

    final Directional directional = (Directional) block.getBlockData();
    directional.setFacing(switch (directional.getFacing()) {
      case NORTH -> BlockFace.EAST;
      case EAST -> BlockFace.SOUTH;
      case SOUTH -> BlockFace.WEST;
      case WEST -> BlockFace.NORTH;
      default -> directional.getFacing(); // do nothing.
    });
    block.setBlockData(directional);

    block.getWorld().playSound(
        block.getLocation(),
        Sound.BLOCK_STONE_PLACE,
        SoundCategory.BLOCKS,
        1F, 2F
    );

    player.swingMainHand();

    event.setCancelled(true);
  }

}
