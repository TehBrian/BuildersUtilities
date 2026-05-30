package dev.tehbrian.buildersutilities.special;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.config.SpecialConfig;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.NodePath;

public final class SpecialMenuProvider {

	private final LangConfig langConfig;
	private final SpecialConfig specialConfig;

	@Inject
	public SpecialMenuProvider(
			final LangConfig langConfig,
			final SpecialConfig specialConfig
	) {
		this.langConfig = langConfig;
		this.specialConfig = specialConfig;
	}

	public Inventory generate() {
		final int slotsRequired = (int) Math.ceil(this.specialConfig.items().size() / 9.0) * 9;

		final Inventory inv = Bukkit.createInventory(
				null,
				Math.clamp(slotsRequired, 9, 54),
				this.langConfig.c(NodePath.path("menus", "special", "inventory-name"))
		);

		for (final ItemStack item : this.specialConfig.items()) {
			inv.addItem(item);
		}

		return inv;
	}

}
