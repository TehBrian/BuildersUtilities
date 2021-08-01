package xyz.tehbrian.buildersutilities.armorcolor;


import org.bukkit.Color;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.core.transform.InterfaceProperty;
import org.incendo.interfaces.core.transform.ReactiveTransform;
import org.incendo.interfaces.core.util.Vector2;
import org.incendo.interfaces.core.view.InterfaceView;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

import java.util.Map;

/**
 * A transform that shows elements on different "pages".
 */
public final class ColoredArmorTransform implements ReactiveTransform<ChestPane, PlayerViewer, Color> {

    private final InterfaceProperty<Color> colorProperty = InterfaceProperty.of(Color.fromRGB(0, 0, 0));

    private final Map<Vector2, ItemStackElement<ChestPane>> elements;

    public ColoredArmorTransform(final @NonNull Map<Vector2, ItemStackElement<ChestPane>> elements) {
        this.elements = elements;
    }

    @Override
    public @NonNull InterfaceProperty<Color> property() {
        return this.colorProperty;
    }

    @Override
    public ChestPane apply(final ChestPane originalPane, final InterfaceView<ChestPane, PlayerViewer> view) {
        // Pane that we're updating.
        ChestPane pane = originalPane;
        // Render the pane.
        for (final var entry : elements.entrySet()) {
            final ItemStackElement<ChestPane> element = entry.getValue();
            final Vector2 position = entry.getKey();

            pane = pane.element(
                    ItemStackElement.of(
                            ItemUtils.colorLeatherArmor(element.itemStack(), colorProperty.get()),
                            element.clickHandler()
                    ),
                    position.x(),
                    position.y()
            );
        }
        // Return the updated pane.
        return pane;
    }

}
