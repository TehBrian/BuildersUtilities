package xyz.tehbrian.buildersutilities.option;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.NodePath;
import xyz.tehbrian.buildersutilities.Constants;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class OptionsInventoryProvider {

    private static final ItemStack GREEN = PaperItemBuilder.ofType(Material.LIME_STAINED_GLASS_PANE).name(LangConfig.EMPTY).build();
    private static final ItemStack ORANGE = PaperItemBuilder.ofType(Material.ORANGE_STAINED_GLASS_PANE).name(LangConfig.EMPTY).build();
    private static final ItemStack RED = PaperItemBuilder.ofType(Material.RED_STAINED_GLASS_PANE).name(LangConfig.EMPTY).build();

    private final LangConfig langConfig;

    @Inject
    public OptionsInventoryProvider(
            final @NonNull LangConfig langConfig
    ) {
        this.langConfig = langConfig;
    }

    public Inventory generate(final User user) {
        final Inventory inv = Bukkit.createInventory(
                null,
                27,
                this.langConfig.c(NodePath.path("inventories", "options", "inventory-name"))
        );

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Constants.Items.INTERFACE_BACKGROUND);
        }

        this.update(inv, user);

        return inv;
    }

    public void update(final Inventory inv, final User user) {
        final @Nullable Player p = user.getPlayer();
        Objects.requireNonNull(p);

        this.drawOption(
                inv,
                1,
                Material.IRON_TRAPDOOR,
                "iron-door-toggle",
                p.hasPermission(Constants.Permissions.IRON_DOOR_TOGGLE),
                user.ironDoorToggleEnabled()
        );

        this.drawOption(
                inv,
                2,
                Material.STONE_SLAB,
                "double-slab-break",
                p.hasPermission(Constants.Permissions.DOUBLE_SLAB_BREAK),
                user.doubleSlabBreakEnabled()
        );

        this.drawOption(
                inv,
                3,
                Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
                "glazed-terracotta-rotate",
                p.hasPermission(Constants.Permissions.GLAZED_TERRACOTTA_ROTATE),
                user.glazedTerracottaRotateEnabled()
        );

        this.drawOption(
                inv,
                5,
                Material.ENDER_EYE,
                "night-vision",
                p.hasPermission(Constants.Permissions.NIGHT_VISION),
                user.nightVisionEnabled()
        );

        this.drawOption(
                inv,
                6,
                Material.COMPASS,
                "no-clip",
                p.hasPermission(Constants.Permissions.NO_CLIP),
                user.noClipEnabled()
        );

        this.drawOption(
                inv,
                7,
                Material.FEATHER,
                "advanced-fly",
                p.hasPermission(Constants.Permissions.ADVANCED_FLY),
                user.advancedFlyEnabled()
        );
    }

    private ItemStack createOptionItem(final Material material, final String optionKey, final String statusKey) {
        final List<Component> lore = new ArrayList<>();
        lore.addAll(this.langConfig.cl(NodePath.path("inventories", "options", optionKey, "description")));
        lore.addAll(this.langConfig.cl(NodePath.path("inventories", "options", "status", statusKey)));

        return PaperItemBuilder.ofType(material)
                .name(this.langConfig.c(NodePath.path("inventories", "options", optionKey, "name")))
                .lore(lore)
                .build();
    }

    private void drawOption(
            final Inventory inv,
            final int row,
            final Material material,
            final String optionKey,
            final boolean hasPermission,
            final boolean isEnabled
    ) {
        if (!hasPermission) {
            inv.setItem(row, ORANGE);
            inv.setItem(row + 9, this.createOptionItem(material, optionKey, "no-permission"));
            inv.setItem(row + 18, ORANGE);
            return;
        }

        if (isEnabled) {
            inv.setItem(row, GREEN);
            inv.setItem(row + 9, this.createOptionItem(material, optionKey, "enabled"));
            inv.setItem(row + 18, GREEN);
        } else {
            inv.setItem(row, RED);
            inv.setItem(row + 9, this.createOptionItem(material, optionKey, "disabled"));
            inv.setItem(row + 18, RED);
        }
    }

}
