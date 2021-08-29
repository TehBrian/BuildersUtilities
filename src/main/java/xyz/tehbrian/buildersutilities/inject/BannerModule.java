package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.banner.provider.BannerBaseInventoryProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerPatternInventoryProvider;

/**
 * Guice module which binds the inventory providers for the banner creator.
 */
public class BannerModule extends AbstractModule {

    /**
     * Binds the banner creator inventory providers as eager singletons.
     */
    @Override
    protected void configure() {
        this.bind(BannerBaseInventoryProvider.class).asEagerSingleton();
        this.bind(BannerColorInventoryProvider.class).asEagerSingleton();
        this.bind(BannerPatternInventoryProvider.class).asEagerSingleton();
    }

}
