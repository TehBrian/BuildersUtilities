package xyz.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.core.configurate.AbstractConfig;
import org.slf4j.Logger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SpecialConfig extends AbstractConfig<YamlConfigurateWrapper> {

    private final Logger logger;

    private final @NonNull List<@NonNull ItemStack> items = new ArrayList<>();

    @Inject
    public SpecialConfig(final @NonNull @Named("dataFolder") Path dataFolder, final @NonNull Logger logger) {
        super(new YamlConfigurateWrapper(dataFolder.resolve("special.yml")));
        this.logger = logger;
    }

    @Override
    public void load() throws ConfigurateException {
        this.configurateWrapper.load();
        final @NonNull CommentedConfigurationNode rootNode = Objects.requireNonNull(this.configurateWrapper.get()); // will not be null as we called #load()
        final String fileName = this.configurateWrapper.filePath().getFileName().toString();

        this.items.clear();

        final @Nullable List<String> itemNames = rootNode.node("items").getList(String.class);
        if (itemNames == null) {
            return;
        }

        for (final String itemName : itemNames) {
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

    public @NonNull List<@NonNull ItemStack> items() {
        return this.items;
    }

}
