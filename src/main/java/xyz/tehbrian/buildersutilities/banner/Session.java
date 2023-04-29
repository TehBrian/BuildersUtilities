package xyz.tehbrian.buildersutilities.banner;

import broccolai.corn.paper.item.PaperItemBuilder;
import broccolai.corn.paper.item.special.BannerBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.banner.menu.BaseMenuProvider;
import xyz.tehbrian.buildersutilities.banner.menu.ColorMenuProvider;
import xyz.tehbrian.buildersutilities.banner.menu.DoneMenuProvider;
import xyz.tehbrian.buildersutilities.banner.menu.PatternMenuProvider;
import xyz.tehbrian.buildersutilities.config.LangConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the state of a player's banner creation session.
 * <p>
 * Any session can be perfectly replicated with only the
 * data in this class. This allows sessions to be paused and resumed.
 */
public class Session {

  private static final ItemStack DEFAULT_BANNER = new ItemStack(Material.WHITE_BANNER);

  private final BaseMenuProvider baseProvider;
  private final ColorMenuProvider colorProvider;
  private final PatternMenuProvider patternProvider;
  private final DoneMenuProvider doneProvider;
  private final LangConfig langConfig;
  /**
   * The banner's current patterns.
   */
  private final List<Pattern> patterns = new ArrayList<>();
  /**
   * The banner's current base color.
   * <p>
   * This is only <b>null</b> iff the current step is {@code PICK_BASE}.
   */
  private @Nullable DyeColor baseColor = null;
  /**
   * The color that will be used for the next pattern.
   * <p>
   * This is only <b>non</b>-null iff the current step is {@code PICK_PATTERN}.
   */
  private @Nullable DyeColor nextPatternColor = null;

  public Session(
      final BaseMenuProvider baseProvider,
      final ColorMenuProvider colorProvider,
      final PatternMenuProvider patternProvider,
      final DoneMenuProvider doneProvider,
      final LangConfig langConfig
  ) {
    this.baseProvider = baseProvider;
    this.colorProvider = colorProvider;
    this.patternProvider = patternProvider;
    this.doneProvider = doneProvider;
    this.langConfig = langConfig;
  }

  /**
   * Builds the interface relevant to this session.
   * <p>
   * Although the player can change this session's state via buttons in the
   * interface, this method alone will not affect this session's state and
   * can be called multiple times without mutating state.
   *
   * @return the built interface
   */
  public Inventory buildInterface() {
    return switch (this.getStep()) {
      case PICK_BASE -> this.baseProvider.generate(this);
      case PICK_COLOR -> this.colorProvider.generate(this);
      case PICK_PATTERN -> this.patternProvider.generate(this);
      case DONE -> this.doneProvider.generate(this);
    };
  }

  /**
   * Opens an interface built via {@link #buildInterface()} to the player.
   *
   * @param player the player to show the built interface to
   */
  public void showInterface(final Player player) {
    player.openInventory(this.buildInterface());
  }

  public Step getStep() {
    if (this.patterns.size() >= 16) {
      return Step.DONE;
    }
    if (this.baseColor == null) {
      return Step.PICK_BASE;
    }
    if (this.nextPatternColor == null) {
      return Step.PICK_COLOR;
    }
    return Step.PICK_PATTERN;
  }

  public @Nullable DyeColor nextPatternColor() {
    return this.nextPatternColor;
  }

  public void nextPatternColor(final @Nullable DyeColor nextPatternColor) {
    this.nextPatternColor = nextPatternColor;
  }

  public void baseColor(final @Nullable DyeColor baseColor) {
    this.baseColor = baseColor;
  }

  public ItemStack generateInterfaceBanner() {
    return PaperItemBuilder.of(this.generateBanner())
        .name(this.langConfig.c(NodePath.path("menus", "banner", "get-banner")))
        .build();
  }

  public ItemStack generateBanner() {
    if (this.baseColor == null) {
      return DEFAULT_BANNER;
    } else {
      return BannerBuilder.ofType(Util.bannerFromColor(this.baseColor))
          .patterns(this.patterns)
          .build();
    }
  }

  public List<Pattern> patterns() {
    return patterns;
  }

  /**
   * A step of the session.
   * <p>
   * Sessions start with {@code PICK_BASE} and then flip back and forth
   * between {@code PICK_COLOR} and {@code PICK_PATTERN}.
   */
  enum Step {
    PICK_BASE,
    PICK_COLOR,
    PICK_PATTERN,
    DONE,
  }

}
