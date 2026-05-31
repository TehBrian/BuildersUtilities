package dev.tehbrian.buildersutilities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.tehbrian.agna.paper.UpdateChecker;
import dev.tehbrian.agna.paper.configurate.ConfigLoader;
import dev.tehbrian.agna.paper.configurate.ConfigLoader.Loadable;
import dev.tehbrian.buildersutilities.ability.AbilityMenuListener;
import dev.tehbrian.buildersutilities.ability.AdvancedFlyListener;
import dev.tehbrian.buildersutilities.ability.DoubleSlabListener;
import dev.tehbrian.buildersutilities.ability.GlazedTerracottaListener;
import dev.tehbrian.buildersutilities.ability.IronDoorListener;
import dev.tehbrian.buildersutilities.ability.NoclipManager;
import dev.tehbrian.buildersutilities.armorcolor.ArmorColorMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.BaseMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.ColorMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.DoneMenuListener;
import dev.tehbrian.buildersutilities.banner.menu.PatternMenuListener;
import dev.tehbrian.buildersutilities.command.AdvancedFlyCommand;
import dev.tehbrian.buildersutilities.command.ArmorColorCommand;
import dev.tehbrian.buildersutilities.command.BannerCommand;
import dev.tehbrian.buildersutilities.command.BuildersUtilitiesCommand;
import dev.tehbrian.buildersutilities.command.NightVisionCommand;
import dev.tehbrian.buildersutilities.command.NoclipCommand;
import dev.tehbrian.buildersutilities.command.WorldEditAliases;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.config.SpecialConfig;
import dev.tehbrian.buildersutilities.inject.PluginModule;
import dev.tehbrian.buildersutilities.inject.SingletonModule;
import dev.tehbrian.buildersutilities.setting.SettingsListener;
import dev.tehbrian.buildersutilities.special.SpecialMenuListener;
import dev.tehbrian.mayi.paper.PaperMayi;
import dev.tehbrian.mayi.paper.PaperRestrictionLoader;
import dev.tehbrian.mayi.paper.restrictions.R_PlotSquared_6_7;
import dev.tehbrian.mayi.paper.restrictions.R_WorldGuard_7;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.incendo.cloud.exception.NoPermissionException;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.Source;
import org.jspecify.annotations.Nullable;
import org.spongepowered.configurate.NodePath;

import java.util.Arrays;
import java.util.List;

import static dev.tehbrian.agna.paper.PluginUtils.disableSelf;
import static dev.tehbrian.agna.paper.PluginUtils.registerListeners;
import static org.incendo.cloud.execution.ExecutionCoordinator.simpleCoordinator;
import static org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper.simpleSenderMapper;

/**
 * The main class for the BuildersUtilities plugin.
 */
public final class BuildersUtilities extends JavaPlugin {

	private static final int BSTATS_PLUGIN_ID = 31665;

	private @Nullable PaperCommandManager<Source> commandManager;
	private @MonotonicNonNull Injector injector;

	private static boolean isEmpty(final Component component) {
		return PlainTextComponentSerializer.plainText().serialize(component).isEmpty();
	}

	@Override
	public void onEnable() {
		try {
			this.injector = Guice.createInjector(
					new PluginModule(this),
					new SingletonModule()
			);
		} catch (final Exception e) {
			this.getSLF4JLogger().error("Something went wrong while creating the injector. Disabling plugin");
			disableSelf(this);
			this.getSLF4JLogger().error("Printing stack trace. Please send this to the developers", e);
			return;
		}

		if (!this.loadConfiguration()) {
			disableSelf(this);
			return;
		}

		if (!this.initCommands()) {
			disableSelf(this);
			return;
		}

		this.initListeners();
		this.initRestrictions();

		this.injector.getInstance(NoclipManager.class).start();

		// initialize bStats.
		Metrics _ = new Metrics(this, BSTATS_PLUGIN_ID);

		new UpdateChecker(this, "buildersutilities").checkForUpdates();
	}

