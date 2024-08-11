package dev.tehbrian.buildersutilities.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static love.broccolai.corn.minecraft.item.ItemBuilder.itemBuilder;

public final class MenuItems {

	public static final ItemStack BACKGROUND = itemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
			.name(Component.empty())
			.build();

	private MenuItems() {
	}

}
