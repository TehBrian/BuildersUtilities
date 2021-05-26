package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.config.Lang;

/**
 * Guice module which provides the various configs.
 */
public class ConfigModule extends AbstractModule {

    /**
     * Binds {@link Lang}.
     */
    @Override
    protected void configure() {
        this.bind(Lang.class).asEagerSingleton();
    }

}
