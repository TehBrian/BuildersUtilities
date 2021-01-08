package xyz.tehbrian.buildersutilities.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemUtils {

    private ItemUtils() {
    }

    public static ItemStack create(Material material, int amount, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(amount);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name != null) {
            Objects.requireNonNull(itemMeta).setDisplayName(MessageUtils.color(name));
        }
        if (lore != null) {
            lore.replaceAll(MessageUtils::color);
            Objects.requireNonNull(itemMeta).setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack create(Material material, int amount, String name) {
        return create(material, amount, name, null);
    }

    public static ItemStack create(Material material, int amount, List<String> lore) {
        return create(material, amount, null, lore);
    }

    public static ItemStack createHead(String data, int amount, String name, List<String> lore) {
        ItemStack itemStack = create(Material.PLAYER_HEAD, amount, name, lore);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", data));

        Field profileField;
        try {
            profileField = Objects.requireNonNull(itemMeta).getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createHead(String data, int amount, String name) {
        return createHead(data, amount, name, new ArrayList<>());
    }

    public static ItemStack colorLeatherArmor(ItemStack itemStack, int red, int green, int blue) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();

        Color color = Color.fromRGB(red, green, blue);
        Objects.requireNonNull(itemMeta).setColor(color);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack removeName(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(null);

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
