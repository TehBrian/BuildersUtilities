package xyz.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.core.configurate.AbstractConfig;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;

/**
 * Loads and holds values for {@code config.yml}.
 */
public final class ConfigConfig extends AbstractConfig<YamlConfigurateWrapper> {

    private @Nullable Settings settings;
    private @Nullable Heads heads;

    /**
     * @param logger     the logger
     * @param dataFolder the data folder
     */
    @Inject
    public ConfigConfig(
            final @NotNull Logger logger,
            final @NotNull @Named("dataFolder") Path dataFolder
    ) {
        super(logger, new YamlConfigurateWrapper(logger, dataFolder.resolve("config.yml"), YamlConfigurationLoader.builder()
                .path(dataFolder.resolve("config.yml"))
                .defaultOptions(opts -> opts.implicitInitialization(false))
                .build()));
    }

    @Override
    public void load() {
        this.configurateWrapper.load();
        final CommentedConfigurationNode rootNode = this.configurateWrapper.get();

        try {
            this.settings = rootNode.node("settings").get(Settings.class);
            this.heads = rootNode.node("heads").get(Heads.class);
        } catch (final SerializationException e) {
            this.logger.warn("Exception caught during configuration deserialization for config.yml.");
            this.logger.warn("The plugin will not work correctly. Please check your config.yml config file.");
            this.logger.warn("Printing stack trace.");
            this.logger.warn(e.getMessage(), e);
            return;
        }

        if (this.settings == null || this.heads == null) {
            this.logger.warn("Deserialized configuration for config.yml was null!");
            this.logger.warn("The plugin will not work correctly. Please check your config.yml config file.");
            return;
        }

        this.logger.info("Successfully loaded all values for {}!", this.configurateWrapper.filePath().getFileName());
    }

    /**
     * @return the settings
     */
    public Settings settings() {
        return settings;
    }

    /**
     * @return the heads
     */
    public Heads heads() {
        return heads;
    }

    @ConfigSerializable
    public static record Settings(boolean disablePhysics,
                                  boolean disableEntityExplode,
                                  boolean disableBlockExplode,
                                  boolean disableLeavesDecay,
                                  boolean disableFarmlandTrample,
                                  boolean disableDragonEggTeleport) {

    }

    @ConfigSerializable
    public static record Heads(@NonNull ArmorColor armorColor,
                               @NonNull Banner banner) {

        @ConfigSerializable
        public static record ArmorColor(@NonNull String red,
                                        @NonNull String green,
                                        @NonNull String blue,
                                        @NonNull String randomizeRed,
                                        @NonNull String randomizeGreen,
                                        @NonNull String randomizeBlue) {

        }

        @ConfigSerializable
        public static record Banner(@NonNull String randomize) {

        }

    }

}
