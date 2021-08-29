package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.config.LangConfig;

/**
 * Guice module which provides the various configs.
 */
public class ConfigModule extends AbstractModule {

    /**
     * Binds {@link LangConfig}.
     */
    @Override
    protected void configure() {
        this.bind(LangConfig.class).asEagerSingleton();
    }

}
