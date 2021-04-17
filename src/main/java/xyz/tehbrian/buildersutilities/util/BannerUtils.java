package xyz.tehbrian.buildersutilities.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class BannerUtils {

    private BannerUtils() {
    }

    public static ItemStack createBanner(final Material material, final List<String> lore, final Pattern pattern) {
        ItemStack item = ItemUtils.create(material, 1, lore);

        BannerMeta meta = (BannerMeta) item.getItemMeta();
        Objects.requireNonNull(meta).addPattern(pattern);

        item.setItemMeta(meta);
        return item;
    }

    public static void addPattern(final ItemStack itemStack, final Pattern pattern) {
        BannerMeta meta = (BannerMeta) itemStack.getItemMeta();

        List<Pattern> patterns = Objects.requireNonNull(meta).getPatterns();
        patterns.add(pattern);
        meta.setPatterns(patterns);

        itemStack.setItemMeta(meta);
    }

    public static Material getBanner(final DyeColor dyeColor) {
        switch (dyeColor) {
            default:
            case WHITE:
                return Material.WHITE_BANNER;
            case ORANGE:
                return Material.ORANGE_BANNER;
            case MAGENTA:
                return Material.MAGENTA_BANNER;
            case LIGHT_BLUE:
                return Material.LIGHT_BLUE_BANNER;
            case YELLOW:
                return Material.YELLOW_BANNER;
            case LIME:
                return Material.LIME_BANNER;
            case PINK:
                return Material.PINK_BANNER;
            case GRAY:
                return Material.GRAY_BANNER;
            case LIGHT_GRAY:
                return Material.LIGHT_GRAY_BANNER;
            case CYAN:
                return Material.CYAN_BANNER;
            case PURPLE:
                return Material.PURPLE_BANNER;
            case BLUE:
                return Material.BLUE_BANNER;
            case BROWN:
                return Material.BROWN_BANNER;
            case GREEN:
                return Material.GREEN_BANNER;
            case RED:
                return Material.RED_BANNER;
            case BLACK:
                return Material.BLACK_BANNER;
        }
    }

    public static Pattern getPattern(final ItemStack itemStack) {
        BannerMeta meta = (BannerMeta) itemStack.getItemMeta();
        return Objects.requireNonNull(meta).getPattern(0);
    }

    public static DyeColor getDyeColor(final Material material) {
        switch (material) {
            default:
            case WHITE_BANNER:
            case WHITE_DYE:
                return DyeColor.WHITE;
            case ORANGE_BANNER:
            case ORANGE_DYE:
                return DyeColor.ORANGE;
            case MAGENTA_BANNER:
            case MAGENTA_DYE:
                return DyeColor.MAGENTA;
            case LIGHT_BLUE_BANNER:
            case LIGHT_BLUE_DYE:
                return DyeColor.LIGHT_BLUE;
            case YELLOW_BANNER:
            case YELLOW_DYE:
                return DyeColor.YELLOW;
            case LIME_BANNER:
            case LIME_DYE:
                return DyeColor.LIME;
            case PINK_BANNER:
            case PINK_DYE:
                return DyeColor.PINK;
            case GRAY_BANNER:
            case GRAY_DYE:
                return DyeColor.GRAY;
            case LIGHT_GRAY_BANNER:
            case LIGHT_GRAY_DYE:
                return DyeColor.LIGHT_GRAY;
            case CYAN_BANNER:
            case CYAN_DYE:
                return DyeColor.CYAN;
            case PURPLE_BANNER:
            case PURPLE_DYE:
                return DyeColor.PURPLE;
            case BLUE_BANNER:
            case BLUE_DYE:
                return DyeColor.BLUE;
            case BROWN_BANNER:
            case BROWN_DYE:
                return DyeColor.BROWN;
            case GREEN_BANNER:
            case GREEN_DYE:
                return DyeColor.GREEN;
            case RED_BANNER:
            case RED_DYE:
                return DyeColor.RED;
            case BLACK_BANNER:
            case BLACK_DYE:
                return DyeColor.BLACK;
        }
    }

    public static List<DyeColor> getAllDyeColors() {
        return Arrays.asList(DyeColor.values());
    }

    public static List<PatternType> getAllPatternTypes() {
        List<PatternType> allPatternTypes = new ArrayList<>();

        for (PatternType patternType : PatternType.values()) {
            if (patternType != PatternType.BASE) {
                allPatternTypes.add(patternType);
            }
        }

        return allPatternTypes;
    }

    public static DyeColor getRandomDyeColor() {
        Random r = new Random();
        return getAllDyeColors().get(r.nextInt(getAllDyeColors().size()));
    }

    public static PatternType getRandomPatternType() {
        Random r = new Random();
        return getAllPatternTypes().get(r.nextInt(getAllPatternTypes().size()));
    }
}
