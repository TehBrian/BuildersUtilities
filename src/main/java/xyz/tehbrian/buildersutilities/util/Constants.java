package xyz.tehbrian.buildersutilities.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.config.Lang;

public class Constants {

    public static class Items {

        public static final ItemStack INTERFACE_BACKGROUND = ItemUtils.create(
                Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                1,
                Lang.EMPTY
        );

    }

}
