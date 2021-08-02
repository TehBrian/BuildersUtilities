package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import xyz.tehbrian.buildersutilities.user.UserService;

/**
 * Guice module which provides bindings for {@link UserService}.
 */
public class UserModule extends AbstractModule {

    /**
     * Provides the {@code UserManager} instance.
     *
     * @return the {@code UserManager}
     */
    @Provides
    @Singleton
    public UserService provideUserManager() {
        return new UserService();
    }

}
