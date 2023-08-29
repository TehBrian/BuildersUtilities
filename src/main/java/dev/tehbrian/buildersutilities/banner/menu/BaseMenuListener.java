package dev.tehbrian.buildersutilities.banner.menu;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.Buttons;
import dev.tehbrian.buildersutilities.banner.PlayerSessions;
import dev.tehbrian.buildersutilities.banner.Session;
import dev.tehbrian.buildersutilities.banner.Util;
import dev.tehbrian.buildersutilities.config.LangConfig;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.spongepowered.configurate.NodePath;

import java.util.Objects;

public final class BaseMenuListener implements Listener {

  private final LangConfig langConfig;
  private final PlayerSessions playerSessions;

  @Inject
  public BaseMenuListener(
      final LangConfig langConfig,
      final PlayerSessions playerSessions
  ) {
    this.langConfig = langConfig;
    this.playerSessions = playerSessions;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(final InventoryClickEvent event) {
    final var title = this.langConfig.c(NodePath.path("menus", "banner", "base-inventory-name"));
    if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
        || !event.getView().title().equals(title)
        || !(event.getWhoClicked() instanceof Player player)) {
      return;
    }

    final int slot = event.getRawSlot();
    final Session session = this.playerSessions.get(player);

    event.setCancelled(true);

    if (slot == Buttons.RANDOM_SLOT) {
      session.baseColor(Util.randomDyeColor());
      session.showInterface(player);
    }

    if (slot == Buttons.BANNER_SLOT) {
      player.getInventory().addItem(session.generateBanner());
    }

    if (slot == Buttons.RESET_SLOT) {
      this.playerSessions.wipe(player);
      this.playerSessions.get(player).showInterface(player);
    }

    if (slot >= 28 && slot <= 44 && (slot % 9) > 0) {
      final DyeColor clickedColor = Util.colorFromItem(Objects.requireNonNull(event.getCurrentItem()).getType());
      session.baseColor(clickedColor);
      session.showInterface(player);
    }
  }

}
