package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.LoggerFactory;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.logging.Logger;

/**
 * Guice module which provides bindings for the plugin and the plugin's logger.
 */
public final class PluginModule extends AbstractModule {

    /**
     * {@code BuildersUtilities} reference.
     */
    private final BuildersUtilities buildersUtilities;

    /**
     * @param buildersUtilities {@code BuildersUtilities} reference
     */
    public PluginModule(final @NonNull BuildersUtilities buildersUtilities) {
        this.buildersUtilities = buildersUtilities;
    }

    /**
     * Binds the plugin classes.
     */
    @Override
    protected void configure() {
        this.bind(JavaPlugin.class).toInstance(this.buildersUtilities);
        this.bind(BuildersUtilities.class).toInstance(this.buildersUtilities);
    }

    /**
     * Provides the plugin's logger.
     *
     * @return the plugin's logger
     */
    @Provides
    @PluginLogger
    public Logger provideLogger() {
        return this.buildersUtilities.getLogger();
    }

    /**
     * Provides the plugin's SLF4J logger.
     *
     * @return the plugin's SLF4J logger
     */
    @Provides
    public org.slf4j.Logger provideSLF4JLogger() {
        return LoggerFactory.getLogger(this.buildersUtilities.getLogger().getName());
    }

}
