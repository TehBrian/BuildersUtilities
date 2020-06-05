package xyz.tehbrian.buildersutilities.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
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

import java.util.Objects;

@SuppressWarnings("unused")
public class BuildingUtilitiesListener implements Listener {

    private final BuildersUtilities main;

    public BuildingUtilitiesListener(BuildersUtilities main) {
        this.main = main;
    }

    /*
        TODO: Possibly change this behavior?
        Perhaps we should make it more similar to how wooden trapdoors work.
        For now I'm keeping it like how it is in the original BuildersUtilities
        because that's what people are used to, and change is scary.
     */
    @EventHandler(ignoreCancelled = true)
    public void onIronTrapDoorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!main.getPlayerDataManager().getPlayerData(player).hasIronTrapdoorToggleEnabled()) return;

        if (Objects.requireNonNull(event.getClickedBlock()).getType() != Material.IRON_TRAPDOOR) return;
        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;
        if (player.isSneaking()) return;

        Bukkit.getScheduler().runTask(main, () -> {
            Block block = event.getClickedBlock();

            TrapDoor trapDoor = (TrapDoor) block.getBlockData();

            trapDoor.setOpen(!trapDoor.isOpen());

            block.setBlockData(trapDoor);
        });
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSlabBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!main.getPlayerDataManager().getPlayerData(player).hasDoubleSlabBreakEnabled()) return;

        if (!Tag.SLABS.isTagged(player.getInventory().getItemInMainHand().getType())) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;
        if (!Tag.SLABS.isTagged(event.getBlock().getType())) return;

        Slab blockData = (Slab) event.getBlock().getBlockData();
        if (blockData.getType() != Slab.Type.DOUBLE) return;

        if (isTop(player, event.getBlock())) {
            blockData.setType(Slab.Type.BOTTOM);
        } else {
            blockData.setType(Slab.Type.TOP);
        }

        event.getBlock().setBlockData(blockData, true);
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onGlazedTerracottaInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!main.getPlayerDataManager().getPlayerData(player).hasGlazedTerracottaRotateEnabled()) return;

        if (!Objects.requireNonNull(event.getClickedBlock()).getType().name().toLowerCase().contains("glazed")) return;
        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;
        if (!player.isSneaking()) return;

        Bukkit.getScheduler().runTask(main, () -> {
            Block block = event.getClickedBlock();
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
            }

            block.setBlockData(directional);
        });
        event.setCancelled(true);
    }

    private boolean isTop(Player player, Block block) {
        Location start = player.getEyeLocation().clone();
        while (!start.getBlock().equals(block) && start.distance(player.getEyeLocation()) < 6.0D) {
            start.add(player.getEyeLocation().getDirection().multiply(0.05D));
        }
        return start.getY() % 1.0D > 0.5D;
    }
}
