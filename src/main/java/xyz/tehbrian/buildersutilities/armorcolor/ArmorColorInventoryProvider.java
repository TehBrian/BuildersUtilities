package xyz.tehbrian.buildersutilities.armorcolor;

import broccolai.corn.paper.item.PaperItemBuilder;
import broccolai.corn.paper.item.special.LeatherArmorBuilder;
import broccolai.corn.paper.item.special.SkullBuilder;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.ConfigUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ArmorColorInventoryProvider {

    private final Lang lang;

    @Inject
    public ArmorColorInventoryProvider(
            final @NonNull Lang lang
    ) {
        this.lang = lang;
    }

    public Inventory generate() {
        final Inventory inv = Bukkit.createInventory(null, 54, this.lang.c("messages.inventories.armor_color.inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Constants.Items.INTERFACE_BACKGROUND);
        }

        inv.setItem(
                10,
                PaperItemBuilder.ofType(Material.LEATHER_HELMET)
                        .lore(this.lang.c("messages.inventories.armor_color.get_helmet"))
                        .build()
        );
        inv.setItem(
                19,
                PaperItemBuilder.ofType(Material.LEATHER_CHESTPLATE)
                        .lore(this.lang.c("messages.inventories.armor_color.get_chestplate"))
                        .build()
        );
        inv.setItem(
                28,
                PaperItemBuilder.ofType(Material.LEATHER_LEGGINGS)
                        .lore(this.lang.c("messages.inventories.armor_color.get_leggings"))
                        .build()
        );
        inv.setItem(
                37,
                PaperItemBuilder.ofType(Material.LEATHER_BOOTS)
                        .lore(this.lang.c("messages.inventories.armor_color.get_boots"))
                        .build()
        );

        final List<Component> lore = this.lang.cl("messages.inventories.armor_color.change");

        inv.setItem(
                22,
                SkullBuilder.ofType(Material.PLAYER_HEAD)
                        .name(this.lang.c("messages.inventories.armor_color.randomize_red"))
                        .textures(ConfigUtils.getString("heads.armor_color.randomize_red"))
                        .build()
        );
        inv.setItem(
                23,
                SkullBuilder.ofType(Material.PLAYER_HEAD)
                        .name(this.lang.c("messages.inventories.armor_color.randomize_green"))
                        .textures(ConfigUtils.getString("heads.armor_color.randomize_green"))
                        .build()
        );
        inv.setItem(
                24,
                SkullBuilder.ofType(Material.PLAYER_HEAD)
                        .name(this.lang.c("messages.inventories.armor_color.randomize_blue"))
                        .textures(ConfigUtils.getString("heads.armor_color.randomize_blue"))
                        .build()
        );
        inv.setItem(
                31,
                SkullBuilder.ofType(Material.PLAYER_HEAD).amount(16)
                        .name(this.lang.c("messages.inventories.armor_color.red"))
                        .lore(lore)
                        .textures(ConfigUtils.getString("heads.armor_color.red"))
                        .build()
        );
        inv.setItem(
                32,
                SkullBuilder.ofType(Material.PLAYER_HEAD).amount(16)
                        .name(this.lang.c("messages.inventories.armor_color.green"))
                        .lore(lore)
                        .textures(ConfigUtils.getString("heads.armor_color.green"))
                        .build()
        );
        inv.setItem(
                33,
                SkullBuilder.ofType(Material.PLAYER_HEAD).amount(16)
                        .name(this.lang.c("messages.inventories.armor_color.blue"))
                        .lore(lore)
                        .textures(ConfigUtils.getString("heads.armor_color.blue"))
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
