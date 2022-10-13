package xyz.tehbrian.buildersutilities.ability;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.User;
import xyz.tehbrian.buildersutilities.util.ChestSize;
import xyz.tehbrian.buildersutilities.util.Items;
import xyz.tehbrian.buildersutilities.util.Permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AbilityMenuProvider {

  private static final ItemStack GREEN = PaperItemBuilder
      .ofType(Material.LIME_STAINED_GLASS_PANE)
      .name(Component.empty())
      .build();
  private static final ItemStack ORANGE = PaperItemBuilder
      .ofType(Material.ORANGE_STAINED_GLASS_PANE)
      .name(Component.empty())
      .build();
  private static final ItemStack RED = PaperItemBuilder
      .ofType(Material.RED_STAINED_GLASS_PANE)
      .name(Component.empty())
      .build();

  private final LangConfig langConfig;

  @Inject
  public AbilityMenuProvider(
      final LangConfig langConfig
  ) {
    this.langConfig = langConfig;
  }

  public Inventory generate(final User user) {
    final Inventory inv = Bukkit.createInventory(
        null,
        ChestSize.SINGLE,
        this.langConfig.c(NodePath.path("menus", "ability", "inventory-name"))
    );

    for (int i = 0; i < inv.getSize(); i++) {
      inv.setItem(i, Items.INTERFACE_BACKGROUND);
    }

    this.update(inv, user);

    return inv;
  }

  public void update(final Inventory inv, final User user) {
    final @Nullable Player p = user.getPlayer();
    Objects.requireNonNull(p);

    this.drawAbility(
        inv,
        1,
        Material.IRON_TRAPDOOR,
        "iron-door-toggle",
        p.hasPermission(Permissions.IRON_DOOR_TOGGLE),
        user.ironDoorToggleEnabled()
    );

    this.drawAbility(
        inv,
        2,
        Material.STONE_SLAB,
        "double-slab-break",
        p.hasPermission(Permissions.DOUBLE_SLAB_BREAK),
        user.doubleSlabBreakEnabled()
    );

    this.drawAbility(
        inv,
        3,
        Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
        "glazed-terracotta-rotate",
        p.hasPermission(Permissions.GLAZED_TERRACOTTA_ROTATE),
        user.glazedTerracottaRotateEnabled()
    );

    this.drawAbility(
        inv,
        5,
        Material.ENDER_EYE,
        "night-vision",
        p.hasPermission(Permissions.NIGHT_VISION),
        user.nightVisionEnabled()
    );

    this.drawAbility(
        inv,
        6,
        Material.COMPASS,
        "noclip",
        p.hasPermission(Permissions.NOCLIP),
        user.noclipEnabled()
    );

    this.drawAbility(
        inv,
        7,
        Material.FEATHER,
        "advanced-fly",
        p.hasPermission(Permissions.ADVANCED_FLY),
        user.advancedFlyEnabled()
    );
  }

  private ItemStack createAbilityItem(final Material material, final String abilityKey, final String statusKey) {
    final List<Component> lore = new ArrayList<>();
    lore.addAll(this.langConfig.cl(NodePath.path("menus", "ability", abilityKey, "description")));
    lore.addAll(this.langConfig.cl(NodePath.path("menus", "ability", "status", statusKey)));

    return PaperItemBuilder.ofType(material)
        .name(this.langConfig.c(NodePath.path("menus", "ability", abilityKey, "name")))
        .lore(lore)
        .build();
  }

  private void drawAbility(
      final Inventory inv,
      final int row,
      final Material material,
      final String abilityKey,
      final boolean hasPermission,
      final boolean isEnabled
  ) {
    if (!hasPermission) {
      inv.setItem(row, ORANGE);
      inv.setItem(row + 9, this.createAbilityItem(material, abilityKey, "no-permission"));
      inv.setItem(row + 18, ORANGE);
      return;
    }

    if (isEnabled) {
      inv.setItem(row, GREEN);
      inv.setItem(row + 9, this.createAbilityItem(material, abilityKey, "enabled"));
      inv.setItem(row + 18, GREEN);
    } else {
      inv.setItem(row, RED);
      inv.setItem(row + 9, this.createAbilityItem(material, abilityKey, "disabled"));
      inv.setItem(row + 18, RED);
    }
  }

}
