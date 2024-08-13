package dev.tehbrian.buildersutilities.config;

import dev.tehbrian.tehlib.configurate.ConfigurateWrapper;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;

public class YamlConfigurateWrapper extends ConfigurateWrapper<YamlConfigurationLoader> {

	/**
	 * @param path the path for the config
	 */
	public YamlConfigurateWrapper(final Path path) {
		super(path, YamlConfigurationLoader.builder().path(path).build());
	}

	/**
	 * @param filePath the path for the config
	 * @param loader   the loader
	 */
	public YamlConfigurateWrapper(final Path filePath, final YamlConfigurationLoader loader) {
		super(filePath, loader);
	}

}
