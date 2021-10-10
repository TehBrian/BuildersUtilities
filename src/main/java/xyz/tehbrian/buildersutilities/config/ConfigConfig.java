package xyz.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.core.configurate.AbstractDataConfig;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;

/**
 * Loads and holds values for {@code config.yml}.
 */
public final class ConfigConfig extends AbstractDataConfig<YamlConfigurateWrapper, ConfigConfig.Data> {

    @Inject
    public ConfigConfig(final @NonNull @Named("dataFolder") Path dataFolder) {
        super(new YamlConfigurateWrapper(dataFolder.resolve("config.yml"), YamlConfigurationLoader.builder()
                .path(dataFolder.resolve("config.yml"))
                .defaultOptions(opts -> opts.implicitInitialization(false))
                .build()));
    }

    @Override
    protected Class<Data> getDataClass() {
        return Data.class;
    }

    @ConfigSerializable
    public static record Data(@NonNull Settings settings,
                              @NonNull Heads heads) {

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

}
