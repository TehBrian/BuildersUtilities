package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.configurate.AbstractDataConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;

/**
 * Loads and holds values for {@code config.yml}.
 */
public final class ConfigConfig extends AbstractDataConfig<YamlConfigurateWrapper, ConfigConfig.Data> {

	@Inject
	public ConfigConfig(final @Named("dataFolder") Path dataFolder) {
		super(new YamlConfigurateWrapper(dataFolder.resolve("config.yml"), YamlConfigurationLoader.builder()
				.path(dataFolder.resolve("config.yml"))
				.defaultOptions(opts -> opts.implicitInitialization(false))
				.build()));
	}

	@Override
	protected Class<Data> dataClass() {
		return Data.class;
	}

	@ConfigSerializable
	public record Data(Settings settings,
										 Heads heads,
										 @Setting("worldedit-aliases") boolean worldEditAliases) {

		@ConfigSerializable
		public record Settings(boolean disableRedstone,
													 boolean disableGravityPhysics,
													 boolean disablePhysics,
													 boolean disableEntityExplode,
													 boolean disableBlockExplode,
													 boolean disableLeavesDecay,
													 boolean disableFarmlandTrample,
													 boolean disableDragonEggTeleport) {

		}

		@ConfigSerializable
		public record Heads(ArmorColor armorColor,
												Banner banner) {

			@ConfigSerializable
			public record ArmorColor(String red,
															 String green,
															 String blue,
															 String randomizeRed,
															 String randomizeGreen,
															 String randomizeBlue) {

			}

			@ConfigSerializable
			public record Banner(String randomize,
													 String undo) {

			}

		}

	}

}
