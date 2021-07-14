package xyz.tehbrian.buildersutilities.util;

import net.kyori.adventure.text.Component;
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

    public static ItemStack createBanner(final Material material, final List<Component> lore, final Pattern pattern) {
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
        return switch (dyeColor) {
            case WHITE -> Material.WHITE_BANNER;
            case ORANGE -> Material.ORANGE_BANNER;
            case MAGENTA -> Material.MAGENTA_BANNER;
            case LIGHT_BLUE -> Material.LIGHT_BLUE_BANNER;
            case YELLOW -> Material.YELLOW_BANNER;
            case LIME -> Material.LIME_BANNER;
            case PINK -> Material.PINK_BANNER;
            case GRAY -> Material.GRAY_BANNER;
            case LIGHT_GRAY -> Material.LIGHT_GRAY_BANNER;
            case CYAN -> Material.CYAN_BANNER;
            case PURPLE -> Material.PURPLE_BANNER;
            case BLUE -> Material.BLUE_BANNER;
            case BROWN -> Material.BROWN_BANNER;
            case GREEN -> Material.GREEN_BANNER;
            case RED -> Material.RED_BANNER;
            case BLACK -> Material.BLACK_BANNER;
        };
    }

    public static Pattern getPattern(final ItemStack itemStack) {
        BannerMeta meta = (BannerMeta) itemStack.getItemMeta();
        return Objects.requireNonNull(meta).getPattern(0);
    }

    public static DyeColor getDyeColor(final Material material) {
        return switch (material) {
            case WHITE_BANNER, WHITE_DYE -> DyeColor.WHITE;
            case ORANGE_BANNER, ORANGE_DYE -> DyeColor.ORANGE;
            case MAGENTA_BANNER, MAGENTA_DYE -> DyeColor.MAGENTA;
            case LIGHT_BLUE_BANNER, LIGHT_BLUE_DYE -> DyeColor.LIGHT_BLUE;
            case YELLOW_BANNER, YELLOW_DYE -> DyeColor.YELLOW;
            case LIME_BANNER, LIME_DYE -> DyeColor.LIME;
            case PINK_BANNER, PINK_DYE -> DyeColor.PINK;
            case GRAY_BANNER, GRAY_DYE -> DyeColor.GRAY;
            case LIGHT_GRAY_BANNER, LIGHT_GRAY_DYE -> DyeColor.LIGHT_GRAY;
            case CYAN_BANNER, CYAN_DYE -> DyeColor.CYAN;
            case PURPLE_BANNER, PURPLE_DYE -> DyeColor.PURPLE;
            case BLUE_BANNER, BLUE_DYE -> DyeColor.BLUE;
            case BROWN_BANNER, BROWN_DYE -> DyeColor.BROWN;
            case GREEN_BANNER, GREEN_DYE -> DyeColor.GREEN;
            case RED_BANNER, RED_DYE -> DyeColor.RED;
            case BLACK_BANNER, BLACK_DYE -> DyeColor.BLACK;
            default -> throw new IllegalArgumentException();
        };
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
