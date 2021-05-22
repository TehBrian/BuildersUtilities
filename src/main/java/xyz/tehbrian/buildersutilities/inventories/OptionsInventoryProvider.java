package xyz.tehbrian.buildersutilities.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.user.User;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public final class OptionsInventoryProvider {

    private static final ItemStack GREEN = ItemUtils.create(Material.LIME_STAINED_GLASS_PANE, 1, "&7");
    private static final ItemStack ORANGE = ItemUtils.create(Material.ORANGE_STAINED_GLASS_PANE, 1, "&7");
    private static final ItemStack RED = ItemUtils.create(Material.RED_STAINED_GLASS_PANE, 1, "&7");

    private OptionsInventoryProvider() {
    }

    public static Inventory generate(final User user) {
        Inventory inv = Bukkit.createInventory(null, 27, MessageUtils.getMessage("messages.inventories.options.inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        update(inv, user);

        return inv;
    }

    public static void update(final Inventory inv, final User user) {
        Player p = user.getPlayer();

        if (p.hasPermission("buildersutilities.irondoortoggle")) {
            if (user.hasIronDoorToggleEnabled()) {
                inv.setItem(1, GREEN);
                inv.setItem(10, createCustomItem(Material.IRON_TRAPDOOR, "iron_door_toggle", "enabled"));
                inv.setItem(19, GREEN);
            } else {
                inv.setItem(1, RED);
                inv.setItem(10, createCustomItem(Material.IRON_TRAPDOOR, "iron_door_toggle", "disabled"));
                inv.setItem(19, RED);
            }
        } else {
            inv.setItem(1, ORANGE);
            inv.setItem(10, createCustomItem(Material.IRON_TRAPDOOR, "iron_door_toggle", "no_permission"));
            inv.setItem(19, ORANGE);
        }

        if (p.hasPermission("buildersutilities.doubleslabbreak")) {
            if (user.hasDoubleSlabBreakEnabled()) {
                inv.setItem(2, GREEN);
                inv.setItem(11, createCustomItem(Material.STONE_SLAB, "double_slab_break", "enabled"));
                inv.setItem(20, GREEN);
            } else {
                inv.setItem(2, RED);
                inv.setItem(11, createCustomItem(Material.STONE_SLAB, "double_slab_break", "disabled"));
                inv.setItem(20, RED);
            }
        } else {
            inv.setItem(2, ORANGE);
            inv.setItem(11, createCustomItem(Material.STONE_SLAB, "double_slab_break", "no_permission"));
            inv.setItem(20, ORANGE);
        }

        if (p.hasPermission("buildersutilities.glazedterracottarotate")) {
            if (user.hasGlazedTerracottaRotateEnabled()) {
                inv.setItem(3, GREEN);
                inv.setItem(12, createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed_terracotta_rotate", "enabled"));
                inv.setItem(21, GREEN);
            } else {
                inv.setItem(3, RED);
                inv.setItem(12, createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed_terracotta_rotate", "disabled"));
                inv.setItem(21, RED);
            }
        } else {
            inv.setItem(3, ORANGE);
            inv.setItem(12, createCustomItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "glazed_terracotta_rotate", "no_permission"));
            inv.setItem(21, ORANGE);
        }

        if (p.hasPermission("buildersutilities.nightvision")) {
            if (user.hasNightVisionEnabled()) {
                inv.setItem(5, GREEN);
                inv.setItem(14, createCustomItem(Material.ENDER_EYE, "night_vision", "enabled"));
                inv.setItem(23, GREEN);
            } else {
                inv.setItem(5, RED);
                inv.setItem(14, createCustomItem(Material.ENDER_EYE, "night_vision", "disabled"));
                inv.setItem(23, RED);
            }
        } else {
            inv.setItem(5, ORANGE);
            inv.setItem(14, createCustomItem(Material.ENDER_EYE, "night_vision", "no_permission"));
            inv.setItem(23, ORANGE);
        }

        if (p.hasPermission("buildersutilities.noclip")) {
            if (user.hasNoClipEnabled()) {
                inv.setItem(6, GREEN);
                inv.setItem(15, createCustomItem(Material.COMPASS, "no_clip", "enabled"));
                inv.setItem(24, GREEN);
            } else {
                inv.setItem(6, RED);
                inv.setItem(15, createCustomItem(Material.COMPASS, "no_clip", "disabled"));
                inv.setItem(24, RED);
            }
        } else {
            inv.setItem(6, ORANGE);
            inv.setItem(15, createCustomItem(Material.COMPASS, "no_clip", "no_permission"));
            inv.setItem(24, ORANGE);
        }

        if (p.hasPermission("buildersutilities.advancedfly")) {
            if (user.hasAdvancedFlyEnabled()) {
                inv.setItem(7, GREEN);
                inv.setItem(16, createCustomItem(Material.FEATHER, "advanced_fly", "enabled"));
                inv.setItem(25, GREEN);
            } else {
                inv.setItem(7, RED);
                inv.setItem(16, createCustomItem(Material.FEATHER, "advanced_fly", "disabled"));
                inv.setItem(25, RED);
            }
        } else {
            inv.setItem(7, ORANGE);
            inv.setItem(16, createCustomItem(Material.FEATHER, "advanced_fly", "no_permission"));
            inv.setItem(25, ORANGE);
        }
    }

    /*
        TODO: This isn't great.
        While it's better than what we had before, ideally we shouldn't
        have magic strings, and I don't like how redundant this system still is.
     */
    private static ItemStack createCustomItem(final Material material, final String optionKey, final String statusKey) {
        String name = MessageUtils.getMessage("messages.inventories.options." + optionKey + ".name");

        List<String> lore = new ArrayList<>();
        lore.addAll(MessageUtils.getMessageList("messages.inventories.options." + optionKey + ".description"));
        lore.addAll(MessageUtils.getMessageList("messages.inventories.options.status." + statusKey));

        return ItemUtils.create(material, 1, name, lore);
    }
}
