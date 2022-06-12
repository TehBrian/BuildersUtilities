package xyz.tehbrian.buildersutilities.armorcolor;

import broccolai.corn.paper.item.PaperItemBuilder;
import broccolai.corn.paper.item.special.LeatherArmorBuilder;
import broccolai.corn.paper.item.special.SkullBuilder;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.ChestSize;
import xyz.tehbrian.buildersutilities.util.Items;

import java.util.List;
import java.util.Objects;

public final class ArmorColorMenuProvider {

    private final LangConfig langConfig;
    private final ConfigConfig configConfig;

    @Inject
    public ArmorColorMenuProvider(
            final @NonNull LangConfig langConfig,
            final @NonNull ConfigConfig configConfig
    ) {
        this.langConfig = langConfig;
        this.configConfig = configConfig;
    }

    public Inventory generate() {
        final Inventory inv = Bukkit.createInventory(
                null,
                ChestSize.DOUBLE,
                this.langConfig.c(NodePath.path("menus", "armor-color", "inventory-name"))
        );

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Items.INTERFACE_BACKGROUND);
        }

        inv.setItem(
                10,
                PaperItemBuilder.ofType(Material.LEATHER_HELMET)
                        .lore(this.langConfig.cl(NodePath.path("menus", "armor-color", "get-helmet")))
                        .build()
        );
        inv.setItem(
                19,
                PaperItemBuilder.ofType(Material.LEATHER_CHESTPLATE)
                        .lore(this.langConfig.cl(NodePath.path("menus", "armor-color", "get-chestplate")))
                        .build()
        );
        inv.setItem(
                28,
                PaperItemBuilder.ofType(Material.LEATHER_LEGGINGS)
                        .lore(this.langConfig.cl(NodePath.path("menus", "armor-color", "get-leggings")))
                        .build()
        );
        inv.setItem(
                37,
                PaperItemBuilder.ofType(Material.LEATHER_BOOTS)
                        .lore(this.langConfig.cl(NodePath.path("menus", "armor-color", "get-boots")))
                        .build()
        );

        final List<Component> lore = this.langConfig.cl(NodePath.path("menus", "armor-color", "change"));

        inv.setItem(
                22,
                SkullBuilder.ofType(Material.PLAYER_HEAD)
                        .name(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-red")))
                        .textures(this.configConfig.data().heads().armorColor().randomizeRed())
                        .build()
        );
        inv.setItem(
                23,
                SkullBuilder.ofType(Material.PLAYER_HEAD)
                        .name(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-green")))
                        .textures(this.configConfig.data().heads().armorColor().randomizeGreen())
                        .build()
        );
        inv.setItem(
                24,
                SkullBuilder.ofType(Material.PLAYER_HEAD)
                        .name(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-blue")))
                        .textures(this.configConfig.data().heads().armorColor().randomizeBlue())
                        .build()
        );
        inv.setItem(
                31,
                SkullBuilder.ofType(Material.PLAYER_HEAD).amount(16)
                        .name(this.langConfig.c(NodePath.path("menus", "armor-color", "red")))
                        .lore(lore)
                        .textures(this.configConfig.data().heads().armorColor().red())
                        .build()
        );
        inv.setItem(
                32,
                SkullBuilder.ofType(Material.PLAYER_HEAD).amount(16)
                        .name(this.langConfig.c(NodePath.path("menus", "armor-color", "green")))
                        .lore(lore)
                        .textures(this.configConfig.data().heads().armorColor().green())
                        .build()
        );
        inv.setItem(
                33,
                SkullBuilder.ofType(Material.PLAYER_HEAD).amount(16)
                        .name(this.langConfig.c(NodePath.path("menus", "armor-color", "blue")))
                        .lore(lore)
                        .textures(this.configConfig.data().heads().armorColor().blue())
                        .build()
        );

        this.update(inv);

        return inv;
    }

    public void update(final Inventory inv) {
        int r = (Objects.requireNonNull(inv.getItem(31)).getAmount() - 1) * 8;
        int g = (Objects.requireNonNull(inv.getItem(32)).getAmount() - 1) * 8;
        int b = (Objects.requireNonNull(inv.getItem(33)).getAmount() - 1) * 8;

        if (r == 256) {
            r = 255;
        }

        if (g == 256) {
            g = 255;
        }

        if (b == 256) {
            b = 255;
        }

        final Color finalColor = Color.fromRGB(r, g, b);

        inv.setItem(10, LeatherArmorBuilder.of(Objects.requireNonNull(inv.getItem(10))).color(finalColor).build());
        inv.setItem(19, LeatherArmorBuilder.of(Objects.requireNonNull(inv.getItem(19))).color(finalColor).build());
        inv.setItem(28, LeatherArmorBuilder.of(Objects.requireNonNull(inv.getItem(28))).color(finalColor).build());
        inv.setItem(37, LeatherArmorBuilder.of(Objects.requireNonNull(inv.getItem(37))).color(finalColor).build());
    }

}
