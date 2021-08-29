package xyz.tehbrian.buildersutilities.banner.provider;

import broccolai.corn.paper.item.special.BannerBuilder;
import broccolai.corn.paper.item.special.SkullBuilder;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ConfigUtils;

public final class BannerPatternInventoryProvider {

    private final LangConfig lang;

    @Inject
    public BannerPatternInventoryProvider(
            final @NonNull LangConfig lang
    ) {
        this.lang = lang;
    }

    public Inventory generate(final ItemStack oldBanner, final DyeColor dyeColor) {
        final Inventory inv = Bukkit.createInventory(
                null,
                54,
                this.lang.c(NodePath.path("messages.inventories.banner.pattern_inventory_name"))
        );

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Constants.Items.INTERFACE_BACKGROUND);
        }

        inv.setItem(3, SkullBuilder.ofType(Material.PLAYER_HEAD)
                .name(this.lang.c(NodePath.path("messages.inventories.banner.randomize")))
                .textures(ConfigUtils.getString("heads.banner.randomize"))
                .build()
        );
        inv.setItem(5, oldBanner);

        final Material base = switch (dyeColor) {
            case WHITE, LIGHT_GRAY, LIME, LIGHT_BLUE, YELLOW -> Material.BLACK_BANNER;
            default -> Material.WHITE_BANNER;
        };

        for (int i = 9; i < (BannerUtils.patternTypes().size() + 9); i++) {
            inv.setItem(i, BannerBuilder.ofType(base)
                    .lore(this.lang.cl(NodePath.path("messages.inventories.banner.select")))
                    .addPattern(new Pattern(dyeColor, BannerUtils.patternTypes().get(i - 9)))
                    .build()
            );
        }

        return inv;
    }

}
