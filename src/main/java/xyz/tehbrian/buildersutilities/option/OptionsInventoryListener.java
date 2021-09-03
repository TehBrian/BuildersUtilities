package xyz.tehbrian.buildersutilities.option;

import com.google.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.UserService;

import java.util.Objects;

@SuppressWarnings("unused")
public final class OptionsInventoryListener implements Listener {

    private final UserService userService;
    private final OptionsInventoryProvider optionsInventoryProvider;
    private final LangConfig langConfig;

    @Inject
    public OptionsInventoryListener(
            final @NonNull UserService userService,
            final @NonNull OptionsInventoryProvider optionsInventoryProvider,
            final @NonNull LangConfig langConfig
    ) {
        this.userService = userService;
        this.optionsInventoryProvider = optionsInventoryProvider;
        this.langConfig = langConfig;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
                || !event.getView().title().equals(this.langConfig.c(NodePath.path("inventories", "options", "inventory-name")))
                || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        final int slot = event.getRawSlot();

        event.setCancelled(true);

        switch (slot) {
            case 1:
            case 10:
            case 19:
                if (player.hasPermission(Constants.Permissions.IRON_DOOR_TOGGLE)) {
                    this.userService.getUser(player).toggleIronDoorToggleEnabled();
                }
                break;
            case 2:
            case 11:
            case 20:
                if (player.hasPermission(Constants.Permissions.DOUBLE_SLAB_BREAK)) {
                    this.userService.getUser(player).toggleDoubleSlabBreakEnabled();
                }
                break;
            case 3:
            case 12:
            case 21:
                if (player.hasPermission(Constants.Permissions.GLAZED_TERRACOTTA_ROTATE)) {
                    this.userService.getUser(player).toggleGlazedTerracottaRotateEnabled();
                }
                break;
            case 5:
            case 14:
            case 23:
                if (player.hasPermission(Constants.Permissions.NIGHT_VISION)) {
                    this.userService.getUser(player).toggleNightVisionEnabled();
                }
                break;
            case 6:
            case 15:
            case 24:
                if (player.hasPermission(Constants.Permissions.NIGHT_VISION)) {
                    this.userService.getUser(player).toggleNoClipEnabled();
                }
                break;
            case 7:
            case 16:
            case 25:
                if (player.hasPermission(Constants.Permissions.ADVANCED_FLY)) {
                    this.userService.getUser(player).toggleAdvancedFlyEnabled();
                }
                break;
            default:
                return;
        }

        this.optionsInventoryProvider.update(event.getView().getTopInventory(), this.userService.getUser(player));
    }

}
