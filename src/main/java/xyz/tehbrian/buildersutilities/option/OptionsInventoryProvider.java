package xyz.tehbrian.buildersutilities.option;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.user.User;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.Permissions;

import java.util.ArrayList;
import java.util.List;

public final class OptionsInventoryProvider {

    private static final ItemStack GREEN = ItemUtils.create(Material.LIME_STAINED_GLASS_PANE, 1, Lang.EMPTY);
    private static final ItemStack ORANGE = ItemUtils.create(Material.ORANGE_STAINED_GLASS_PANE, 1, Lang.EMPTY);
    private static final ItemStack RED = ItemUtils.create(Material.RED_STAINED_GLASS_PANE, 1, Lang.EMPTY);

    private final Lang lang;

    @Inject
    public OptionsInventoryProvider(
            final @NonNull Lang lang
    ) {
        this.lang = lang;
    }

    /*
        TODO: This isn't great.
        While it's better than what we had before, ideally we shouldn't
        have magic strings, and I don't like how redundant this system still is.
     */
    private ItemStack createCustomItem(final Material material, final String optionKey, final String statusKey) {
        Component name = this.lang.c("messages.inventories.options." + optionKey + ".name");

        List<Component> lore = new ArrayList<>();
        lore.addAll(this.lang.cl("messages.inventories.options." + optionKey + ".description"));
        lore.addAll(this.lang.cl("messages.inventories.options.status." + statusKey));

        return ItemUtils.create(material, 1, name, lore);
    }

    public Inventory generate(final User user) {
        Inventory inv = Bukkit.createInventory(null, 27, this.lang.c("messages.inventories.options.inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, Lang.EMPTY));
        }

        this.update(inv, user);

        return inv;
    }

    public void update(final Inventory inv, final User user) {
        Player p = user.getPlayer();

        if (p.hasPermission(Permissions.IRON_DOOR_TOGGLE)) {
            if (user.hasIronDoorToggleEnabled()) {
                inv.setItem(1, GREEN);
                inv.setItem(10, this.createCustomItem(Material.IRON_TRAPDOOR, "iron_door_toggle", "enabled"));
                inv.setItem(19, GREEN);
            } else {
                inv.setItem(1, RED);
                inv.setItem(10, this.createCustomItem(Material.IRON_TRAPDOOR, "iron_door_toggle", "disabled"));
                inv.setItem(19, RED);
            }
        } else {
            inv.setItem(1, ORANGE);
            inv.setItem(10, this.createCustomItem(Material.IRON_TRAPDOOR, "iron_door_toggle", "no_permission"));
            inv.setItem(19, ORANGE);
        }

        if (p.hasPermission(Permissions.DOUBLE_SLAB_BREAK)) {
            if (user.hasDoubleSlabBreakEnabled()) {
                inv.setItem(2, GREEN);
                inv.setItem(11, this.createCustomItem(Material.STONE_SLAB, "double_slab_break", "enabled"));
                inv.setItem(20, GREEN);
            } else {
                inv.setItem(2, RED);
                inv.setItem(11, this.createCustomItem(Material.STONE_SLAB, "double_slab_break", "disabled"));
                inv.setItem(20, RED);
            }
        } else {
            inv.setItem(2, ORANGE);
            inv.setItem(11, this.createCustomItem(Material.STONE_SLAB, "double_slab_break", "no_permission"));
            inv.setItem(20, ORANGE);
        }

        if (p.hasPermission(Permissions.GLAZED_TERRACOTTA_ROTATE)) {
            if (user.hasGlazedTerracottaRotateEnabled()) {
                inv.setItem(3, GREEN);
                inv.setItem(12, this.createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed_terracotta_rotate", "enabled"));
                inv.setItem(21, GREEN);
            } else {
                inv.setItem(3, RED);
                inv.setItem(12, this.createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed_terracotta_rotate", "disabled"));
                inv.setItem(21, RED);
            }
        } else {
            inv.setItem(3, ORANGE);
            inv.setItem(12, this.createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed_terracotta_rotate", "no_permission"));
            inv.setItem(21, ORANGE);
        }

        if (p.hasPermission(Permissions.NIGHT_VISION)) {
            if (user.hasNightVisionEnabled()) {
                inv.setItem(5, GREEN);
                inv.setItem(14, this.createCustomItem(Material.ENDER_EYE, "night_vision", "enabled"));
                inv.setItem(23, GREEN);
            } else {
                inv.setItem(5, RED);
                inv.setItem(14, this.createCustomItem(Material.ENDER_EYE, "night_vision", "disabled"));
                inv.setItem(23, RED);
            }
        } else {
            inv.setItem(5, ORANGE);
            inv.setItem(14, this.createCustomItem(Material.ENDER_EYE, "night_vision", "no_permission"));
            inv.setItem(23, ORANGE);
        }

        if (p.hasPermission(Permissions.NO_CLIP)) {
            if (user.hasNoClipEnabled()) {
                inv.setItem(6, GREEN);
                inv.setItem(15, this.createCustomItem(Material.COMPASS, "no_clip", "enabled"));
                inv.setItem(24, GREEN);
            } else {
                inv.setItem(6, RED);
                inv.setItem(15, this.createCustomItem(Material.COMPASS, "no_clip", "disabled"));
                inv.setItem(24, RED);
            }
        } else {
            inv.setItem(6, ORANGE);
            inv.setItem(15, this.createCustomItem(Material.COMPASS, "no_clip", "no_permission"));
            inv.setItem(24, ORANGE);
        }

        if (p.hasPermission(Permissions.ADVANCED_FLY)) {
            if (user.hasAdvancedFlyEnabled()) {
                inv.setItem(7, GREEN);
                inv.setItem(16, this.createCustomItem(Material.FEATHER, "advanced_fly", "enabled"));
                inv.setItem(25, GREEN);
            } else {
                inv.setItem(7, RED);
                inv.setItem(16, this.createCustomItem(Material.FEATHER, "advanced_fly", "disabled"));
                inv.setItem(25, RED);
            }
        } else {
            inv.setItem(7, ORANGE);
            inv.setItem(16, this.createCustomItem(Material.FEATHER, "advanced_fly", "no_permission"));
            inv.setItem(25, ORANGE);
        }
    }

}
