package xyz.tehbrian.buildersutilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.restrictionhelper.core.ActionType;

import java.util.Objects;

@SuppressWarnings("unused")
public final class BuildingUtilitiesListener implements Listener {

    private final BuildersUtilities main;

    public BuildingUtilitiesListener(final BuildersUtilities main) {
        this.main = main;
    }

    /*
        TODO: Possibly change this behavior?
        Perhaps we should make it more similar to how wooden trapdoors work.
        For now I'm keeping it like how it is in the original BuildersUtilities
        because that's what people are used to, and change is scary.
     */
    @EventHandler(ignoreCancelled = true)
    public void onIronDoorInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!this.main.getUserManager().getUserData(player).hasIronDoorToggleEnabled()) {
            return;
        }

        Block block = Objects.requireNonNull(event.getClickedBlock());

        if (block.getType() != Material.IRON_DOOR
                || player.getInventory().getItemInMainHand().getType() != Material.AIR
                || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getHand() != EquipmentSlot.HAND
                || player.getGameMode() != GameMode.CREATIVE
                || player.isSneaking()
                || !this.main.getRestrictionHelper().checkRestrictions(player, block.getLocation(), ActionType.BREAK)
                || !this.main.getRestrictionHelper().checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
            return;
        }

        Bukkit.getScheduler().runTask(this.main, () -> {
            Door door = (Door) block.getBlockData();

            door.setOpen(!door.isOpen());

            block.setBlockData(door);
        });
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onIronTrapDoorInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!this.main.getUserManager().getUserData(player).hasIronDoorToggleEnabled()) {
            return;
        }

        Block block = Objects.requireNonNull(event.getClickedBlock());

        if (block.getType() != Material.IRON_TRAPDOOR
                || player.getInventory().getItemInMainHand().getType() != Material.AIR
                || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getHand() != EquipmentSlot.HAND
                || player.getGameMode() != GameMode.CREATIVE
                || player.isSneaking()
                || !this.main.getRestrictionHelper().checkRestrictions(player, block.getLocation(), ActionType.BREAK)
                || !this.main.getRestrictionHelper().checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
            return;
        }

        Bukkit.getScheduler().runTask(this.main, () -> {
            TrapDoor trapDoor = (TrapDoor) block.getBlockData();

            trapDoor.setOpen(!trapDoor.isOpen());

            block.setBlockData(trapDoor);
        });
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSlabBreak(final BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!this.main.getUserManager().getUserData(player).hasDoubleSlabBreakEnabled()
                || !Tag.SLABS.isTagged(player.getInventory().getItemInMainHand().getType())
                || player.getGameMode() != GameMode.CREATIVE
                || !Tag.SLABS.isTagged(event.getBlock().getType())) {
            return;
        }

        Slab blockData = (Slab) event.getBlock().getBlockData();
        if (blockData.getType() != Slab.Type.DOUBLE) {
            return;
        }

        if (this.isTop(player, event.getBlock())) {
            blockData.setType(Slab.Type.BOTTOM);
        } else {
            blockData.setType(Slab.Type.TOP);
        }

        event.getBlock().setBlockData(blockData, true);
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onGlazedTerracottaInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!this.main.getUserManager().getUserData(player).hasGlazedTerracottaRotateEnabled()) {
            return;
        }

        Block block = Objects.requireNonNull(event.getClickedBlock());

        if (!block.getType().name().toLowerCase().contains("glazed")
                || player.getInventory().getItemInMainHand().getType() != Material.AIR
                || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getHand() != EquipmentSlot.HAND
                || player.getGameMode() != GameMode.CREATIVE
                || !player.isSneaking()
                || !this.main.getRestrictionHelper().checkRestrictions(player, block.getLocation(), ActionType.BREAK)
                || !this.main.getRestrictionHelper().checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
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

    private boolean isTop(final Player player, final Block block) {
        Location start = player.getEyeLocation().clone();
        while (!start.getBlock().equals(block) && start.distance(player.getEyeLocation()) < 6.0D) {
            start.add(player.getEyeLocation().getDirection().multiply(0.05D));
        }
        return start.getY() % 1.0D > 0.5D;
    }
}
