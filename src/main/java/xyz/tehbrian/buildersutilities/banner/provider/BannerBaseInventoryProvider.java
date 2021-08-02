package xyz.tehbrian.buildersutilities.banner.provider;

import broccolai.corn.paper.item.PaperItemBuilder;
import broccolai.corn.paper.item.special.SkullBuilder;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.ConfigUtils;

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
        return PaperItemBuilder.ofType(material).lore(this.lang.cl("messages.inventories.banner.select")).build();
    }

    public Inventory generate() {
        final Inventory inv = Bukkit.createInventory(null, 54, this.lang.c("messages.inventories.banner.base_inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Constants.Items.INTERFACE_BACKGROUND);
        }

        inv.setItem(3, SkullBuilder.ofType(Material.PLAYER_HEAD)
                .name(this.lang.c("messages.inventories.banner.randomize"))
                .textures(ConfigUtils.getString("heads.banner.randomize"))
                .build()
        );
        inv.setItem(
                5,
                PaperItemBuilder.ofType(Material.BARRIER).name(this.lang.c("messages.inventories.banner.cannot_get_banner")).build()
        );

        inv.setItem(28, this.createCustomBanner(Material.BLACK_BANNER));
        inv.setItem(29, this.createCustomBanner(Material.RED_BANNER));
        inv.setItem(30, this.createCustomBanner(Material.GREEN_BANNER));
        inv.setItem(31, this.createCustomBanner(Material.BROWN_BANNER));
        inv.setItem(32, this.createCustomBanner(Material.BLUE_BANNER));
        inv.setItem(33, this.createCustomBanner(Material.PURPLE_BANNER));
        inv.setItem(34, this.createCustomBanner(Material.CYAN_BANNER));
        inv.setItem(35, this.createCustomBanner(Material.LIGHT_GRAY_BANNER));

        inv.setItem(37, this.createCustomBanner(Material.GRAY_BANNER));
        inv.setItem(38, this.createCustomBanner(Material.PINK_BANNER));
        inv.setItem(39, this.createCustomBanner(Material.LIME_BANNER));
        inv.setItem(40, this.createCustomBanner(Material.YELLOW_BANNER));
        inv.setItem(41, this.createCustomBanner(Material.LIGHT_BLUE_BANNER));
        inv.setItem(42, this.createCustomBanner(Material.MAGENTA_BANNER));
        inv.setItem(43, this.createCustomBanner(Material.ORANGE_BANNER));
        inv.setItem(44, this.createCustomBanner(Material.WHITE_BANNER));

        return inv;
    }

}
