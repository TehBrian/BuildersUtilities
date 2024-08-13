package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.configurate.AbstractConfig;
import org.bukkit.Material;
import org.bukkit.block.data.type.Light;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class SpecialConfig extends AbstractConfig<YamlConfigurateWrapper> {

	private final Logger logger;

	private final List<ItemStack> items = new ArrayList<>();

	@Inject
	public SpecialConfig(final @Named("dataFolder") Path dataFolder, final Logger logger) {
		super(new YamlConfigurateWrapper(dataFolder.resolve("special.yml")));
		this.logger = logger;
	}

	@Override
	public void load() throws ConfigurateException {
		this.wrapper().load();
		final CommentedConfigurationNode rootNode = this.wrapper().rootNode();
		final String fileName = this.wrapper().path().getFileName().toString();

		this.items.clear();

		final @Nullable List<String> itemNames = rootNode.node("items").getList(String.class);
		if (itemNames == null) {
			return;
		}

		for (final String dirtyItemName : itemNames) {
			final var itemName = dirtyItemName.strip().toUpperCase(Locale.ROOT);

			// special case for light levels.
			if (itemName.startsWith("LIGHT") && !itemName.equals("LIGHT")) {
				int level;
				try {
					level = Integer.parseInt(itemName.split("-")[1]);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					this.logger.warn("Invalid light data {}.", itemName);
					this.logger.warn("Skipping this item. Please check your {}", fileName);
					this.logger.warn("Printing stack trace:", e);
					continue;
				}

				if (level > 15) {
					level = 15;
				}
				if (level < 0) {
					level = 0;
				}

				// create a light item with that light level.
				final ItemStack item = new ItemStack(Material.LIGHT);
				final BlockDataMeta meta = (BlockDataMeta) item.getItemMeta();
				final Light data = (Light) Material.LIGHT.createBlockData();
				data.setLevel(level);
				meta.setBlockData(data);
				item.setItemMeta(meta);

				this.items.add(item);
				continue;
			}

			final Material itemMaterial;
			try {
				itemMaterial = Material.valueOf(itemName);
			} catch (final IllegalArgumentException e) {
				this.logger.warn("The material {} does not exist.", itemName);
				this.logger.warn("Skipping this item. Please check your {}", fileName);
				this.logger.warn("Printing stack trace:", e);
				continue;
			}

			this.items.add(new ItemStack(itemMaterial));
		}
	}

	public List<ItemStack> items() {
		return this.items;
	}

}
