package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.nio.file.Path;

public final class PluginModule extends AbstractModule {

    private final BuildersUtilities buildersUtilities;

    public PluginModule(final @NonNull BuildersUtilities buildersUtilities) {
        this.buildersUtilities = buildersUtilities;
    }

    @Override
    protected void configure() {
        this.bind(BuildersUtilities.class).toInstance(this.buildersUtilities);
        this.bind(JavaPlugin.class).toInstance(this.buildersUtilities);
    }

    /**
     * @return the plugin's SLF4J logger
     */
    @SuppressWarnings("unused")
    @Provides
    public @NonNull Logger provideSLF4JLogger() {
        return this.buildersUtilities.getSLF4JLogger();
    }

    /**
     * @return the plugin's data folder
     */
    @SuppressWarnings("unused")
    @Provides
    @Named("dataFolder")
    public @NonNull Path provideDataFolder() {
        return this.buildersUtilities.getDataFolder().toPath();
    }

}
