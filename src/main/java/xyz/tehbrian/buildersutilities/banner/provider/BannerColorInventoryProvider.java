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

public final class BannerColorInventoryProvider {

    private final Lang lang;

    @Inject
    public BannerColorInventoryProvider(
            final @NonNull Lang lang
    ) {
        this.lang = lang;
    }

    private ItemStack createCustomDye(final Material material) {
        return ItemUtils.create(material, 1, this.lang.cl("messages.inventories.banner.select"));
    }

    public Inventory generate(final ItemStack oldBanner) {
        Inventory inv = Bukkit.createInventory(null, 54, this.lang.c("messages.inventories.banner.color_inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, Lang.EMPTY));
        }

        inv.setItem(3, ItemUtils.createHead(
                ConfigUtils.getString("heads.banner.randomize"),
                1,
                this.lang.c("messages.inventories.banner.randomize")));
        inv.setItem(5, oldBanner);

        inv.setItem(28, this.createCustomDye(Material.BLACK_DYE));
        inv.setItem(29, this.createCustomDye(Material.RED_DYE));
        inv.setItem(30, this.createCustomDye(Material.GREEN_DYE));
        inv.setItem(31, this.createCustomDye(Material.BROWN_DYE));
        inv.setItem(32, this.createCustomDye(Material.BLUE_DYE));
        inv.setItem(33, this.createCustomDye(Material.PURPLE_DYE));
        inv.setItem(34, this.createCustomDye(Material.CYAN_DYE));
        inv.setItem(35, this.createCustomDye(Material.LIGHT_GRAY_DYE));

        inv.setItem(37, this.createCustomDye(Material.GRAY_DYE));
        inv.setItem(38, this.createCustomDye(Material.PINK_DYE));
        inv.setItem(39, this.createCustomDye(Material.LIME_DYE));
        inv.setItem(40, this.createCustomDye(Material.YELLOW_DYE));
        inv.setItem(41, this.createCustomDye(Material.LIGHT_BLUE_DYE));
        inv.setItem(42, this.createCustomDye(Material.MAGENTA_DYE));
        inv.setItem(43, this.createCustomDye(Material.ORANGE_DYE));
        inv.setItem(44, this.createCustomDye(Material.WHITE_DYE));

        return inv;
    }
}
