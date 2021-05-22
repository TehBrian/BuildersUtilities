package xyz.tehbrian.buildersutilities.banner.listener;

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
import xyz.tehbrian.buildersutilities.banner.provider.BannerPatternInventoryProvider;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.Objects;

public final class BannerColorInventoryListener implements Listener {

    private final BannerPatternInventoryProvider bannerPatternInventoryProvider;

    @Inject
    public BannerColorInventoryListener(
            final @NonNull BannerPatternInventoryProvider bannerPatternInventoryProvider
    ) {
        this.bannerPatternInventoryProvider = bannerPatternInventoryProvider;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().getTitle().equals(MessageUtils.getMessage("messages.inventories.banner.color_inventory_name"))
                || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        Inventory inventory = event.getClickedInventory();

        ItemStack oldBanner = inventory.getItem(5);
        Objects.requireNonNull(oldBanner);

        event.setCancelled(true);

        if (slot == 3) {
            DyeColor dyeColor = BannerUtils.getRandomDyeColor();
            player.openInventory(this.bannerPatternInventoryProvider.generate(oldBanner, dyeColor));
        }

        if (slot == 5) {
            player.getInventory().addItem(ItemUtils.removeName(oldBanner));
            player.closeInventory();
        }

        if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
            DyeColor dyeColor = BannerUtils.getDyeColor(Objects.requireNonNull(event.getCurrentItem()).getType());
            player.openInventory(this.bannerPatternInventoryProvider.generate(oldBanner, dyeColor));
        }
    }
}
