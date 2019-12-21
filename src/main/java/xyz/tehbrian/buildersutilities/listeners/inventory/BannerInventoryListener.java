package xyz.tehbrian.buildersutilities.listeners.inventory;

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
import xyz.tehbrian.buildersutilities.inventories.BannerInventory;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.Objects;

@SuppressWarnings("unused")
public class BannerInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBaseInventoryClick(InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())) return;

        if (!event.getView().getTitle().equals(MessageUtils.getMessage("msg.banner.base_inventory_name"))) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        event.setCancelled(true);

        if (slot == 12) {
            DyeColor dyeColor = BannerUtils.getRandomDyeColor();
            ItemStack newBanner = ItemUtils.create(BannerUtils.getBanner(dyeColor), 1, MessageUtils.getMessage("msg.banner.get_banner"));
            player.openInventory(BannerInventory.generateColorInventory(newBanner));
        }

        if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
            DyeColor dyeColor = BannerUtils.getDyeColor(Objects.requireNonNull(event.getCurrentItem()).getType());
            ItemStack newBanner = ItemUtils.create(BannerUtils.getBanner(dyeColor), 1, MessageUtils.getMessage("msg.banner.get_banner"));
            player.openInventory(BannerInventory.generateColorInventory(newBanner));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onColorInventoryClick(InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())) return;

        if (!event.getView().getTitle().equals(MessageUtils.getMessage("msg.banner.color_inventory_name"))) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        Inventory inventory = event.getClickedInventory();

        ItemStack oldBanner = inventory.getItem(14);
        Objects.requireNonNull(oldBanner);

        event.setCancelled(true);

        if (slot == 12) {
            DyeColor dyeColor = BannerUtils.getRandomDyeColor();
            player.openInventory(BannerInventory.generatePatternInventory(oldBanner, dyeColor));
        }

        if (slot == 14) {
            player.getInventory().addItem(ItemUtils.removeName(oldBanner));
            player.closeInventory();
        }

        if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
            DyeColor dyeColor = BannerUtils.getDyeColor(Objects.requireNonNull(event.getCurrentItem()).getType());
            player.openInventory(BannerInventory.generatePatternInventory(oldBanner, dyeColor));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPatternInventoryClick(InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())) return;

        if (!event.getView().getTitle().equals(MessageUtils.getMessage("msg.banner.pattern_inventory_name"))) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        Inventory inventory = event.getClickedInventory();

        ItemStack oldBanner = inventory.getItem(5);
        Objects.requireNonNull(oldBanner);

        ItemMeta itemMeta = oldBanner.getItemMeta();

        if (!(itemMeta instanceof BannerMeta)) return;
        BannerMeta bannerMeta = (BannerMeta) itemMeta;

        event.setCancelled(true);

        if (slot == 3) {
            DyeColor dyeColor = BannerUtils.getPattern(Objects.requireNonNull(inventory.getItem(9))).getColor();
            BannerUtils.addPattern(oldBanner, new Pattern(dyeColor, BannerUtils.getRandomPatternType()));

            if (bannerMeta.numberOfPatterns() > 16) {
                player.getInventory().addItem(ItemUtils.removeName(oldBanner));
                player.closeInventory();
            } else {
                player.openInventory(BannerInventory.generateColorInventory(oldBanner));
            }
        }

        if (slot == 5) {
            player.getInventory().addItem(ItemUtils.removeName(oldBanner));
            player.closeInventory();
        }

        if (slot >= 9 && slot <= 47) {
            BannerUtils.addPattern(oldBanner, BannerUtils.getPattern(Objects.requireNonNull(event.getCurrentItem())));

            if (bannerMeta.numberOfPatterns() > 16) {
                player.getInventory().addItem(ItemUtils.removeName(oldBanner));
                player.closeInventory();
            } else {
                player.openInventory(BannerInventory.generateColorInventory(oldBanner));
            }
        }
    }
}

