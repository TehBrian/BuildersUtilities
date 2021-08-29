package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

/**
 * Guice module which provides bindings for the plugin's instances.
 */
public final class PluginModule extends AbstractModule {

    private final BuildersUtilities buildersUtilities;

    /**
     * @param buildersUtilities BuildersUtilities reference
     */
    public PluginModule(final @NonNull BuildersUtilities buildersUtilities) {
        this.buildersUtilities = buildersUtilities;
    }

    @Override
    protected void configure() {
        this.bind(BuildersUtilities.class).toInstance(this.buildersUtilities);
        this.bind(JavaPlugin.class).toInstance(this.buildersUtilities);
    }

    /**
     * Provides the plugin's Log4J logger.
     *
     * @return the plugin's Log4J logger
     */
    @Provides
    public @NonNull Logger provideLog4JLogger() {
        return this.buildersUtilities.getLog4JLogger();
    }

}
