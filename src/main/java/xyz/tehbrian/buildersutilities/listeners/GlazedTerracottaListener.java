package xyz.tehbrian.buildersutilities.listeners;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
import xyz.tehbrian.buildersutilities.user.UserManager;
import xyz.tehbrian.restrictionhelper.bukkit.BukkitRestrictionHelper;
import xyz.tehbrian.restrictionhelper.core.ActionType;

import java.util.Objects;

@SuppressWarnings("unused")
public final class GlazedTerracottaListener implements Listener {

    private final BuildersUtilities main;
    private final UserManager userManager;
    private final BukkitRestrictionHelper restrictionHelper;

    @Inject
    public GlazedTerracottaListener(
            final @NonNull BuildersUtilities main,
            final @NonNull UserManager userManager,
            final @NonNull BukkitRestrictionHelper restrictionHelper
    ) {
        this.main = main;
        this.userManager = userManager;
        this.restrictionHelper = restrictionHelper;
    }

    @EventHandler(ignoreCancelled = true)
    public void onGlazedTerracottaInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!this.userManager.getUser(player).hasGlazedTerracottaRotateEnabled()) {
            return;
        }

        Block block = Objects.requireNonNull(event.getClickedBlock());

        if (!block.getType().name().toLowerCase().contains("glazed")
                || player.getInventory().getItemInMainHand().getType() != Material.AIR
                || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getHand() != EquipmentSlot.HAND
                || player.getGameMode() != GameMode.CREATIVE
                || !player.isSneaking()
                || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.BREAK)
                || !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
            return;
        }

        Bukkit.getScheduler().runTask(this.main, () -> {
            Directional directional = (Directional) block.getBlockData();

            switch (directional.getFacing()) {
                case NORTH:
                    directional.setFacing(BlockFace.EAST);
                    break;
                case EAST:
                    directional.setFacing(BlockFace.SOUTH);
                    break;
                case SOUTH:
                    directional.setFacing(BlockFace.WEST);
                    break;
                case WEST:
                    directional.setFacing(BlockFace.NORTH);
                    break;
                default:
                    break;
            }

            block.setBlockData(directional);
        });
        event.setCancelled(true);
    }
}
