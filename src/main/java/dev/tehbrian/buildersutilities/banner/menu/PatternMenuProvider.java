package dev.tehbrian.buildersutilities.banner.menu;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.Buttons;
import dev.tehbrian.buildersutilities.banner.Sayge;
import dev.tehbrian.buildersutilities.banner.Session;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.util.ChestSize;
import dev.tehbrian.buildersutilities.util.MenuItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.Inventory;
import org.spongepowered.configurate.NodePath;

import static java.util.Objects.requireNonNull;
import static love.broccolai.corn.minecraft.item.special.BannerBuilder.bannerBuilder;

public final class PatternMenuProvider {

  private final LangConfig langConfig;
  private final ConfigConfig configConfig;

  @Inject
  public PatternMenuProvider(
      final LangConfig langConfig,
      final ConfigConfig configConfig
  ) {
    this.langConfig = langConfig;
    this.configConfig = configConfig;
  }

  public Inventory generate(final Session session) {
    final Inventory inv = Bukkit.createInventory(
        null,
        ChestSize.DOUBLE,
        this.langConfig.c(NodePath.path("menus", "banner", "pattern-inventory-name"))
    );

    for (int i = 0; i < inv.getSize(); i++) {
      inv.setItem(i, MenuItems.BACKGROUND);
    }

    Buttons.addToolbar(inv, this.langConfig, this.configConfig, session.generateInterfaceBanner());

    requireNonNull(session.nextPatternColor());
    final Material displayBase = switch (session.nextPatternColor()) {
      case WHITE, LIGHT_GRAY, LIME, LIGHT_BLUE, YELLOW -> Material.BLACK_BANNER;
      default -> Material.WHITE_BANNER;
    };

    for (int i = 0; i < Sayge.patternTypes().size(); i++) {
      inv.setItem(i + 9, bannerBuilder(displayBase)
          .lore(this.langConfig.cl(NodePath.path("menus", "banner", "select")))
          .addPattern(new Pattern(session.nextPatternColor(), Sayge.patternTypes().get(i)))
          .build()
      );
    }

    return inv;
  }

}
