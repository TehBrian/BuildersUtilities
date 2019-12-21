package xyz.tehbrian.buildersutilities.listeners.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.tehbrian.buildersutilities.inventories.OptionsInventory;
import xyz.tehbrian.buildersutilities.managers.PlayerOptionsManager;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.Objects;

@SuppressWarnings("unused")
public class OptionsInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())) return;

        if (!event.getView().getTitle().equals(MessageUtils.getMessage("msg.options.inventory_name"))) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        event.setCancelled(true);

        switch (slot) {
            case 1:
            case 10:
            case 19:
                PlayerOptionsManager.toggleTrapdoorToggle(player);
                break;
            case 2:
            case 11:
            case 20:
                PlayerOptionsManager.toggleSlabBreak(player);
                break;
            case 3:
            case 12:
            case 21:
                PlayerOptionsManager.toggleTerracottaRotate(player);
                break;
            case 5:
            case 14:
            case 23:
                if (player.hasPermission("buildersutilities.nightvision")) {
                    PlayerOptionsManager.toggleNightVision(player);
                }
                break;
            case 6:
            case 15:
            case 24:
                if (player.hasPermission("buildersutilities.noclip")) {
                    PlayerOptionsManager.toggleNoClip(player);
                }
                break;
            case 7:
            case 16:
            case 25:
                if (player.hasPermission("buildersutilities.advancedfly")) {
                    PlayerOptionsManager.toggleAdvancedFly(player);
                }
                break;
            default:
                return;
        }

        OptionsInventory.update(event.getView().getTopInventory(), player);
    }
}


