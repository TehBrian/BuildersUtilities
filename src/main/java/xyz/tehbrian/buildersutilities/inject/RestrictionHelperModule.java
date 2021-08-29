package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;

/**
 * Guice module which provides bindings for RestrictionHelper.
 */
public class RestrictionHelperModule extends AbstractModule {

    /**
     * Provides the {@code RestrictionHelper} instance.
     *
     * @return the {code RestrictionHelper} instance
     */
    @Provides
    @Singleton
    public SpigotRestrictionHelper provideRestrictionHelper() {
        return new SpigotRestrictionHelper();
    }

}
