package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import dev.tehbrian.tehlib.configurate.AbstractConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
			final String configItem = dirtyItemName.strip();
			ItemStack item = this.itemFromString(configItem, fileName);
			if (item != null) {
				this.items.add(item);
			}
		}
	}

	public List<ItemStack> items() {
		return this.items;
	}

	/**
	 * This method return the ItemStack object based on the configuration following
	 * the syntax '%Material name%, %JSON values%'.
	 * @param configItem The item string
	 * @param fileName The name of the configuration file for logging purposes
	 * @return The ItemStack object or null if the item is invalid
	 */
	private @Nullable ItemStack itemFromString(String configItem, String fileName) {
		try {
			String[] itemData = configItem.strip().split(", ", 2);
			Material material = Material.valueOf(itemData[0].toUpperCase());
			ItemStack item = new ItemStack(material);

			if (itemData.length > 1 && !itemData[1].isBlank()) {
				try {
					ReadableNBT itemNBT = NBT.parseNBT(itemData[1]);
					NBT.modifyComponents(item, nbt -> {
						nbt.mergeCompound(itemNBT);
					});
				} catch (Exception e) {
					this.logger.warn("Invalid NBT data for item {}: {}", material, itemData[1], e);
					return null;
				}
			}
			return item;
		} catch (IllegalArgumentException e) {
			this.logger.warn("The material {} does not exist.", configItem);
			this.logger.warn("Skipping this item. Please check your {}", fileName);
			this.logger.warn("Printing stack trace:", e);
			return null;
		}
	}
}
