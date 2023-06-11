package dev.tehbrian.buildersutilities.banner.menu;

import broccolai.corn.paper.item.PaperItemBuilder;
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

    inv.setItem(28, this.createSelectBanner(Material.BLACK_BANNER));
    inv.setItem(29, this.createSelectBanner(Material.RED_BANNER));
    inv.setItem(30, this.createSelectBanner(Material.GREEN_BANNER));
    inv.setItem(31, this.createSelectBanner(Material.BROWN_BANNER));
    inv.setItem(32, this.createSelectBanner(Material.BLUE_BANNER));
    inv.setItem(33, this.createSelectBanner(Material.PURPLE_BANNER));
    inv.setItem(34, this.createSelectBanner(Material.CYAN_BANNER));
    inv.setItem(35, this.createSelectBanner(Material.LIGHT_GRAY_BANNER));

    inv.setItem(37, this.createSelectBanner(Material.GRAY_BANNER));
    inv.setItem(38, this.createSelectBanner(Material.PINK_BANNER));
    inv.setItem(39, this.createSelectBanner(Material.LIME_BANNER));
    inv.setItem(40, this.createSelectBanner(Material.YELLOW_BANNER));
    inv.setItem(41, this.createSelectBanner(Material.LIGHT_BLUE_BANNER));
    inv.setItem(42, this.createSelectBanner(Material.MAGENTA_BANNER));
    inv.setItem(43, this.createSelectBanner(Material.ORANGE_BANNER));
    inv.setItem(44, this.createSelectBanner(Material.WHITE_BANNER));

    return inv;
  }

  private ItemStack createSelectBanner(final Material material) {
    return PaperItemBuilder.ofType(material)
        .lore(this.langConfig.cl(NodePath.path("menus", "banner", "select")))
        .build();
  }

}
