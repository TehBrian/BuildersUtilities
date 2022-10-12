package xyz.tehbrian.buildersutilities.banner.listener;

import broccolai.corn.paper.item.special.BannerBuilder;
import com.google.inject.Inject;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorMenuProvider;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.BannerUtil;

import java.util.Objects;

@SuppressWarnings("ClassCanBeRecord")
public final class BannerPatternMenuListener implements Listener {

  private final BannerColorMenuProvider bannerColorMenuProvider;
  private final LangConfig langConfig;

  @Inject
  public BannerPatternMenuListener(
      final @NonNull BannerColorMenuProvider bannerColorMenuProvider,
      final @NonNull LangConfig langConfig
  ) {
    this.bannerColorMenuProvider = bannerColorMenuProvider;
    this.langConfig = langConfig;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(final InventoryClickEvent event) {
    if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
        || !event.getView().title().equals(this.langConfig.c(NodePath.path(
        "menus",
        "banner",
        "pattern-inventory-name"
    )))
        || !(event.getWhoClicked() instanceof Player player)) {
      return;
    }

    final int slot = event.getRawSlot();

    final Inventory inventory = event.getClickedInventory();
    Objects.requireNonNull(inventory);

    final BannerBuilder oldBannerBuilder = BannerBuilder.of(Objects.requireNonNull(inventory.getItem(5)));

    event.setCancelled(true);

    // the get banner button
    if (slot == 5) {
      player.getInventory().addItem(oldBannerBuilder.name(null).build());
      player.closeInventory();
      return;
    }

    // the random button
    if (slot == 3) {
      final DyeColor currentColor = BannerBuilder
          .of(Objects.requireNonNull(inventory.getItem(9)))
          .getPattern(0)
          .getColor();

      final var newBannerBuilder = oldBannerBuilder
          .addPattern(new Pattern(currentColor, BannerUtil.randomPatternType()));

      if (newBannerBuilder.patterns().size() >= 16) {
        player.getInventory().addItem(newBannerBuilder.name(null).build());
        player.closeInventory();
      } else {
        player.openInventory(this.bannerColorMenuProvider.generate(newBannerBuilder.build()));
      }
    }

    // the various banner buttons
    if (slot >= 9 && slot <= (8 + BannerUtil.patternTypes().size())) {
      final var newBannerBuilder = oldBannerBuilder
          .addPattern(BannerBuilder.of(Objects.requireNonNull(event.getCurrentItem())).getPattern(0));

      if (newBannerBuilder.patterns().size() >= 16) {
        player.getInventory().addItem(newBannerBuilder.name(null).build());
        player.closeInventory();
      } else {
        player.openInventory(this.bannerColorMenuProvider.generate(newBannerBuilder.build()));
      }
    }
  }

}
