package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.configurate.AbstractDataConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
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
  protected Class<Data> getDataClass() {
    return Data.class;
  }

  @ConfigSerializable
  public record Data(Settings settings,
                     Heads heads) {

    @SuppressWarnings("unused")
    @ConfigSerializable
    public record Settings(boolean disablePhysics,
                           boolean disableEntityExplode,
                           boolean disableBlockExplode,
                           boolean disableLeavesDecay,
                           boolean disableFarmlandTrample,
                           boolean disableDragonEggTeleport) {

    }

    @SuppressWarnings("unused")
    @ConfigSerializable
    public record Heads(ArmorColor armorColor,
                        Banner banner) {

      @SuppressWarnings("unused")
      @ConfigSerializable
      public record ArmorColor(String red,
                               String green,
                               String blue,
                               String randomizeRed,
                               String randomizeGreen,
                               String randomizeBlue) {

      }

      @SuppressWarnings("unused")
      @ConfigSerializable
      public record Banner(String randomize,
                           String undo) {

      }

    }

  }

}
