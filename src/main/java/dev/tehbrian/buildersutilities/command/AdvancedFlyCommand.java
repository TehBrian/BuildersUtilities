package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.spongepowered.configurate.NodePath;

import static org.incendo.cloud.description.Description.description;

public final class AdvancedFlyCommand {

	private final UserService userService;
	private final LangConfig langConfig;

	@Inject
	public AdvancedFlyCommand(
			final UserService userService,
			final LangConfig langConfig
	) {
		this.userService = userService;
		this.langConfig = langConfig;
	}

	public void register(final PaperCommandManager<Source> commandManager) {
		final var root = commandManager.commandBuilder("advancedfly", "advfly", "af")
				.commandDescription(description("Toggles advanced fly."))
				.permission(Permissions.ADVANCED_FLY)
				.senderType(PlayerSource.class);

		final var on = root.literal("on", description("Enables advanced fly."))
				.handler(c -> {
					final var sender = c.sender().source();
					this.userService.getUser(sender).advancedFlyEnabled(true);
					sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "enabled")));
				});

		final var off = root.literal("off", description("Disables advanced fly."))
				.handler(c -> {
					final var sender = c.sender().source();
					this.userService.getUser(sender).advancedFlyEnabled(false);
					sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "disabled")));
				});

		final var toggle = root
				.handler(c -> {
					final var sender = c.sender().source();
					if (this.userService.getUser(sender).toggleAdvancedFlyEnabled()) {
						sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "enabled")));
					} else {
						sender.sendMessage(this.langConfig.c(NodePath.path("commands", "advanced-fly", "disabled")));
					}
				});

		commandManager.command(on)
				.command(off)
				.command(toggle);
	}

}
