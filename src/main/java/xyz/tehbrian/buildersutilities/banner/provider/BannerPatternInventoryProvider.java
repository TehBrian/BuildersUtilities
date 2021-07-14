package xyz.tehbrian.buildersutilities.banner.provider;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ConfigUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

public final class BannerPatternInventoryProvider {

    private final Lang lang;

    @Inject
    public BannerPatternInventoryProvider(
            final @NonNull Lang lang
    ) {
        this.lang = lang;
    }

    public Inventory generate(final ItemStack oldBanner, final DyeColor dyeColor) {
        Inventory inv = Bukkit.createInventory(null, 54, this.lang.c("messages.inventories.banner.pattern_inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, Lang.EMPTY));
        }

        inv.setItem(3, ItemUtils.createHead(
                ConfigUtils.getString("heads.banner.randomize"),
                1,
                this.lang.c("messages.inventories.banner.randomize")
        ));
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
            inv.setItem(i, BannerUtils.createBanner(
                    base,
                    this.lang.cl("messages.inventories.banner.select"),
                    new Pattern(dyeColor, BannerUtils.getAllPatternTypes().get(i - 9))
            ));
        }

        return inv;
    }

}
