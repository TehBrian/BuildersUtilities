package xyz.tehbrian.buildersutilities.armorcolor;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.ConfigUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

import java.util.List;
import java.util.Objects;

public final class ArmorColorInventoryProvider {

    private final Lang lang;

    @Inject
    public ArmorColorInventoryProvider(
            final @NonNull Lang lang
    ) {
        this.lang = lang;
    }

    public Inventory generate() {
        Inventory inv = Bukkit.createInventory(null, 54, this.lang.c("messages.inventories.armor_color.inventory_name"));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, Lang.EMPTY));
        }

        inv.setItem(10, ItemUtils.create(Material.LEATHER_HELMET, 1, this.lang.c("messages.inventories.armor_color.get_helmet")));
        inv.setItem(19, ItemUtils.create(Material.LEATHER_CHESTPLATE, 1, this.lang.c("messages.inventories.armor_color.get_chestplate")));
        inv.setItem(28, ItemUtils.create(Material.LEATHER_LEGGINGS, 1, this.lang.c("messages.inventories.armor_color.get_leggings")));
        inv.setItem(37, ItemUtils.create(Material.LEATHER_BOOTS, 1, this.lang.c("messages.inventories.armor_color.get_boots")));

        List<Component> lore = this.lang.cl("messages.inventories.armor_color.change");

        inv.setItem(22, ItemUtils.createHead(ConfigUtils.getString("heads.armor_color.randomize_red"), 1, this.lang.c("messages.inventories.armor_color.randomize_red")));
        inv.setItem(23, ItemUtils.createHead(ConfigUtils.getString("heads.armor_color.randomize_green"), 1, this.lang.c("messages.inventories.armor_color.randomize_green")));
        inv.setItem(24, ItemUtils.createHead(ConfigUtils.getString("heads.armor_color.randomize_blue"), 1, this.lang.c("messages.inventories.armor_color.randomize_blue")));
        inv.setItem(31, ItemUtils.createHead(ConfigUtils.getString("heads.armor_color.red"), 16, this.lang.c("messages.inventories.armor_color.red"), lore));
        inv.setItem(32, ItemUtils.createHead(ConfigUtils.getString("heads.armor_color.green"), 16, this.lang.c("messages.inventories.armor_color.green"), lore));
        inv.setItem(33, ItemUtils.createHead(ConfigUtils.getString("heads.armor_color.blue"), 16, this.lang.c("messages.inventories.armor_color.blue"), lore));

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

        inv.setItem(10, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(10)), r, g, b));
        inv.setItem(19, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(19)), r, g, b));
        inv.setItem(28, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(28)), r, g, b));
        inv.setItem(37, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(37)), r, g, b));
    }

}
