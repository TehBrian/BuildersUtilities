package xyz.tehbrian.buildersutilities.inventories.banner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

// TODO: Add an undo button.
public class BannerBaseInventoryProvider {

    private BannerBaseInventoryProvider() {
    }

    public static Inventory generate() {
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getMessage("messages.inventories.banner.base_inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        inv.setItem(3, ItemUtils.createHead(MessageUtils.getMessage("heads.banner.randomize"), 1, MessageUtils.getMessage("messages.inventories.banner.randomize")));
        inv.setItem(5, ItemUtils.create(Material.BARRIER, 1, MessageUtils.getMessage("messages.inventories.banner.cannot_get_banner")));

        inv.setItem(28, createCustomBanner(Material.BLACK_BANNER));
        inv.setItem(29, createCustomBanner(Material.RED_BANNER));
        inv.setItem(30, createCustomBanner(Material.GREEN_BANNER));
        inv.setItem(31, createCustomBanner(Material.BROWN_BANNER));
        inv.setItem(32, createCustomBanner(Material.BLUE_BANNER));
        inv.setItem(33, createCustomBanner(Material.PURPLE_BANNER));
        inv.setItem(34, createCustomBanner(Material.CYAN_BANNER));
        inv.setItem(35, createCustomBanner(Material.LIGHT_GRAY_BANNER));

        inv.setItem(37, createCustomBanner(Material.GRAY_BANNER));
        inv.setItem(38, createCustomBanner(Material.PINK_BANNER));
        inv.setItem(39, createCustomBanner(Material.LIME_BANNER));
        inv.setItem(40, createCustomBanner(Material.YELLOW_BANNER));
        inv.setItem(41, createCustomBanner(Material.LIGHT_BLUE_BANNER));
        inv.setItem(42, createCustomBanner(Material.MAGENTA_BANNER));
        inv.setItem(43, createCustomBanner(Material.ORANGE_BANNER));
        inv.setItem(44, createCustomBanner(Material.WHITE_BANNER));

        return inv;
    }

    private static ItemStack createCustomBanner(Material material) {
        return ItemUtils.create(material, 1, MessageUtils.getMessageList("messages.inventories.banner.select"));
    }
}
