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
import org.spongepowered.configurate.NodePath;

public final class DoneMenuProvider {

  private final LangConfig langConfig;
  private final ConfigConfig configConfig;

  @Inject
  public DoneMenuProvider(
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
        this.langConfig.c(NodePath.path("menus", "banner", "done-inventory-name"))
    );

    for (int i = 0; i < inv.getSize(); i++) {
      inv.setItem(i, MenuItems.BACKGROUND);
    }

    Buttons.addToolbar(inv, this.langConfig, this.configConfig, session.generateInterfaceBanner());

    return inv;
  }

}
