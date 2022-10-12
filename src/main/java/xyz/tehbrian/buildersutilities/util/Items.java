package xyz.tehbrian.buildersutilities.util;

import broccolai.corn.paper.item.PaperItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class Items {

  public static final ItemStack INTERFACE_BACKGROUND = PaperItemBuilder
      .ofType(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
      .name(Component.empty())
      .build();

  private Items() {
  }

}
