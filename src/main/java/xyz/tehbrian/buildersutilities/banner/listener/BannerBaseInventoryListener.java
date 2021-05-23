package xyz.tehbrian.buildersutilities.banner.listener;

import com.google.inject.Inject;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

import java.util.Objects;

public final class BannerBaseInventoryListener implements Listener {

    private final BannerColorInventoryProvider bannerColorInventoryProvider;
    private final Lang lang;

    @Inject
    public BannerBaseInventoryListener(
            final @NonNull BannerColorInventoryProvider bannerColorInventoryProvider,
            final @NonNull Lang lang
    ) {
        this.bannerColorInventoryProvider = bannerColorInventoryProvider;
        this.lang = lang;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().title().equals(this.lang.c("messages.inventories.banner.base_inventory_name"))
                || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        event.setCancelled(true);

        if (slot == 3) {
            DyeColor dyeColor = BannerUtils.getRandomDyeColor();
            ItemStack newBanner = ItemUtils.create(
                    BannerUtils.getBanner(dyeColor),
                    1,
                    this.lang.c("messages.inventories.banner.get_banner"));
            player.openInventory(this.bannerColorInventoryProvider.generate(newBanner));
        }

        if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
            DyeColor dyeColor = BannerUtils.getDyeColor(Objects.requireNonNull(event.getCurrentItem()).getType());
            ItemStack newBanner = ItemUtils.create(BannerUtils.getBanner(dyeColor),
                    1,
                    this.lang.c("messages.inventories.banner.get_banner"));
            player.openInventory(this.bannerColorInventoryProvider.generate(newBanner));
        }
    }
}
