package xyz.tehbrian.buildersutilities.banner.provider;

import broccolai.corn.paper.item.PaperItemBuilder;
import broccolai.corn.paper.item.special.SkullBuilder;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;

public final class BannerColorMenuProvider {

    private final LangConfig langConfig;
    private final ConfigConfig configConfig;

    @Inject
    public BannerColorMenuProvider(
            final @NonNull LangConfig langConfig,
            final @NonNull ConfigConfig configConfig
    ) {
        this.langConfig = langConfig;
        this.configConfig = configConfig;
    }

    private ItemStack createCustomDye(final Material material) {
        return PaperItemBuilder.ofType(material)
                .lore(this.langConfig.cl(NodePath.path("menus", "banner", "select")))
                .build();
    }

    public Inventory generate(final ItemStack oldBanner) {
        final Inventory inv = Bukkit.createInventory(
                null,
                54,
                this.langConfig.c(NodePath.path("menus", "banner", "color-inventory-name"))
        );

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Constants.Items.INTERFACE_BACKGROUND);
        }

        inv.setItem(3, SkullBuilder.ofType(Material.PLAYER_HEAD)
                .name(this.langConfig.c(NodePath.path("menus", "banner", "randomize")))
                .textures(this.configConfig.data().heads().banner().randomize())
                .build());
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
