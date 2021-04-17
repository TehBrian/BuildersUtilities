package xyz.tehbrian.buildersutilities.listeners.inventories.banner;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.tehbrian.buildersutilities.inventories.banner.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.Objects;

public final class BannerBaseInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().getTitle().equals(MessageUtils.getMessage("messages.inventories.banner.base_inventory_name"))
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
                    MessageUtils.getMessage("messages.inventories.banner.get_banner"));
            player.openInventory(BannerColorInventoryProvider.generate(newBanner));
        }

        if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
            DyeColor dyeColor = BannerUtils.getDyeColor(Objects.requireNonNull(event.getCurrentItem()).getType());
            ItemStack newBanner = ItemUtils.create(BannerUtils.getBanner(dyeColor),
                    1,
                    MessageUtils.getMessage("messages.inventories.banner.get_banner"));
            player.openInventory(BannerColorInventoryProvider.generate(newBanner));
        }
    }
}
