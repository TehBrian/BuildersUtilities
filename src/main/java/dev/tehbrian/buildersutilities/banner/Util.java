package dev.tehbrian.buildersutilities.banner;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.PatternType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Util {

  public static final Random RANDOM = new Random();

  private Util() {
  }

  public static Material bannerFromColor(final DyeColor dyeColor) {
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

  public static DyeColor colorFromItem(final Material material) {
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

  public static List<DyeColor> dyeColors() {
    return Arrays.asList(DyeColor.values());
  }

  /**
   * Compiles a list of all pattern types except {@link PatternType#BASE}.
   *
   * @return a list of all pattern types
   */
  public static List<PatternType> patternTypes() {
    final List<PatternType> allPatternTypes = new ArrayList<>();

    for (final PatternType patternType : PatternType.values()) {
      if (patternType != PatternType.BASE) {
        allPatternTypes.add(patternType);
      }
    }

    return allPatternTypes;
  }

  public static DyeColor randomDyeColor() {
    return dyeColors().get(RANDOM.nextInt(dyeColors().size()));
  }

  public static PatternType randomPatternType() {
    return patternTypes().get(RANDOM.nextInt(patternTypes().size()));
  }

}
