package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;

/**
 * Guice module which provides bindings for RestrictionHelper.
 */
public class RestrictionHelperModule extends AbstractModule {

    /**
     * Binds {@link SpigotRestrictionHelper} as an eager singleton.
     */
    @Override
    protected void configure() {
        this.bind(SpigotRestrictionHelper.class).asEagerSingleton();
    }

}
