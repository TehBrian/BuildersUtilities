package xyz.tehbrian.buildersutilities.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.List;
import java.util.Objects;

public class ArmorColorInventory {

    private ArmorColorInventory() {
    }

    public static Inventory generate() {
        Inventory inv = Bukkit.createInventory(null, 54, MessageUtils.getMessage("msg.armor_color.inventory_name"));

        for (int x = 0; x < inv.getSize(); x++) {
            inv.setItem(x, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "&7"));
        }

        inv.setItem(10, ItemUtils.create(Material.LEATHER_HELMET, 1, MessageUtils.getMessage("msg.armor_color.get_helmet")));
        inv.setItem(19, ItemUtils.create(Material.LEATHER_CHESTPLATE, 1, MessageUtils.getMessage("msg.armor_color.get_chestplate")));
        inv.setItem(28, ItemUtils.create(Material.LEATHER_LEGGINGS, 1, MessageUtils.getMessage("msg.armor_color.get_leggings")));
        inv.setItem(37, ItemUtils.create(Material.LEATHER_BOOTS, 1, MessageUtils.getMessage("msg.armor_color.get_boots")));

        List<String> lore = MessageUtils.getMessageList("msg.armor_color.change");

        inv.setItem(22, ItemUtils.createHead(MessageUtils.getMessage("head.armor_color.randomize_red"), 1, MessageUtils.getMessage("msg.armor_color.randomize_red")));
        inv.setItem(23, ItemUtils.createHead(MessageUtils.getMessage("head.armor_color.randomize_green"), 1, MessageUtils.getMessage("msg.armor_color.randomize_green")));
        inv.setItem(24, ItemUtils.createHead(MessageUtils.getMessage("head.armor_color.randomize_blue"), 1, MessageUtils.getMessage("msg.armor_color.randomize_blue")));
        inv.setItem(31, ItemUtils.createHead(MessageUtils.getMessage("head.armor_color.red"), 16, MessageUtils.getMessage("msg.armor_color.red"), lore));
        inv.setItem(32, ItemUtils.createHead(MessageUtils.getMessage("head.armor_color.green"), 16, MessageUtils.getMessage("msg.armor_color.green"), lore));
        inv.setItem(33, ItemUtils.createHead(MessageUtils.getMessage("head.armor_color.blue"), 16, MessageUtils.getMessage("msg.armor_color.blue"), lore));

        update(inv);

        return inv;
    }

    public static void update(Inventory inv) {
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
