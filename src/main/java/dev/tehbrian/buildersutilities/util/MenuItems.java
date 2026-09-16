package dev.tehbrian.buildersutilities.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

import static dev.tehbrian.agna.paper.FluentItem.createItem;

public final class MenuItems {

	public static final ItemStack BACKGROUND = createItem(ItemType.LIGHT_GRAY_STAINED_GLASS_PANE)
			.hideTooltip(true)
			.item();

	private MenuItems() {
	}

}
