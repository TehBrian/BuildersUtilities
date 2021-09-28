package xyz.tehbrian.buildersutilities;

import broccolai.corn.paper.item.PaperItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.config.LangConfig;

/**
 * Constant values.
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
                .name(LangConfig.EMPTY)
                .build();

        private Items() {
        }

    }

    /**
     * Permissions.
     */
    public static final class Permissions {

        public static final String ROOT = "buildersutilities";

        public static final String BANNER = ROOT + ".banner";
        public static final String ARMOR_COLOR = ROOT + ".armorcolor";
        public static final String BUILDERS_UTILITIES = ROOT + ".buildersutilities";

        public static final String ADVANCED_FLY = ROOT + ".advancedfly";
        public static final String NIGHT_VISION = ROOT + ".nightvision";
        public static final String NO_CLIP = ROOT + ".noclip";

        public static final String IRON_DOOR_TOGGLE = ROOT + ".irondoortoggle";
        public static final String DOUBLE_SLAB_BREAK = ROOT + ".doubleslabbreak";
        public static final String GLAZED_TERRACOTTA_ROTATE = ROOT + ".glazedterracottarotate";

        public static final String RC = ROOT + ".rc";
        public static final String RELOAD = ROOT + ".reload";

        public static final String SPECTATE_TELEPORT = ROOT + ".spectate.teleport";
        public static final String SPECTATE_ENTITY = ROOT + ".spectate.entity";
        public static final String SPECTATE_PLAYER = ROOT + ".spectate.player";

        private Permissions() {
        }

    }

}
