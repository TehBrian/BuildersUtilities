package dev.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import dev.tehbrian.buildersutilities.BuildersUtilities;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class PluginModule extends AbstractModule {

	private final BuildersUtilities buildersUtilities;

	public PluginModule(final BuildersUtilities buildersUtilities) {
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
	public Logger provideSLF4JLogger() {
		return this.buildersUtilities.getSLF4JLogger();
	}

	/**
	 * @return the plugin's data folder
	 */
	@SuppressWarnings("unused")
	@Provides
	@Named("dataFolder")
	public Path provideDataFolder() {
		return this.buildersUtilities.getDataFolder().toPath();
	}

}
