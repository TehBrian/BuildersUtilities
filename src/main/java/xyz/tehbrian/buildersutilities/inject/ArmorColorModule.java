package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInventoryProvider;

/**
 * Guice module which binds the inventory provider for the armor color creator.
 */
public class ArmorColorModule extends AbstractModule {

    /**
     * Binds the inventory provider for the armor color creator.
     */
    @Override
    protected void configure() {
        this.bind(ArmorColorInventoryProvider.class).asEagerSingleton();
    }

}
