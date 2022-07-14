package xyz.tehbrian.buildersutilities.armorcolor;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.config.LangConfig;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("ClassCanBeRecord")
public final class ArmorColorMenuListener implements Listener {

    private final ArmorColorMenuProvider armorColorInventoryProvider;
    private final LangConfig langConfig;

    @Inject
    public ArmorColorMenuListener(
            final @NonNull ArmorColorMenuProvider armorColorInventoryProvider,
            final @NonNull LangConfig langConfig
    ) {
        this.armorColorInventoryProvider = armorColorInventoryProvider;
        this.langConfig = langConfig;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().title().equals(this.langConfig.c(NodePath.path("menus", "armor-color", "inventory-name")))
                || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        final int slot = event.getRawSlot();

        final Inventory inventory = event.getClickedInventory();
        Objects.requireNonNull(inventory);

        final ItemStack item = inventory.getItem(slot);
        Objects.requireNonNull(item);

        event.setCancelled(true);

        switch (slot) {
            case 10, 19, 28, 37 -> player.getInventory().addItem(PaperItemBuilder
                    .of(Objects.requireNonNull(event.getCurrentItem()).clone())
                    .name(null)
                    .lore(null)
                    .build());
            case 31, 33, 32 -> {
                final ClickType clickType = event.getClick();
                if (clickType == ClickType.LEFT && item.getAmount() < 33) {
                    item.setAmount(item.getAmount() + 1);
                } else if (clickType == ClickType.RIGHT && item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else if (clickType == ClickType.SHIFT_LEFT && item.getAmount() < 30) {
                    item.setAmount(item.getAmount() + 4);
                } else if (clickType == ClickType.SHIFT_LEFT) {
                    item.setAmount(33);
                } else if (clickType == ClickType.SHIFT_RIGHT && item.getAmount() > 4) {
                    item.setAmount(item.getAmount() - 4);
                } else if (clickType == ClickType.SHIFT_RIGHT) {
                    item.setAmount(1);
                }
            }
            case 22, 23, 24 -> Objects.requireNonNull(inventory.getItem(slot + 9)).setAmount(new Random().nextInt(33) + 1);
            default -> {
            }
        }

        inventory.setItem(slot, item);

        this.armorColorInventoryProvider.update(event.getClickedInventory());
    }

}
