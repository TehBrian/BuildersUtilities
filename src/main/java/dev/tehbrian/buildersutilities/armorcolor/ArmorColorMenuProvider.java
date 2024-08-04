package dev.tehbrian.buildersutilities.armorcolor;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.util.ChestSize;
import dev.tehbrian.buildersutilities.util.MenuItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.spongepowered.configurate.NodePath;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static love.broccolai.corn.minecraft.item.ItemBuilder.itemBuilder;
import static love.broccolai.corn.minecraft.item.special.LeatherArmorBuilder.leatherArmorBuilder;
import static love.broccolai.corn.minecraft.item.special.SkullBuilder.skullBuilder;

public final class ArmorColorMenuProvider {

  private final LangConfig langConfig;
  private final ConfigConfig configConfig;

  @Inject
  public ArmorColorMenuProvider(
      final LangConfig langConfig,
      final ConfigConfig configConfig
  ) {
    this.langConfig = langConfig;
    this.configConfig = configConfig;
  }

  public Inventory generate() {
    final Inventory inv = Bukkit.createInventory(
        null,
        ChestSize.DOUBLE,
        this.langConfig.c(NodePath.path("menus", "armor-color", "inventory-name"))
    );

    for (int i = 0; i < inv.getSize(); i++) {
      inv.setItem(i, MenuItems.BACKGROUND);
    }

    inv.setItem(
        10,
        itemBuilder(Material.LEATHER_HELMET)
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "get-helmet")))
            .build()
    );
    inv.setItem(
        19,
        itemBuilder(Material.LEATHER_CHESTPLATE)
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "get-chestplate")))
            .build()
    );
    inv.setItem(
        28,
        itemBuilder(Material.LEATHER_LEGGINGS)
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "get-leggings")))
            .build()
    );
    inv.setItem(
        37,
        itemBuilder(Material.LEATHER_BOOTS)
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "get-boots")))
            .build()
    );

    final List<Component> lore = this.langConfig.cl(NodePath.path("menus", "armor-color", "change"));

    inv.setItem(
        22,
        skullBuilder()
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-red")))
            .textures(this.configConfig.data().heads().armorColor().randomizeRed())
            .build()
    );
    inv.setItem(
        23,
        skullBuilder()
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-green")))
            .textures(this.configConfig.data().heads().armorColor().randomizeGreen())
            .build()
    );
    inv.setItem(
        24,
        skullBuilder()
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-blue")))
            .textures(this.configConfig.data().heads().armorColor().randomizeBlue())
            .build()
    );
    inv.setItem(
        31,
        skullBuilder().amount(16)
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "red")))
            .lore(lore)
            .textures(this.configConfig.data().heads().armorColor().red())
            .build()
    );
    inv.setItem(
        32,
        skullBuilder().amount(16)
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "green")))
            .lore(lore)
            .textures(this.configConfig.data().heads().armorColor().green())
            .build()
    );
    inv.setItem(
        33,
        skullBuilder().amount(16)
            .name(this.langConfig.c(NodePath.path("menus", "armor-color", "blue")))
            .lore(lore)
            .textures(this.configConfig.data().heads().armorColor().blue())
            .build()
    );

    this.update(inv);

    return inv;
  }

  public void update(final Inventory inv) {
    int r = (requireNonNull(inv.getItem(31)).getAmount() - 1) * 8;
    int g = (requireNonNull(inv.getItem(32)).getAmount() - 1) * 8;
    int b = (requireNonNull(inv.getItem(33)).getAmount() - 1) * 8;

    if (r == 256) {
      r = 255;
    }

    if (g == 256) {
      g = 255;
    }

    if (b == 256) {
      b = 255;
    }

    final Color finalColor = Color.fromRGB(r, g, b);

    inv.setItem(10, leatherArmorBuilder(requireNonNull(inv.getItem(10))).color(finalColor).build());
    inv.setItem(19, leatherArmorBuilder(requireNonNull(inv.getItem(19))).color(finalColor).build());
    inv.setItem(28, leatherArmorBuilder(requireNonNull(inv.getItem(28))).color(finalColor).build());
    inv.setItem(37, leatherArmorBuilder(requireNonNull(inv.getItem(37))).color(finalColor).build());
  }

}
