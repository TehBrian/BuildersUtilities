package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.user.UserService;

/**
 * Guice module which provides bindings for {@link UserService}.
 */
public class UserModule extends AbstractModule {

    /**
     * Binds {@link UserService} as an eager singleton.
     */
    @Override
    protected void configure() {
        this.bind(UserService.class).asEagerSingleton();
    }

}
