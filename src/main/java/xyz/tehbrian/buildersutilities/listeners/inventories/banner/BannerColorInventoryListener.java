package xyz.tehbrian.buildersutilities.listeners.inventories.banner;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.inventories.banner.BannerPatternInventoryProvider;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.Objects;

public final class BannerColorInventoryListener implements Listener {

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
            player.openInventory(BannerPatternInventoryProvider.generate(oldBanner, dyeColor));
        }

        if (slot == 5) {
            player.getInventory().addItem(ItemUtils.removeName(oldBanner));
            player.closeInventory();
        }

        if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
            DyeColor dyeColor = BannerUtils.getDyeColor(Objects.requireNonNull(event.getCurrentItem()).getType());
            player.openInventory(BannerPatternInventoryProvider.generate(oldBanner, dyeColor));
        }
    }
}
