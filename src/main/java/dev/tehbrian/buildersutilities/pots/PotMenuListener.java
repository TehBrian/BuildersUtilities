package dev.tehbrian.buildersutilities.pots;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.LangConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.spongepowered.configurate.NodePath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class PotMenuListener implements Listener {

	private final PotMenuProvider potMenuProvider;
	private final LangConfig langConfig;

	@Inject
	public PotMenuListener(
			final PotMenuProvider potMenuProvider,
			final LangConfig langConfig
	) {
		this.potMenuProvider = potMenuProvider;
		this.langConfig = langConfig;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClick(final InventoryClickEvent event) {
		if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())) return;
		if (!event.getView().title().equals(this.langConfig.c(NodePath.path("menus", "pots", "inventory-name")))) return;
		if (!(event.getWhoClicked() instanceof final Player player)) return;

		final int slot = event.getRawSlot();
		final Inventory inventory = event.getClickedInventory();
		Objects.requireNonNull(inventory);

		event.setCancelled(true);

		final List<ItemType> sides = new ArrayList<>(this.potMenuProvider.sides(inventory));
		final int selectedSide = this.potMenuProvider.selectedSide(inventory);

		switch (slot) {
			case PotMenuProvider.POT_SLOT -> player.getInventory().addItem(PotMenuProvider.createPot(sides));
			case PotMenuProvider.RANDOM_SLOT -> this.potMenuProvider.render(inventory, PotMenuProvider.randomSides(), selectedSide);
			case PotMenuProvider.RESET_SLOT -> this.potMenuProvider.render(inventory, Collections.nCopies(sides.size(), ItemType.BRICK), 0);
			default -> {
				final int sideIndex = PotMenuProvider.SIDE_SLOTS.indexOf(slot);
				if (sideIndex != -1) {
					this.potMenuProvider.render(inventory, sides, sideIndex);
					return;
				}

				if (slot < PotMenuProvider.FIRST_SHERD_SLOT) return;
				final ItemStack clicked = event.getCurrentItem();
				if (clicked == null) return;
				final ItemType clickedType = clicked.getType().asItemType();
				if (clickedType == null || !PotMenuProvider.faceTypes().contains(clickedType)) return;

				sides.set(selectedSide, clickedType);
				this.potMenuProvider.render(inventory, sides, (selectedSide + 1) % sides.size());
			}
		}
	}
}
