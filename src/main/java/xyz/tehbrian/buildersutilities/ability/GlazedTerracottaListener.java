package xyz.tehbrian.buildersutilities.ability;

import com.destroystokyo.paper.MaterialTags;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
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
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.buildersutilities.util.Permissions;
import xyz.tehbrian.restrictionhelper.core.ActionType;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;

import java.util.Objects;

public final class GlazedTerracottaListener implements Listener {

  private final BuildersUtilities buildersUtilities;
  private final UserService userService;
  private final SpigotRestrictionHelper restrictionHelper;

  @Inject
  public GlazedTerracottaListener(
      final @NonNull BuildersUtilities buildersUtilities,
      final @NonNull UserService userService,
      final @NonNull SpigotRestrictionHelper restrictionHelper
  ) {
    this.buildersUtilities = buildersUtilities;
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

    Bukkit.getScheduler().runTask(this.buildersUtilities, () -> {
      final Directional directional = (Directional) block.getBlockData();

      directional.setFacing(switch (directional.getFacing()) {
        case NORTH -> BlockFace.EAST;
        case EAST -> BlockFace.SOUTH;
        case SOUTH -> BlockFace.WEST;
        case WEST -> BlockFace.NORTH;
        default -> directional.getFacing(); // do nothing
      });

      block.setBlockData(directional);

      block.getWorld().playSound(
          block.getLocation(),
          Sound.BLOCK_STONE_PLACE,
          SoundCategory.MASTER,
          1F, 2F
      );
    });
    event.setCancelled(true);
  }

}
