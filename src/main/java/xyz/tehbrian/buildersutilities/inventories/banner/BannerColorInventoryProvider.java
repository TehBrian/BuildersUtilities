package xyz.tehbrian.buildersutilities.inventories.banner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public class BannerColorInventoryProvider {

    private BannerColorInventoryProvider() {
    }

    public static Inventory generate(ItemStack oldBanner) {
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getMessage("messages.inventories.banner.color_inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        inv.setItem(3, ItemUtils.createHead(MessageUtils.getMessage("heads.banner.randomize"), 1, MessageUtils.getMessage("messages.inventories.banner.randomize")));
        inv.setItem(5, oldBanner);

        inv.setItem(28, createCustomDye(Material.BLACK_DYE));
        inv.setItem(29, createCustomDye(Material.RED_DYE));
        inv.setItem(30, createCustomDye(Material.GREEN_DYE));
        inv.setItem(31, createCustomDye(Material.BROWN_DYE));
        inv.setItem(32, createCustomDye(Material.BLUE_DYE));
        inv.setItem(33, createCustomDye(Material.PURPLE_DYE));
        inv.setItem(34, createCustomDye(Material.CYAN_DYE));
        inv.setItem(35, createCustomDye(Material.LIGHT_GRAY_DYE));

        inv.setItem(37, createCustomDye(Material.GRAY_DYE));
        inv.setItem(38, createCustomDye(Material.PINK_DYE));
        inv.setItem(39, createCustomDye(Material.LIME_DYE));
        inv.setItem(40, createCustomDye(Material.YELLOW_DYE));
        inv.setItem(41, createCustomDye(Material.LIGHT_BLUE_DYE));
        inv.setItem(42, createCustomDye(Material.MAGENTA_DYE));
        inv.setItem(43, createCustomDye(Material.ORANGE_DYE));
        inv.setItem(44, createCustomDye(Material.WHITE_DYE));

        return inv;
    }

    private static ItemStack createCustomDye(Material material) {
        return ItemUtils.create(material, 1, MessageUtils.getMessageList("messages.inventories.banner.select"));
    }
}
