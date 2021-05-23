package xyz.tehbrian.buildersutilities.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.tehbrian.buildersutilities.config.Lang;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ItemUtils {

    private ItemUtils() {
    }

    public static ItemStack create(final Material material, final int amount, final Component name, final List<Component> lore) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(amount);

        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta);

        if (name != null) {
            itemMeta.displayName(name.decoration(TextDecoration.ITALIC, false));
        }
        if (lore != null) {
            lore.replaceAll((item) -> item.decoration(TextDecoration.ITALIC, false));
            itemMeta.lore(lore);
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack create(final Material material, final int amount, final Component name) {
        return create(material, amount, name, null);
    }

    public static ItemStack create(final Material material, final int amount, final List<Component> lore) {
        return create(material, amount, null, lore);
    }

    public static ItemStack createHead(final String data, final int amount, final Component name, final List<Component> lore) {
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

    public static ItemStack createHead(final String data, final int amount, final Component name) {
        return createHead(data, amount, name, new ArrayList<>());
    }

    public static ItemStack colorLeatherArmor(final ItemStack itemStack, final int red, final int green, final int blue) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();

        Color color = Color.fromRGB(red, green, blue);
        Objects.requireNonNull(itemMeta).setColor(color);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack removeName(final ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).displayName(null);

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
