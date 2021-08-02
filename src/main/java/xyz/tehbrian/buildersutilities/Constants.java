package xyz.tehbrian.buildersutilities;

import broccolai.corn.paper.item.PaperItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.config.Lang;

/**
 * Constant values used in the plugin.
 */
public final class Constants {

    private Constants() {
    }

    /**
     * Items.
     */
    public static final class Items {

        public static final ItemStack INTERFACE_BACKGROUND = PaperItemBuilder
                .ofType(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .name(Lang.EMPTY)
                .build();

        private Items() {
        }

    }

    /**
     * Permissions.
     */
    public static final class Permissions {

        public static final String ADVANCED_FLY = "buildersutilities.advancedfly";
        public static final String NIGHT_VISION = "buildersutilities.nightvision";
        public static final String NO_CLIP = "buildersutilities.noclip";

        public static final String IRON_DOOR_TOGGLE = "buildersutilities.irondoortoggle";
        public static final String DOUBLE_SLAB_BREAK = "buildersutilities.doubleslabbreak";
        public static final String GLAZED_TERRACOTTA_ROTATE = "buildersutilities.glazedterracottarotate";

        public static final String RELOAD = "buildersutilities.reload";
        public static final String TPGM3 = "buildersutilities.tpgm3";

        private Permissions() {
        }

    }

}
