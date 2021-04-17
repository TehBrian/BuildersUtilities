package xyz.tehbrian.buildersutilities.listeners.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.tehbrian.buildersutilities.BuildersUtilities;
import xyz.tehbrian.buildersutilities.inventories.OptionsInventoryProvider;
import xyz.tehbrian.buildersutilities.util.MessageUtils;

import java.util.Objects;

@SuppressWarnings("unused")
public final class OptionsInventoryListener implements Listener {

    private final BuildersUtilities main;

    public OptionsInventoryListener(final BuildersUtilities main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().getTitle().equals(MessageUtils.getMessage("messages.inventories.options.inventory_name"))
                || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();

        event.setCancelled(true);

        switch (slot) {
            case 1:
            case 10:
            case 19:
                this.main.getPlayerDataManager().getPlayerData(player).toggleIronTrapdoorToggleEnabled();
                break;
            case 2:
            case 11:
            case 20:
                this.main.getPlayerDataManager().getPlayerData(player).toggleDoubleSlabBreakEnabled();
                break;
            case 3:
            case 12:
            case 21:
                this.main.getPlayerDataManager().getPlayerData(player).toggleGlazedTerracottaRotateEnabled();
                break;
            case 5:
            case 14:
            case 23:
                if (player.hasPermission("buildersutilities.nightvision")) {
                    this.main.getPlayerDataManager().getPlayerData(player).toggleNightVisionEnabled();
                }
                break;
            case 6:
            case 15:
            case 24:
                if (player.hasPermission("buildersutilities.noclip")) {
                    this.main.getPlayerDataManager().getPlayerData(player).toggleNoClipEnabled();
                }
                break;
            case 7:
            case 16:
            case 25:
                if (player.hasPermission("buildersutilities.advancedfly")) {
                    this.main.getPlayerDataManager().getPlayerData(player).toggleAdvancedFlyEnabled();
                }
                break;
            default:
                return;
        }

        OptionsInventoryProvider.update(event.getView().getTopInventory(), player);
    }
}