	/**
	 * Loads the plugin's configuration.
	 * <p>
	 * If there is an error while loading a config file, the exception is logged
	 * and the file is skipped.
	 *
	 * @return whether all config files were successfully loaded
	 */
	public boolean loadConfiguration() {
		return new ConfigLoader(this).load(List.of(
				Loadable.ofVersioned("config.yml", this.injector.getInstance(ConfigConfig.class), 2),
				Loadable.ofVersioned("lang.yml", this.injector.getInstance(LangConfig.class), 2),
				Loadable.ofVersioned("special.yml", this.injector.getInstance(SpecialConfig.class), 2)
		));
	}

	/**
	 * @return whether it was successful
	 */
	private boolean initCommands() {
		if (this.commandManager != null) {
			throw new IllegalStateException("The CommandManager is already instantiated");
		}

		try {
			this.commandManager = PaperCommandManager
					.builder(simpleSenderMapper())
					.executionCoordinator(simpleCoordinator())
					.buildOnEnable(this);
		} catch (final Exception e) {
			this.getSLF4JLogger().error("An error occurred while creating the command manager");
			this.getSLF4JLogger().error("Printing stack trace. Please send this to the developers", e);
			return false;
		}

		final LangConfig langConfig = this.injector.getInstance(LangConfig.class);

		final MinecraftExceptionHandler.MessageFactory<Source, NoPermissionException> noPermissionHandler = (formatter, ctx) -> {
			final var noPermission = langConfig.c(NodePath.path("commands", "no-permission"));
			if (isEmpty(noPermission)) {
				return this.getServer().permissionMessage();
			} else {
				return noPermission;
			}
		};

		MinecraftExceptionHandler.create(Source::source)
				.defaultArgumentParsingHandler()
				.defaultInvalidSenderHandler()
				.defaultInvalidSyntaxHandler()
				.handler(NoPermissionException.class, noPermissionHandler)
				.defaultCommandExecutionHandler()
				.registerTo(this.commandManager);

		this.injector.getInstance(AdvancedFlyCommand.class).register(this.commandManager);
		this.injector.getInstance(ArmorColorCommand.class).register(this.commandManager);
		this.injector.getInstance(BannerCommand.class).register(this.commandManager);
		this.injector.getInstance(BuildersUtilitiesCommand.class).register(this.commandManager);
		this.injector.getInstance(NightVisionCommand.class).register(this.commandManager);
		this.injector.getInstance(NoclipCommand.class).register(this.commandManager);

		if (this.injector.getInstance(ConfigConfig.class).data().worldEditAliases()) {
			final PluginManager pm = this.getServer().getPluginManager();
			if (pm.isPluginEnabled("WorldEdit")) {
				this.injector.getInstance(WorldEditAliases.class).register(this.commandManager, false);
			} else if (pm.isPluginEnabled("FastAsyncWorldEdit")) {
				this.injector.getInstance(WorldEditAliases.class).register(this.commandManager, true);
			} else {
				this.getSLF4JLogger().error("worledit-aliases is enabled in config.yml, but WorldEdit isn't present "
						+ "on this server. WorldEdit aliases will not be registered");
			}
		}

		return true;
	}

	private void initListeners() {
		registerListeners(
				this,
				this.injector.getInstance(AbilityMenuListener.class),
				this.injector.getInstance(ArmorColorMenuListener.class),
				this.injector.getInstance(SpecialMenuListener.class),

				this.injector.getInstance(BaseMenuListener.class),
				this.injector.getInstance(ColorMenuListener.class),
				this.injector.getInstance(PatternMenuListener.class),
				this.injector.getInstance(DoneMenuListener.class),

				this.injector.getInstance(AdvancedFlyListener.class),
				this.injector.getInstance(DoubleSlabListener.class),
				this.injector.getInstance(GlazedTerracottaListener.class),
				this.injector.getInstance(IronDoorListener.class),
				this.injector.getInstance(SettingsListener.class)
		);
	}

	private void initRestrictions() {
		final var loader = new PaperRestrictionLoader(
				this.getSLF4JLogger(),
				Arrays.asList(this.getServer().getPluginManager().getPlugins()),
				List.of(R_PlotSquared_6_7.class, R_WorldGuard_7.class)
		);

		loader.load(this.injector.getInstance(PaperMayi.class));
	}

}
