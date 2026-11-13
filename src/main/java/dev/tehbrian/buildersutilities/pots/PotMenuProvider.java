package dev.tehbrian.buildersutilities.pots;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.util.ChestSize;
import dev.tehbrian.buildersutilities.util.MenuItems;
import io.papermc.paper.datacomponent.item.PotDecorations;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.spongepowered.configurate.NodePath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

import static dev.tehbrian.agna.paper.FluentItem.createItem;
import static io.papermc.paper.datacomponent.DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE;
import static io.papermc.paper.datacomponent.DataComponentTypes.POT_DECORATIONS;

public final class PotMenuProvider {

	public static final int RANDOM_SLOT = 3;
	public static final int POT_SLOT = 5;
	public static final int RESET_SLOT = 8;
	public static final List<Integer> SIDE_SLOTS = List.of(10, 12, 14, 16);
	public static final int FIRST_SHERD_SLOT = 18;

	private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
	private static final List<String> SIDE_KEYS = List.of("back", "left", "right", "front");

	private final LangConfig langConfig;
	private final ConfigConfig configConfig;

	@Inject
	public PotMenuProvider(
			final LangConfig langConfig,
			final ConfigConfig configConfig
	) {
		this.langConfig = langConfig;
		this.configConfig = configConfig;
	}

	public static List<ItemType> faceTypes() {
		final List<ItemType> types = new ArrayList<>();
		types.add(ItemType.BRICK);
		Tag.ITEMS_DECORATED_POT_SHERDS.getValues().stream()
				.sorted(Comparator.comparing(Material::getKey))
				.map(Material::asItemType)
				.filter(Objects::nonNull)
				.forEach(types::add);

		return types;
	}

	public Inventory generate() {
		Component title = this.langConfig.c(NodePath.path("menus", "pots", "inventory-name"));
		final Inventory inv = Bukkit.createInventory(null, ChestSize.DOUBLE, title);

		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, MenuItems.BACKGROUND);
		}

		inv.setItem(
				RANDOM_SLOT,
				createItem(ItemType.PLAYER_HEAD)
						.itemName(this.langConfig.c(NodePath.path("menus", "pots", "randomize")))
						.textures(this.configConfig.data().heads().banner().randomize())
						.item()
		);
		inv.setItem(
				RESET_SLOT,
				createItem(ItemType.BARRIER)
						.itemName(this.langConfig.c(NodePath.path("menus", "pots", "reset")))
						.item()
		);

		final List<ItemType> faceTypes = faceTypes();
		final int sherdSlots = inv.getSize() - FIRST_SHERD_SLOT;
		for (int i = 0; i < Math.min(faceTypes.size(), sherdSlots); i++) {
			inv.setItem(
					FIRST_SHERD_SLOT + i,
					createItem(faceTypes.get(i))
							.lore(this.langConfig.cl(NodePath.path("menus", "pots", "select")))
							.item()
			);
		}

		this.render(inv, List.of(ItemType.BRICK, ItemType.BRICK, ItemType.BRICK, ItemType.BRICK), 0);

		return inv;
	}

	public void render(final Inventory inv, final List<ItemType> sides, final int selectedSide) {
		for (int i = 0; i < SIDE_SLOTS.size(); i++) {
			inv.setItem(
					SIDE_SLOTS.get(i),
					createItem(sides.get(i))
							.itemName(this.langConfig.c(NodePath.path("menus", "pots", SIDE_KEYS.get(i))))
							.lore(this.langConfig.cl(NodePath.path("menus", "pots", "select-side")))
							.set(ENCHANTMENT_GLINT_OVERRIDE, i == selectedSide)
							.item()
			);
		}

		inv.setItem(
				POT_SLOT,
				createItem(ItemType.DECORATED_POT)
						.set(POT_DECORATIONS, decorations(sides))
						.itemName(this.langConfig.c(NodePath.path("menus", "pots", "get-pot")))
						.item()
		);
	}

	public List<ItemType> sides(final Inventory inv) {
		List<ItemType> sides = new ArrayList<>();
		for (int slot : SIDE_SLOTS) {
			ItemStack item = inv.getItem(slot);
			if (item == null) continue;
			ItemType itemType = item.getType().asItemType();
			if (itemType != null) sides.add(itemType);
		}
		return sides;
	}

	public int selectedSide(final Inventory inv) {
		for (int i = 0; i < SIDE_SLOTS.size(); i++) {
			final ItemStack item = Objects.requireNonNull(inv.getItem(SIDE_SLOTS.get(i)));
			if (Boolean.TRUE.equals(item.getData(ENCHANTMENT_GLINT_OVERRIDE))) {
				return i;
			}
		}
		return 0;
	}

	public static List<ItemType> randomSides() {
		final List<ItemType> faceTypes = faceTypes();
		final List<ItemType> sherds = faceTypes.subList(1, faceTypes.size());
		return SIDE_SLOTS.stream()
				.map(slot -> sherds.get(RANDOM.nextInt(sherds.size())))
				.toList();
	}

	public static ItemStack createPot(final List<ItemType> sides) {
		return createItem(ItemType.DECORATED_POT)
				.set(POT_DECORATIONS, decorations(sides))
				.item();
	}

	/**
	 * Brick is passed as null because that is how vanilla stores an undecorated side,
	 * so plain sides stack with crafted pots
	 */
	private static PotDecorations decorations(final List<ItemType> sides) {
		return PotDecorations.potDecorations(
				orNull(sides.get(0)),
				orNull(sides.get(1)),
				orNull(sides.get(2)),
				orNull(sides.get(3))
		);
	}

	private static @Nullable ItemType orNull(final ItemType type) {
		return type.equals(ItemType.BRICK) ? null : type;
	}

}
