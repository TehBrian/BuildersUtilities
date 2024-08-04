package dev.tehbrian.buildersutilities.banner.menu;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.Buttons;
import dev.tehbrian.buildersutilities.banner.PlayerSessions;
import dev.tehbrian.buildersutilities.banner.Sayge;
import dev.tehbrian.buildersutilities.banner.Session;
import dev.tehbrian.buildersutilities.config.LangConfig;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.spongepowered.configurate.NodePath;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static love.broccolai.corn.minecraft.item.special.BannerBuilder.bannerBuilder;

public final class PatternMenuListener implements Listener {

  private final LangConfig langConfig;
  private final PlayerSessions playerSessions;

  @Inject
  public PatternMenuListener(
      final LangConfig langConfig,
      final PlayerSessions playerSessions
  ) {
    this.langConfig = langConfig;
    this.playerSessions = playerSessions;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(final InventoryClickEvent event) {
    final var title = this.langConfig.c(NodePath.path("menus", "banner", "pattern-inventory-name"));
    if (!Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())
        || !event.getView().title().equals(title)
        || !(event.getWhoClicked() instanceof final Player player)) {
      return;
    }

    final int slot = event.getRawSlot();
    final Session session = this.playerSessions.get(player);

    event.setCancelled(true);

    if (slot == Buttons.UNDO_SLOT) {
      session.nextPatternColor(null);
      session.showInterface(player);
    }

    if (slot == Buttons.RANDOM_SLOT) {
      session.patterns().add(new Pattern(
          requireNonNull(session.nextPatternColor()),
          Sayge.randomPatternType()
      ));
      session.nextPatternColor(null);
      session.showInterface(player);
    }

    if (slot == Buttons.BANNER_SLOT) {
      player.getInventory().addItem(session.generateBanner());
    }

    if (slot == Buttons.RESET_SLOT) {
      this.playerSessions.wipe(player);
      this.playerSessions.get(player).showInterface(player);
    }

    if (slot >= 9 && slot <= (8 + Sayge.patternTypes().size())) {
      // get the pattern from the clicked banner that PatternMenuProvider has already assigned the next pattern color to.
      final Pattern clickedPattern = bannerBuilder(requireNonNull(event.getCurrentItem())).pattern(0);
      session.patterns().add(clickedPattern);
      session.nextPatternColor(null);
      session.showInterface(player);
    }
  }

}
