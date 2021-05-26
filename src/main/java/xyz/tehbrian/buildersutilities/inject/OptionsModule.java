package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.option.OptionsInventoryProvider;

/**
 * Guice module which binds the inventory provider for the options menu.
 */
public class OptionsModule extends AbstractModule {

    /**
     * Binds the inventory provider for the options menu.
     */
    @Override
    protected void configure() {
        this.bind(OptionsInventoryProvider.class).asEagerSingleton();
    }

}
