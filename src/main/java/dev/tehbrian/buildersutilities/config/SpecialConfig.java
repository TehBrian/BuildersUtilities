package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.tehbrian.agna.configurate.AbstractConfig;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import org.bukkit.inventory.ItemStack;
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

		final List<String> itemStrings = rootNode.node("items").getList(String.class);
		if (itemStrings == null) {
			return;
		}

		for (final String itemString : itemStrings) {
			final ItemStack item;
			try {
				item = ArgumentTypes.itemStack().parse(new StringReader(itemString));
			} catch (final CommandSyntaxException e) {
				this.logger.warn("The item {} could not be parsed", itemString);
				this.logger.warn("Skipping this item. Please check your {}", fileName);
				this.logger.warn("Printing stack trace", e);
				continue;
			}

			this.items.add(item);
		}
	}

	public List<ItemStack> items() {
		return this.items;
	}

}
