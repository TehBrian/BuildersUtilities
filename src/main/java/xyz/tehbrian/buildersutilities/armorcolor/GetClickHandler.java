package xyz.tehbrian.buildersutilities.armorcolor;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.core.click.ClickContext;
import org.incendo.interfaces.core.click.ClickHandler;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.pane.ChestPane;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

import java.util.Objects;

class GetClickHandler implements
        ClickHandler<ChestPane, InventoryClickEvent, PlayerViewer, ClickContext<ChestPane, InventoryClickEvent, PlayerViewer>> {

    @Override
    public void accept(final ClickContext<ChestPane, InventoryClickEvent, PlayerViewer> context) {
        final @NonNull ItemStack item = Objects.requireNonNull(context
                .click()
                .cause()
                .getCurrentItem())
                .clone();

        context.viewer().player().getInventory().addItem(ItemUtils.removeName(item));
        context.view().update();
    }

}
