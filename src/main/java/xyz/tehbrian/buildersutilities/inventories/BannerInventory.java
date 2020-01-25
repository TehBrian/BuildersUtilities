package xyz.tehbrian.buildersutilities.inventories;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

/*
    TODO: Add back button.
    I'm not quite sure how we would implement it,
    but it's definitely top priority in terms of
    making the plugin visibly better, because currently if you
    make a bad decision you essentially have to restart.
 */

/*
    TODO: Clean up this code.
    It's all a mess.
 */
public class BannerInventory {

    private BannerInventory() {
    }

    public static Inventory generateBaseInventory() {
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getMessage("msg.banner.base_inventory_name"));

        for (int x = 0; x < inv.getSize(); x++) {
            inv.setItem(x, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        inv.setItem(12, ItemUtils.createHead(MessageUtils.getMessage("head.banner.randomize"), 1, MessageUtils.getMessage("msg.banner.randomize")));
        inv.setItem(14, ItemUtils.create(Material.BARRIER, 1, MessageUtils.getMessage("msg.banner.cannot_get_banner")));

        inv.setItem(28, ItemUtils.create(Material.BLACK_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(29, ItemUtils.create(Material.RED_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(30, ItemUtils.create(Material.GREEN_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(31, ItemUtils.create(Material.BROWN_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(32, ItemUtils.create(Material.BLUE_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(33, ItemUtils.create(Material.PURPLE_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(34, ItemUtils.create(Material.CYAN_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(35, ItemUtils.create(Material.LIGHT_GRAY_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));

        inv.setItem(37, ItemUtils.create(Material.GRAY_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(38, ItemUtils.create(Material.PINK_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(39, ItemUtils.create(Material.LIME_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(40, ItemUtils.create(Material.YELLOW_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(41, ItemUtils.create(Material.LIGHT_BLUE_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(42, ItemUtils.create(Material.MAGENTA_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(43, ItemUtils.create(Material.ORANGE_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(44, ItemUtils.create(Material.WHITE_BANNER, 1, MessageUtils.getMessageList("msg.banner.select")));

        return inv;
    }

    public static Inventory generateColorInventory(ItemStack oldBanner) {
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getMessage("msg.banner.color_inventory_name"));

        for (int x = 0; x < inv.getSize(); x++) {
            inv.setItem(x, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        inv.setItem(12, ItemUtils.createHead(MessageUtils.getMessage("head.banner.randomize"), 1, MessageUtils.getMessage("msg.banner.randomize")));
        inv.setItem(14, oldBanner);

        inv.setItem(28, ItemUtils.create(Material.BLACK_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(29, ItemUtils.create(Material.RED_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(30, ItemUtils.create(Material.GREEN_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(31, ItemUtils.create(Material.BROWN_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(32, ItemUtils.create(Material.BLUE_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(33, ItemUtils.create(Material.PURPLE_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(34, ItemUtils.create(Material.CYAN_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(35, ItemUtils.create(Material.LIGHT_GRAY_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));

        inv.setItem(37, ItemUtils.create(Material.GRAY_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(38, ItemUtils.create(Material.PINK_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(39, ItemUtils.create(Material.LIME_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(40, ItemUtils.create(Material.YELLOW_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(41, ItemUtils.create(Material.LIGHT_BLUE_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(42, ItemUtils.create(Material.MAGENTA_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(43, ItemUtils.create(Material.ORANGE_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));
        inv.setItem(44, ItemUtils.create(Material.WHITE_DYE, 1, MessageUtils.getMessageList("msg.banner.select")));

        return inv;
    }

    public static Inventory generatePatternInventory(ItemStack oldBanner, DyeColor dyeColor) {
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getMessage("msg.banner.pattern_inventory_name"));

        for (int x = 0; x < inv.getSize(); x++) {
            inv.setItem(x, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        inv.setItem(3, ItemUtils.createHead(MessageUtils.getMessage("head.banner.randomize"), 1, MessageUtils.getMessage("msg.banner.randomize")));
        inv.setItem(5, oldBanner);

        Material base;

        switch (dyeColor) {
            case WHITE:
            case LIGHT_GRAY:
            case LIME:
            case LIGHT_BLUE:
            case YELLOW:
                base = Material.BLACK_BANNER;
                break;
            default:
                base = Material.WHITE_BANNER;
                break;
        }

        for (int x = 9; x < (BannerUtils.getAllPatternTypes().size() + 9); x++) {
            inv.setItem(x, BannerUtils.createBanner(base, MessageUtils.getMessageList("msg.banner.select"), new Pattern(dyeColor, BannerUtils.getAllPatternTypes().get(x - 9))));
        }

        return inv;
    }
}
