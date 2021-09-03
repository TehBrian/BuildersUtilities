package xyz.tehbrian.buildersutilities.option;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class OptionsInventoryProvider {

    private static final ItemStack GREEN = PaperItemBuilder.ofType(Material.LIME_STAINED_GLASS_PANE).name(LangConfig.EMPTY).build();
    private static final ItemStack ORANGE = PaperItemBuilder.ofType(Material.ORANGE_STAINED_GLASS_PANE).name(LangConfig.EMPTY).build();
    private static final ItemStack RED = PaperItemBuilder.ofType(Material.RED_STAINED_GLASS_PANE).name(LangConfig.EMPTY).build();

    private final LangConfig langConfig;

    @Inject
    public OptionsInventoryProvider(
            final @NonNull LangConfig langConfig
    ) {
        this.langConfig = langConfig;
    }

    /*
        TODO: This isn't great.
        While it's better than what we had before, ideally we shouldn't
        have magic strings, and I don't like how redundant this system still is.
     */
    private ItemStack createCustomItem(final Material material, final String optionKey, final String statusKey) {
        final Component name = this.langConfig.c(NodePath.path("inventories", "options", optionKey, "name"));

        final List<Component> lore = new ArrayList<>();
        lore.addAll(this.langConfig.cl(NodePath.path("inventories", "options", optionKey, "description")));
        lore.addAll(this.langConfig.cl(NodePath.path("inventories", "options", "status", statusKey)));

        return PaperItemBuilder.ofType(material)
                .name(name)
                .lore(lore)
                .build();
    }

    public Inventory generate(final User user) {
        final Inventory inv = Bukkit.createInventory(
                null,
                27,
                this.langConfig.c(NodePath.path("inventories", "options", "inventory-name"))
        );

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Constants.Items.INTERFACE_BACKGROUND);
        }

        this.update(inv, user);

        return inv;
    }

    public void update(final Inventory inv, final User user) {
        final Player p = user.getPlayer();

        if (Objects.requireNonNull(p).hasPermission(Constants.Permissions.IRON_DOOR_TOGGLE)) {
            if (user.ironDoorToggleEnabled()) {
                inv.setItem(1, GREEN);
                inv.setItem(10, this.createCustomItem(Material.IRON_TRAPDOOR, "iron-door-toggle", "enabled"));
                inv.setItem(19, GREEN);
            } else {
                inv.setItem(1, RED);
                inv.setItem(10, this.createCustomItem(Material.IRON_TRAPDOOR, "iron-door-toggle", "disabled"));
                inv.setItem(19, RED);
            }
        } else {
            inv.setItem(1, ORANGE);
            inv.setItem(10, this.createCustomItem(Material.IRON_TRAPDOOR, "iron-door-toggle", "no-permission"));
            inv.setItem(19, ORANGE);
        }

        if (p.hasPermission(Constants.Permissions.DOUBLE_SLAB_BREAK)) {
            if (user.doubleSlabBreakEnabled()) {
                inv.setItem(2, GREEN);
                inv.setItem(11, this.createCustomItem(Material.STONE_SLAB, "double-slab-break", "enabled"));
                inv.setItem(20, GREEN);
            } else {
                inv.setItem(2, RED);
                inv.setItem(11, this.createCustomItem(Material.STONE_SLAB, "double-slab-break", "disabled"));
                inv.setItem(20, RED);
            }
        } else {
            inv.setItem(2, ORANGE);
            inv.setItem(11, this.createCustomItem(Material.STONE_SLAB, "double-slab-break", "no-permission"));
            inv.setItem(20, ORANGE);
        }

        if (p.hasPermission(Constants.Permissions.GLAZED_TERRACOTTA_ROTATE)) {
            if (user.glazedTerracottaRotateEnabled()) {
                inv.setItem(3, GREEN);
                inv.setItem(12, this.createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed-terracotta-rotate", "enabled"));
                inv.setItem(21, GREEN);
            } else {
                inv.setItem(3, RED);
                inv.setItem(12, this.createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed-terracotta-rotate", "disabled"));
                inv.setItem(21, RED);
            }
        } else {
            inv.setItem(3, ORANGE);
            inv.setItem(12, this.createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed-terracotta-rotate", "no-permission"));
            inv.setItem(21, ORANGE);
        }

        if (p.hasPermission(Constants.Permissions.NIGHT_VISION)) {
            if (user.nightVisionEnabled()) {
                inv.setItem(5, GREEN);
                inv.setItem(14, this.createCustomItem(Material.ENDER_EYE, "night-vision", "enabled"));
                inv.setItem(23, GREEN);
            } else {
                inv.setItem(5, RED);
                inv.setItem(14, this.createCustomItem(Material.ENDER_EYE, "night-vision", "disabled"));
                inv.setItem(23, RED);
            }
        } else {
            inv.setItem(5, ORANGE);
            inv.setItem(14, this.createCustomItem(Material.ENDER_EYE, "night-vision", "no-permission"));
            inv.setItem(23, ORANGE);
        }

        if (p.hasPermission(Constants.Permissions.NO_CLIP)) {
            if (user.noClipEnabled()) {
                inv.setItem(6, GREEN);
                inv.setItem(15, this.createCustomItem(Material.COMPASS, "no-clip", "enabled"));
                inv.setItem(24, GREEN);
            } else {
                inv.setItem(6, RED);
                inv.setItem(15, this.createCustomItem(Material.COMPASS, "no-clip", "disabled"));
                inv.setItem(24, RED);
            }
        } else {
            inv.setItem(6, ORANGE);
            inv.setItem(15, this.createCustomItem(Material.COMPASS, "no-clip", "no-permission"));
            inv.setItem(24, ORANGE);
        }

        if (p.hasPermission(Constants.Permissions.ADVANCED_FLY)) {
            if (user.advancedFlyEnabled()) {
                inv.setItem(7, GREEN);
                inv.setItem(16, this.createCustomItem(Material.FEATHER, "advanced-fly", "enabled"));
                inv.setItem(25, GREEN);
            } else {
                inv.setItem(7, RED);
                inv.setItem(16, this.createCustomItem(Material.FEATHER, "advanced-fly", "disabled"));
                inv.setItem(25, RED);
            }
        } else {
            inv.setItem(7, ORANGE);
            inv.setItem(16, this.createCustomItem(Material.FEATHER, "advanced-fly", "no-permission"));
            inv.setItem(25, ORANGE);
        }
    }

}
