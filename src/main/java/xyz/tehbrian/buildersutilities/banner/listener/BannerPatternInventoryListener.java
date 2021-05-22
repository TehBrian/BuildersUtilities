package xyz.tehbrian.buildersutilities.banner.listener;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.Objects;

public final class BannerPatternInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().getTitle().equals(MessageUtils.getMessage("messages.inventories.banner.pattern_inventory_name"))
                || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        Inventory inventory = event.getClickedInventory();

        ItemStack oldBanner = inventory.getItem(5);
        Objects.requireNonNull(oldBanner);

        ItemMeta itemMeta = oldBanner.getItemMeta();

        if (!(itemMeta instanceof BannerMeta)) {
            return;
        }
        BannerMeta bannerMeta = (BannerMeta) itemMeta;

        event.setCancelled(true);

        if (slot == 3) {
            DyeColor dyeColor = BannerUtils.getPattern(Objects.requireNonNull(inventory.getItem(9))).getColor();
            BannerUtils.addPattern(oldBanner, new Pattern(dyeColor, BannerUtils.getRandomPatternType()));

            if (bannerMeta.numberOfPatterns() > 16) {
                player.getInventory().addItem(ItemUtils.removeName(oldBanner));
                player.closeInventory();
            } else {
                player.openInventory(BannerColorInventoryProvider.generate(oldBanner));
            }
        }

        if (slot == 5) {
            player.getInventory().addItem(ItemUtils.removeName(oldBanner));
            player.closeInventory();
        }

        if (slot >= 9 && slot <= (8 + BannerUtils.getAllPatternTypes().size())) {
            BannerUtils.addPattern(oldBanner, BannerUtils.getPattern(Objects.requireNonNull(event.getCurrentItem())));

            if (bannerMeta.numberOfPatterns() > 16) {
                player.getInventory().addItem(ItemUtils.removeName(oldBanner));
                player.closeInventory();
            } else {
                player.openInventory(BannerColorInventoryProvider.generate(oldBanner));
            }
        }
    }
}
