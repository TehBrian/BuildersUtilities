package dev.tehbrian.buildersutilities.special;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.LangConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.NodePath;

import java.util.Objects;

public final class SpecialMenuListener implements Listener {

	private final LangConfig langConfig;

	@Inject
	public SpecialMenuListener(
			final LangConfig langConfig
	) {
		this.langConfig = langConfig;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClick(final InventoryClickEvent event) {
		final var title = this.langConfig.c(NodePath.path("menus", "special", "inventory-name"));
		if (!event.getView().title().equals(title) || !(event.getWhoClicked() instanceof final Player player)) {
			return;
		}

		final var clickedInventory = event.getClickedInventory();
		if (clickedInventory == null) {
			return;
		}

		if (Objects.equals(clickedInventory, event.getView().getTopInventory())) {
			event.setResult(Event.Result.DENY);

			final ItemStack item = event.getCurrentItem();
			if (item == null) {
				return;
			}

			player.getInventory().addItem(item.clone());
		} else if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
				|| event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
			event.setResult(Event.Result.DENY);
		}

	}

}
