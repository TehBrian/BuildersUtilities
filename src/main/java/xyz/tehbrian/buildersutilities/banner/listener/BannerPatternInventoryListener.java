package xyz.tehbrian.buildersutilities.banner.listener;

import com.google.inject.Inject;
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
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.BannerUtils;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

import java.util.Objects;

public final class BannerPatternInventoryListener implements Listener {

    private final BannerColorInventoryProvider bannerColorInventoryProvider;
    private final Lang lang;

    @Inject
    public BannerPatternInventoryListener(
            final @NonNull BannerColorInventoryProvider bannerColorInventoryProvider,
            final @NonNull Lang lang
    ) {
        this.bannerColorInventoryProvider = bannerColorInventoryProvider;
        this.lang = lang;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().title().equals(this.lang.c("messages.inventories.banner.pattern_inventory_name"))
                || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        final int slot = event.getRawSlot();

        final Inventory inventory = event.getClickedInventory();

        final ItemStack oldBanner = inventory.getItem(5);
        Objects.requireNonNull(oldBanner);

        final ItemMeta itemMeta = oldBanner.getItemMeta();

        if (!(itemMeta instanceof BannerMeta bannerMeta)) {
            return;
        }

        event.setCancelled(true);

        if (slot == 3) {
            final DyeColor dyeColor = BannerUtils.getPattern(Objects.requireNonNull(inventory.getItem(9))).getColor();
            BannerUtils.addPattern(oldBanner, new Pattern(dyeColor, BannerUtils.getRandomPatternType()));

            if (bannerMeta.numberOfPatterns() > 16) {
                player.getInventory().addItem(ItemUtils.removeName(oldBanner));
                player.closeInventory();
            } else {
                player.openInventory(this.bannerColorInventoryProvider.generate(oldBanner));
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
                player.openInventory(this.bannerColorInventoryProvider.generate(oldBanner));
            }
        }
    }

}
