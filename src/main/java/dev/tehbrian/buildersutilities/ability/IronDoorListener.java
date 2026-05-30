package dev.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import dev.tehbrian.mayi.core.ActionType;
import dev.tehbrian.mayi.paper.PaperMayi;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

/**
 * Allows players to open iron doors/trapdoors similarly to how wooden
 * doors/trapdoors can be opened.
 */
public final class IronDoorListener implements Listener {

	private final UserService userService;
	private final PaperMayi restrictionHelper;

	@Inject
	public IronDoorListener(
			final UserService userService,
			final PaperMayi restrictionHelper
	) {
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
		final Material blockType = block.getType();

		if ((blockType != Material.IRON_DOOR && blockType != Material.IRON_TRAPDOOR)
				|| event.getAction() != Action.RIGHT_CLICK_BLOCK
				|| event.getHand() != EquipmentSlot.HAND
				|| player.getGameMode() != GameMode.CREATIVE
				|| !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.BREAK)
				|| !this.restrictionHelper.checkRestrictions(player, block.getLocation(), ActionType.PLACE)) {
			return;
		}

		if (player.getInventory().getItemInMainHand().getType() != Material.AIR
				&& player.isSneaking()) {
			return;
		}

		final Openable door = (Openable) block.getBlockData();
		final boolean willOpen = !door.isOpen();
		door.setOpen(willOpen);
		block.setBlockData(door);

		final Sound sound;
		if (blockType.equals(Material.IRON_DOOR)) {
			sound = willOpen ? Sound.BLOCK_IRON_DOOR_OPEN : Sound.BLOCK_IRON_DOOR_CLOSE;
		} else { // type is iron trapdoor.
			sound = willOpen ? Sound.BLOCK_IRON_TRAPDOOR_OPEN : Sound.BLOCK_IRON_TRAPDOOR_CLOSE;
		}

		block.getWorld().playSound(
				block.getLocation(), sound,
				SoundCategory.BLOCKS,
				1F, 1F
		);

		player.swingMainHand();

		event.setCancelled(true);
	}

}
