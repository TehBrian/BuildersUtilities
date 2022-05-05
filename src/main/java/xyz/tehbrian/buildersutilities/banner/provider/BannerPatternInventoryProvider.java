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
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.BannerUtil;

public final class BannerPatternInventoryProvider {

    private final LangConfig langConfig;
    private final ConfigConfig configConfig;

    @Inject
    public BannerPatternInventoryProvider(
            final @NonNull LangConfig langConfig,
            final @NonNull ConfigConfig configConfig
    ) {
        this.langConfig = langConfig;
        this.configConfig = configConfig;
    }

    public Inventory generate(final ItemStack oldBanner, final DyeColor dyeColor) {
        final Inventory inv = Bukkit.createInventory(
                null,
                54,
                this.langConfig.c(NodePath.path("inventories", "banner", "pattern-inventory-name"))
        );

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Constants.Items.INTERFACE_BACKGROUND);
        }

        inv.setItem(3, SkullBuilder.ofType(Material.PLAYER_HEAD)
                .name(this.langConfig.c(NodePath.path("inventories", "banner", "randomize")))
                .textures(this.configConfig.data().heads().banner().randomize())
                .build()
        );
        inv.setItem(5, oldBanner);

        final Material base = switch (dyeColor) {
            case WHITE, LIGHT_GRAY, LIME, LIGHT_BLUE, YELLOW -> Material.BLACK_BANNER;
            default -> Material.WHITE_BANNER;
        };

        for (int i = 9; i < (BannerUtil.patternTypes().size() + 9); i++) {
            inv.setItem(i, BannerBuilder.ofType(base)
                    .lore(this.langConfig.cl(NodePath.path("inventories", "banner", "select")))
                    .addPattern(new Pattern(dyeColor, BannerUtil.patternTypes().get(i - 9)))
                    .build()
            );
        }

        return inv;
    }

}
