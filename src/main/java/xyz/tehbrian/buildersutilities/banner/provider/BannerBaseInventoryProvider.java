package xyz.tehbrian.buildersutilities.banner.provider;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.ConfigUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

// TODO: Add an undo button.
public final class BannerBaseInventoryProvider {

    private final Lang lang;

    @Inject
    public BannerBaseInventoryProvider(
            final @NonNull Lang lang
    ) {
        this.lang = lang;
    }

    private ItemStack createCustomBanner(final Material material) {
        return ItemUtils.create(material, 1, this.lang.cl("messages.inventories.banner.select"));
    }

    public Inventory generate() {
        Inventory inv = Bukkit.createInventory(null, 54, this.lang.c("messages.inventories.banner.base_inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, Lang.EMPTY));
        }

        inv.setItem(3, ItemUtils.createHead(
                ConfigUtils.getString("heads.banner.randomize"),
                1,
                this.lang.c("messages.inventories.banner.randomize")));
        inv.setItem(5, ItemUtils.create(Material.BARRIER, 1, this.lang.c("messages.inventories.banner.cannot_get_banner")));

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
}
