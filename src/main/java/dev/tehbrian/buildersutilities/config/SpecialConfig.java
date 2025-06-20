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
			final var configItem = dirtyItemName.strip();

			final Material itemMaterial;
			try {
				if (configItem.contains(", ")) {
					this.items.add(this.itemFromString(configItem));
					continue;
				}
				itemMaterial = Material.valueOf(configItem.toUpperCase());
			} catch (final IllegalArgumentException e) {
				this.logger.warn("The material {} does not exist.", configItem);
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

	private ItemStack itemFromString(String configItem) {
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
				this.logger.warn("Invalid NBT data for item {}: {}", material, itemData[1]);
				this.logger.warn("Skipping this item. Please check your special.yml", e);
			}
		}
		return item;
	}
}
