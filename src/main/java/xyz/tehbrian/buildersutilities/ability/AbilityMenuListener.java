package xyz.tehbrian.buildersutilities.ability;

import com.google.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.User;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.buildersutilities.util.Permissions;

import java.util.Objects;

public final class AbilityMenuListener implements Listener {

  private final UserService userService;
  private final AbilityMenuProvider abilityMenuProvider;
  private final LangConfig langConfig;

  @Inject
  public AbilityMenuListener(
      final UserService userService,
      final AbilityMenuProvider abilityMenuProvider,
      final LangConfig langConfig
  ) {
    this.userService = userService;
    this.abilityMenuProvider = abilityMenuProvider;
    this.langConfig = langConfig;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(final InventoryClickEvent event) {
    if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
        || !event.getView().title().equals(this.langConfig.c(NodePath.path("menus", "ability", "inventory-name")))
        || !(event.getWhoClicked() instanceof Player player)) {
      return;
    }

    final int slot = event.getRawSlot();

    event.setCancelled(true);

    final User user = this.userService.getUser(player);
    switch (slot) {
      case 1, 10, 19 -> {
        if (player.hasPermission(Permissions.IRON_DOOR_TOGGLE)) {
          user.toggleIronDoorToggleEnabled();
        }
      }
      case 2, 11, 20 -> {
        if (player.hasPermission(Permissions.DOUBLE_SLAB_BREAK)) {
          user.toggleDoubleSlabBreakEnabled();
        }
      }
      case 3, 12, 21 -> {
        if (player.hasPermission(Permissions.GLAZED_TERRACOTTA_ROTATE)) {
          user.toggleGlazedTerracottaRotateEnabled();
        }
      }
      case 5, 14, 23 -> {
        if (player.hasPermission(Permissions.NIGHT_VISION)) {
          user.toggleNightVisionEnabled();
        }
      }
      case 6, 15, 24 -> {
        if (player.hasPermission(Permissions.NIGHT_VISION)) {
          user.toggleNoclipEnabled();
        }
      }
      case 7, 16, 25 -> {
        if (player.hasPermission(Permissions.ADVANCED_FLY)) {
          user.toggleAdvancedFlyEnabled();
        }
      }
      default -> {
      }
    }

    this.abilityMenuProvider.update(event.getView().getTopInventory(), user);
  }

}
