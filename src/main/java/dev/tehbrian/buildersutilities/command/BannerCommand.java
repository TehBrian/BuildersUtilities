package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.PlayerSessions;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;

import static org.incendo.cloud.description.Description.description;

public final class BannerCommand {

	private final PlayerSessions playerSessions;

	@Inject
	public BannerCommand(
			final PlayerSessions playerSessions
	) {
		this.playerSessions = playerSessions;
	}

	public void register(final PaperCommandManager<Source> commandManager) {
		final var main = commandManager.commandBuilder("banner", "bc")
				.commandDescription(description("Opens the banner creator."))
				.permission(Permissions.BANNER)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					this.playerSessions.get(sender).showInterface(sender);
				});

		commandManager.command(main);
	}

}
