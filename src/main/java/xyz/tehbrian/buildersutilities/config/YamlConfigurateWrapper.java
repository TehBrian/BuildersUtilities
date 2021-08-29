package xyz.tehbrian.buildersutilities.config;

import dev.tehbrian.tehlib.core.configurate.ConfigurateWrapper;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;

public class YamlConfigurateWrapper extends ConfigurateWrapper<YamlConfigurationLoader> {

    /**
     * @param logger   the logger
     * @param filePath the file path for the config
     */
    public YamlConfigurateWrapper(
            @NonNull final Logger logger,
            @NonNull final Path filePath
    ) {
        super(logger, filePath, YamlConfigurationLoader.builder()
                .path(filePath)
                .build());
    }

    /**
     * @param logger   the logger
     * @param filePath the file path for the config
     * @param loader   the loader
     */
    public YamlConfigurateWrapper(
            @NonNull final Logger logger,
            @NonNull final Path filePath,
            @NonNull final YamlConfigurationLoader loader
    ) {
        super(logger, filePath, loader);
    }

}
