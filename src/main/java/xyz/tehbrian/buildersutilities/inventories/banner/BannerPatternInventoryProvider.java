package xyz.tehbrian.buildersutilities.inventories.banner;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

public class BannerPatternInventoryProvider {

    private BannerPatternInventoryProvider() {
    }

    public static Inventory generate(ItemStack oldBanner, DyeColor dyeColor) {
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getMessage("messages.inventories.banner.pattern_inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        inv.setItem(3, ItemUtils.createHead(MessageUtils.getMessage("heads.banner.randomize"), 1, MessageUtils.getMessage("messages.inventories.banner.randomize")));
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

        for (int i = 9; i < (BannerUtils.getAllPatternTypes().size() + 9); i++) {
            inv.setItem(i, BannerUtils.createBanner(base, MessageUtils.getMessageList("messages.inventories.banner.select"), new Pattern(dyeColor, BannerUtils.getAllPatternTypes().get(i - 9))));
        }

        return inv;
    }
}
