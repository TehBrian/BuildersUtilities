package xyz.tehbrian.buildersutilities.banner.listener;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.inject.Inject;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.banner.provider.BannerPatternMenuProvider;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.BannerUtil;

import java.util.Objects;

@SuppressWarnings("ClassCanBeRecord")
public final class BannerColorMenuListener implements Listener {

  private final BannerPatternMenuProvider bannerPatternMenuProvider;
  private final LangConfig langConfig;

  @Inject
  public BannerColorMenuListener(
      final @NonNull BannerPatternMenuProvider bannerPatternMenuProvider,
      final @NonNull LangConfig langConfig
  ) {
    this.bannerPatternMenuProvider = bannerPatternMenuProvider;
    this.langConfig = langConfig;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(final InventoryClickEvent event) {
    if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
        || !event.getView().title().equals(this.langConfig.c(NodePath.path("menus", "banner", "color-inventory-name")))
        || !(event.getWhoClicked() instanceof Player player)) {
      return;
    }

    final int slot = event.getRawSlot();

    final Inventory inventory = event.getClickedInventory();
    Objects.requireNonNull(inventory);

    final ItemStack oldBanner = inventory.getItem(5);
    Objects.requireNonNull(oldBanner);
    final PaperItemBuilder oldBannerBuilder = PaperItemBuilder.of(oldBanner);

    event.setCancelled(true);

    if (slot == 3) {
      final DyeColor dyeColor = BannerUtil.randomDyeColor();
      player.openInventory(this.bannerPatternMenuProvider.generate(oldBanner, dyeColor));
    }

    if (slot == 5) {
      player.getInventory().addItem(oldBannerBuilder.name(null).build());
      player.closeInventory();
    }

    if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
      final DyeColor dyeColor = BannerUtil.bannerToColor(Objects.requireNonNull(event.getCurrentItem()).getType());
      player.openInventory(this.bannerPatternMenuProvider.generate(oldBanner, dyeColor));
    }
  }

}
