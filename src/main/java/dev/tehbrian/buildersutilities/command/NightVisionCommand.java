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

public final class NightVisionCommand {

	private final UserService userService;
	private final LangConfig langConfig;

	@Inject
	public NightVisionCommand(
			final UserService userService,
			final LangConfig langConfig
	) {
		this.userService = userService;
		this.langConfig = langConfig;
	}

	public void register(final PaperCommandManager<Source> commandManager) {
		final var root = commandManager.commandBuilder("nightvision", "nv")
				.commandDescription(description("Toggles night vision."))
				.permission(Permissions.NIGHT_VISION)
				.senderType(PlayerSource.class);

		final var on = root.literal("on", description("Enables night vision."))
				.handler(c -> {
					final var sender = c.sender().source();
					this.userService.getUser(sender).nightVisionEnabled(true);
					sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "enabled")));
				});

		final var off = root.literal("off", description("Disables night vision."))
				.handler(c -> {
					final var sender = c.sender().source();
					this.userService.getUser(sender).nightVisionEnabled(false);
					sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "disabled")));
				});

		final var toggle = root
				.handler(c -> {
					final var sender = c.sender().source();
					if (this.userService.getUser(sender).toggleNightVisionEnabled()) {
						sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "enabled")));
					} else {
						sender.sendMessage(this.langConfig.c(NodePath.path("commands", "night-vision", "disabled")));
					}
				});

		commandManager.command(on)
				.command(off)
				.command(toggle);
	}

}
