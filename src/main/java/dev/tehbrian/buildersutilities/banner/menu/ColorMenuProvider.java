package dev.tehbrian.buildersutilities.banner.menu;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.Buttons;
import dev.tehbrian.buildersutilities.banner.Session;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.util.ChestSize;
import dev.tehbrian.buildersutilities.util.MenuItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.NodePath;

import static love.broccolai.corn.minecraft.item.ItemBuilder.itemBuilder;

public final class ColorMenuProvider {

	private final LangConfig langConfig;
	private final ConfigConfig configConfig;

	@Inject
	public ColorMenuProvider(
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
				this.langConfig.c(NodePath.path("menus", "banner", "color-inventory-name"))
		);

		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, MenuItems.BACKGROUND);
		}

		Buttons.addToolbar(inv, this.langConfig, this.configConfig, session.generateInterfaceBanner());

		inv.setItem(19, this.createSelectDye(Material.WHITE_DYE));
		inv.setItem(20, this.createSelectDye(Material.LIGHT_GRAY_DYE));
		inv.setItem(21, this.createSelectDye(Material.GRAY_DYE));
		inv.setItem(22, this.createSelectDye(Material.BLACK_DYE));

		inv.setItem(28, this.createSelectDye(Material.BROWN_DYE));
		inv.setItem(29, this.createSelectDye(Material.RED_DYE));
		inv.setItem(30, this.createSelectDye(Material.ORANGE_DYE));
		inv.setItem(31, this.createSelectDye(Material.YELLOW_DYE));
		inv.setItem(32, this.createSelectDye(Material.LIME_DYE));
		inv.setItem(33, this.createSelectDye(Material.GREEN_DYE));

		inv.setItem(37, this.createSelectDye(Material.LIGHT_BLUE_DYE));
		inv.setItem(38, this.createSelectDye(Material.CYAN_DYE));
		inv.setItem(39, this.createSelectDye(Material.BLUE_DYE));
		inv.setItem(40, this.createSelectDye(Material.PURPLE_DYE));
		inv.setItem(41, this.createSelectDye(Material.MAGENTA_DYE));
		inv.setItem(42, this.createSelectDye(Material.PINK_DYE));

		return inv;
	}

	private ItemStack createSelectDye(final Material material) {
		return itemBuilder(material)
				.lore(this.langConfig.cl(NodePath.path("menus", "banner", "select")))
				.build();
	}

}
