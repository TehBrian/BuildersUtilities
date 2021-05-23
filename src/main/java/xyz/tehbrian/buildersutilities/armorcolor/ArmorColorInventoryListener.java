package xyz.tehbrian.buildersutilities.armorcolor;

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
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("unused")
public final class ArmorColorInventoryListener implements Listener {

    private final ArmorColorInventoryProvider armorColorInventoryProvider;
    private final Lang lang;

    @Inject
    public ArmorColorInventoryListener(
            final @NonNull ArmorColorInventoryProvider armorColorInventoryProvider,
            final @NonNull Lang lang
    ) {
        this.armorColorInventoryProvider = armorColorInventoryProvider;
        this.lang = lang;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().title().equals(this.lang.c("messages.inventories.armor_color.inventory_name"))
                || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        Inventory inventory = event.getClickedInventory();

        ItemStack item = inventory.getItem(slot);
        Objects.requireNonNull(item);

        event.setCancelled(true);

        switch (slot) {
            case 10:
            case 19:
            case 28:
            case 37:
                player.getInventory().addItem(ItemUtils.removeName(Objects.requireNonNull(event.getCurrentItem()).clone()));
                break;
            case 31:
            case 33:
            case 32:
                if (event.getClick() == ClickType.LEFT && item.getAmount() < 33) {
                    item.setAmount(item.getAmount() + 1);
                } else if (event.getClick() == ClickType.RIGHT && item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else if (event.getClick() == ClickType.SHIFT_LEFT && item.getAmount() < 30) {
                    item.setAmount(item.getAmount() + 4);
                } else if (event.getClick() == ClickType.SHIFT_LEFT) {
                    item.setAmount(33);
                } else if (event.getClick() == ClickType.SHIFT_RIGHT && item.getAmount() > 4) {
                    item.setAmount(item.getAmount() - 4);
                } else if (event.getClick() == ClickType.SHIFT_RIGHT) {
                    item.setAmount(1);
                }
                break;
            case 22:
            case 23:
            case 24:
                Objects.requireNonNull(inventory.getItem(slot + 9)).setAmount(new Random().nextInt(33) + 1);
                break;
            default:
                return;
        }

        inventory.setItem(slot, item);

        this.armorColorInventoryProvider.update(event.getClickedInventory());
    }
}
