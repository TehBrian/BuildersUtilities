package xyz.tehbrian.buildersutilities.banner.listener;

import broccolai.corn.paper.item.special.BannerBuilder;
import com.google.inject.Inject;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorMenuProvider;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.BannerUtil;

import java.util.Objects;

public final class BannerBaseMenuListener implements Listener {

  private final BannerColorMenuProvider bannerColorMenuProvider;
  private final LangConfig langConfig;

  @Inject
  public BannerBaseMenuListener(
      final BannerColorMenuProvider bannerColorMenuProvider,
      final LangConfig langConfig
  ) {
    this.bannerColorMenuProvider = bannerColorMenuProvider;
    this.langConfig = langConfig;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(final InventoryClickEvent event) {
    if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
        || !event.getView().title().equals(this.langConfig.c(NodePath.path("menus", "banner", "base-inventory-name")))
        || !(event.getWhoClicked() instanceof Player player)) {
      return;
    }

    final int slot = event.getRawSlot();

    event.setCancelled(true);

    if (slot == 3) {
      final DyeColor dyeColor = BannerUtil.randomDyeColor();
      final ItemStack newBanner = BannerBuilder.ofType(BannerUtil.colorToBanner(dyeColor))
          .name(this.langConfig.c(NodePath.path("menus", "banner", "get-banner")))
          .build();
      player.openInventory(this.bannerColorMenuProvider.generate(newBanner));
    }

    if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
      final DyeColor dyeColor = BannerUtil.bannerToColor(Objects.requireNonNull(event.getCurrentItem()).getType());
      final ItemStack newBanner = BannerBuilder.ofType(BannerUtil.colorToBanner(dyeColor))
          .name(this.langConfig.c(NodePath.path("menus", "banner", "get-banner")))
          .build();
      player.openInventory(this.bannerColorMenuProvider.generate(newBanner));
    }
  }

}
