package dev.tehbrian.buildersutilities.banner.menu;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.Buttons;
import dev.tehbrian.buildersutilities.banner.Session;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.util.ChestSize;
import dev.tehbrian.buildersutilities.util.MenuItems;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.spongepowered.configurate.NodePath;

import static dev.tehbrian.agna.paper.FluentItem.createItem;

public final class BaseMenuProvider {

	private final LangConfig langConfig;
	private final ConfigConfig configConfig;

	@Inject
	public BaseMenuProvider(
			final LangConfig langConfig,
			final ConfigConfig configConfig
	) {
		this.langConfig = langConfig;
		this.configConfig = configConfig;
	}

	public Inventory generate(final Session session) {
		final Inventory inv = Bukkit.createInventory(
				null,
				ChestSize.DOUBLE,
				this.langConfig.c(NodePath.path("menus", "banner", "base-inventory-name"))
		);

		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, MenuItems.BACKGROUND);
		}

		Buttons.addToolbar(inv, this.langConfig, this.configConfig, session.generateInterfaceBanner());

		inv.setItem(19, this.createSelectBanner(ItemType.WHITE_BANNER));
		inv.setItem(20, this.createSelectBanner(ItemType.LIGHT_GRAY_BANNER));
		inv.setItem(21, this.createSelectBanner(ItemType.GRAY_BANNER));
		inv.setItem(22, this.createSelectBanner(ItemType.BLACK_BANNER));

		inv.setItem(28, this.createSelectBanner(ItemType.BROWN_BANNER));
		inv.setItem(29, this.createSelectBanner(ItemType.RED_BANNER));
		inv.setItem(30, this.createSelectBanner(ItemType.ORANGE_BANNER));
		inv.setItem(31, this.createSelectBanner(ItemType.YELLOW_BANNER));
		inv.setItem(32, this.createSelectBanner(ItemType.LIME_BANNER));
		inv.setItem(33, this.createSelectBanner(ItemType.GREEN_BANNER));

		inv.setItem(37, this.createSelectBanner(ItemType.LIGHT_BLUE_BANNER));
		inv.setItem(38, this.createSelectBanner(ItemType.CYAN_BANNER));
		inv.setItem(39, this.createSelectBanner(ItemType.BLUE_BANNER));
		inv.setItem(40, this.createSelectBanner(ItemType.PURPLE_BANNER));
		inv.setItem(41, this.createSelectBanner(ItemType.MAGENTA_BANNER));
		inv.setItem(42, this.createSelectBanner(ItemType.PINK_BANNER));

		return inv;
	}

	private ItemStack createSelectBanner(final ItemType itemType) {
		return createItem(itemType)
				.lore(this.langConfig.cl(NodePath.path("menus", "banner", "select")))
				.item();
	}

}
