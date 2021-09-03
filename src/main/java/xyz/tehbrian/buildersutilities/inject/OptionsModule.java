package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.option.OptionsInventoryProvider;

/**
 * Guice module which provides bindings for the options menu inventory provider.
 */
public class OptionsModule extends AbstractModule {

    /**
     * Binds the options menu inventory provider as an eager singleton.
     */
    @Override
    protected void configure() {
        this.bind(OptionsInventoryProvider.class).asEagerSingleton();
    }

}
