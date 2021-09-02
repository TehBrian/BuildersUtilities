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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.BannerUtils;

import java.util.Objects;

public final class BannerBaseInventoryListener implements Listener {

    private final BannerColorInventoryProvider bannerColorInventoryProvider;
    private final LangConfig lang;

    @Inject
    public BannerBaseInventoryListener(
            final @NonNull BannerColorInventoryProvider bannerColorInventoryProvider,
            final @NonNull LangConfig lang
    ) {
        this.bannerColorInventoryProvider = bannerColorInventoryProvider;
        this.lang = lang;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().title().equals(this.lang.c(NodePath.path("inventories", "banner", "base-inventory-name")))
                || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        final int slot = event.getRawSlot();

        event.setCancelled(true);

        if (slot == 3) {
            final DyeColor dyeColor = BannerUtils.randomDyeColor();
            final ItemStack newBanner = BannerBuilder.ofType(BannerUtils.colorToBanner(dyeColor))
                    .name(this.lang.c(NodePath.path("inventories", "banner", "get-banner")))
                    .build();
            player.openInventory(this.bannerColorInventoryProvider.generate(newBanner));
        }

        if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
            final DyeColor dyeColor = BannerUtils.bannerToColor(Objects.requireNonNull(event.getCurrentItem()).getType());
            final ItemStack newBanner = BannerBuilder.ofType(BannerUtils.colorToBanner(dyeColor))
                    .name(this.lang.c(NodePath.path("inventories", "banner", "get-banner")))
                    .build();
            player.openInventory(this.bannerColorInventoryProvider.generate(newBanner));
        }
    }

}
