package xyz.tehbrian.buildersutilities.listeners.inventories;

import com.google.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.inventories.OptionsInventoryProvider;
import xyz.tehbrian.buildersutilities.user.UserManager;
import xyz.tehbrian.buildersutilities.util.MessageUtils;
import xyz.tehbrian.buildersutilities.util.Permissions;

import java.util.Objects;

@SuppressWarnings("unused")
public final class OptionsInventoryListener implements Listener {

    private final UserManager userManager;

    @Inject
    public OptionsInventoryListener(
            final @NonNull UserManager userManager
    ) {
        this.userManager = userManager;
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
                if (player.hasPermission(Permissions.IRON_DOOR_TOGGLE)) {
                    this.userManager.getUser(player).toggleIronDoorToggleEnabled();
                }
                break;
            case 2:
            case 11:
            case 20:
                if (player.hasPermission(Permissions.DOUBLE_SLAB_BREAK)) {
                    this.userManager.getUser(player).toggleDoubleSlabBreakEnabled();
                }
                break;
            case 3:
            case 12:
            case 21:
                if (player.hasPermission(Permissions.GLAZED_TERRACOTTA_ROTATE)) {
                    this.userManager.getUser(player).toggleGlazedTerracottaRotateEnabled();
                }
                break;
            case 5:
            case 14:
            case 23:
                if (player.hasPermission(Permissions.NIGHT_VISION)) {
                    this.userManager.getUser(player).toggleNightVisionEnabled();
                }
                break;
            case 6:
            case 15:
            case 24:
                if (player.hasPermission(Permissions.NIGHT_VISION)) {
                    this.userManager.getUser(player).toggleNoClipEnabled();
                }
                break;
            case 7:
            case 16:
            case 25:
                if (player.hasPermission(Permissions.ADVANCED_FLY)) {
                    this.userManager.getUser(player).toggleAdvancedFlyEnabled();
                }
                break;
            default:
                return;
        }

        OptionsInventoryProvider.update(event.getView().getTopInventory(), this.userManager.getUser(player));
    }
}
