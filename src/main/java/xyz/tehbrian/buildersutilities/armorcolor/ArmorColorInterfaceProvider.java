package xyz.tehbrian.buildersutilities.armorcolor;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.core.click.ClickHandler;
import org.incendo.interfaces.core.transform.InterfaceProperty;
import org.incendo.interfaces.core.util.Vector2;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.transform.PaperTransform;
import org.incendo.interfaces.paper.type.ChestInterface;
import xyz.tehbrian.buildersutilities.config.Lang;
import xyz.tehbrian.buildersutilities.util.ConfigUtils;
import xyz.tehbrian.buildersutilities.util.Constants;
import xyz.tehbrian.buildersutilities.util.ItemUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public final class ArmorColorInterfaceProvider {

    private final Lang lang;

    @Inject
    public ArmorColorInterfaceProvider(
            final @NonNull Lang lang
    ) {
        this.lang = lang;
    }

    public ChestInterface generate() {
        final List<Component> lore = this.lang.cl("messages.inventories.armor_color.change");

        final GetClickHandler getClickHandler = new GetClickHandler();

        final Map<Vector2, ItemStackElement<ChestPane>> armorElements = Map.of(
                Vector2.at(1, 1),
                ItemStackElement.of(ItemUtils.create(
                        Material.LEATHER_HELMET,
                        1,
                        this.lang.c("messages.inventories.armor_color.get_helmet")
                ), getClickHandler),
                Vector2.at(1, 2),
                ItemStackElement.of(ItemUtils.create(
                        Material.LEATHER_CHESTPLATE,
                        1,
                        this.lang.c("messages.inventories.armor_color.get_chestplate")
                ), getClickHandler),
                Vector2.at(1, 3),
                ItemStackElement.of(ItemUtils.create(
                        Material.LEATHER_LEGGINGS,
                        1,
                        this.lang.c("messages.inventories.armor_color.get_leggings")
                ), getClickHandler),
                Vector2.at(1, 4),
                ItemStackElement.of(ItemUtils.create(
                        Material.LEATHER_BOOTS,
                        1,
                        this.lang.c("messages.inventories.armor_color.get_boots")
                ), getClickHandler)
        );

        final ColoredArmorTransform coloredArmorTransform = new ColoredArmorTransform(armorElements);

        return ChestInterface.builder()
                .rows(6)
                .updates(false, 1)
                .title(this.lang.c("messages.inventories.armor_color.inventory_name"))
                .clickHandler(ClickHandler.cancel())
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(Constants.Items.INTERFACE_BACKGROUND)))
                .addReactiveTransform(coloredArmorTransform)
                .addTransform(PaperTransform.chestItem(ItemStackElement.of(
                        ItemUtils.createHead(
                                ConfigUtils.getString("heads.armor_color.randomize_red"),
                                1,
                                this.lang.c("messages.inventories.armor_color.randomize_red")
                        ), (context) -> {
                            final InterfaceProperty<Color> color = coloredArmorTransform.property();
                            color.set(color.get().setRed(new Random().nextInt(33) + 1));
                        }
                ), 4, 2))
                .addTransform(PaperTransform.chestItem(ItemStackElement.of(
                        ItemUtils.createHead(
                                ConfigUtils.getString("heads.armor_color.randomize_green"),
                                1,
                                this.lang.c("messages.inventories.armor_color.randomize_green")
                        ), (context) -> {

                        }
                ), 5, 2))
                .addTransform(PaperTransform.chestItem(ItemStackElement.of(
                        ItemUtils.createHead(
                                ConfigUtils.getString("heads.armor_color.randomize_blue"),
                                1,
                                this.lang.c("messages.inventories.armor_color.randomize_blue")
                        ), (context) -> {

                        }
                ), 6, 2))
                .addTransform(PaperTransform.chestItem(ItemStackElement.of(
                        ItemUtils.createHead(
                                ConfigUtils.getString("heads.armor_color.red"),
                                1,
                                this.lang.c("messages.inventories.armor_color.red")
                        ), (context) -> {

                        }
                ), 4, 3))
                .addTransform(PaperTransform.chestItem(ItemStackElement.of(
                        ItemUtils.createHead(
                                ConfigUtils.getString("heads.armor_color.green"),
                                1,
                                this.lang.c("messages.inventories.armor_color.green")
                        ), (context) -> {

                        }
                ), 5, 3))
                .addTransform(PaperTransform.chestItem(ItemStackElement.of(
                        ItemUtils.createHead(
                                ConfigUtils.getString("heads.armor_color.blue"),
                                1,
                                this.lang.c("messages.inventories.armor_color.blue")
                        ), (context) -> {

                        }
                ), 6, 3))
                .build();
    }

    public void update(final Inventory inv) {
        int r = (Objects.requireNonNull(inv.getItem(31)).getAmount() - 1) * 8;
        int g = (Objects.requireNonNull(inv.getItem(32)).getAmount() - 1) * 8;
        int b = (Objects.requireNonNull(inv.getItem(33)).getAmount() - 1) * 8;

        if (r == 256) {
            r = 255;
        }

        if (g == 256) {
            g = 255;
        }

        if (b == 256) {
            b = 255;
        }

        inv.setItem(10, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(10)), r, g, b));
        inv.setItem(19, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(19)), r, g, b));
        inv.setItem(28, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(28)), r, g, b));
        inv.setItem(37, ItemUtils.colorLeatherArmor(Objects.requireNonNull(inv.getItem(37)), r, g, b));
    }

}

