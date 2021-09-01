package xyz.tehbrian.buildersutilities.banner.listener;

import broccolai.corn.paper.item.special.BannerBuilder;
import com.google.inject.Inject;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.util.BannerUtils;

import java.util.Objects;

public final class BannerPatternInventoryListener implements Listener {

    private final BannerColorInventoryProvider bannerColorInventoryProvider;
    private final LangConfig lang;

    @Inject
    public BannerPatternInventoryListener(
            final @NonNull BannerColorInventoryProvider bannerColorInventoryProvider,
            final @NonNull LangConfig lang
    ) {
        this.bannerColorInventoryProvider = bannerColorInventoryProvider;
        this.lang = lang;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().title().equals(this.lang.c(NodePath.path("inventories", "banner", "pattern_inventory_name")))
                || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        final int slot = event.getRawSlot();

        final Inventory inventory = event.getClickedInventory();

        final BannerBuilder oldBannerBuilder = BannerBuilder.of(Objects.requireNonNull(inventory.getItem(5)));

        event.setCancelled(true);

        // The get banner button.
        if (slot == 5) {
            player.getInventory().addItem(oldBannerBuilder.name(null).build());
            player.closeInventory();
            return;
        }

        // The random button.
        if (slot == 3) {
            final DyeColor currentColor = BannerBuilder.of(Objects.requireNonNull(inventory.getItem(9))).getPattern(0).getColor();

            final var newBannerBuilder = oldBannerBuilder
                    .addPattern(new Pattern(currentColor, BannerUtils.randomPatternType()));

            if (newBannerBuilder.patterns().size() >= 16) {
                player.getInventory().addItem(newBannerBuilder.name(null).build());
                player.closeInventory();
            } else {
                player.openInventory(this.bannerColorInventoryProvider.generate(newBannerBuilder.build()));
            }
        }

        // The various banner buttons.
        if (slot >= 9 && slot <= (8 + BannerUtils.patternTypes().size())) {
            final var newBannerBuilder = oldBannerBuilder
                    .addPattern(BannerBuilder.of(Objects.requireNonNull(event.getCurrentItem())).getPattern(0));

            if (newBannerBuilder.patterns().size() >= 16) {
                player.getInventory().addItem(newBannerBuilder.name(null).build());
                player.closeInventory();
            } else {
                player.openInventory(this.bannerColorInventoryProvider.generate(newBannerBuilder.build()));
            }
        }
    }

}
