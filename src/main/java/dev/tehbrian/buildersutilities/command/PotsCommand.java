package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.pots.PotMenuProvider;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;

import static org.incendo.cloud.description.Description.description;

public final class PotsCommand {

	private final PotMenuProvider potMenuProvider;

	@Inject
	public PotsCommand(
			final PotMenuProvider potMenuProvider
	) {
		this.potMenuProvider = potMenuProvider;
	}

	public void register(final PaperCommandManager<Source> commandManager) {
		final var main = commandManager.commandBuilder("pots", "pot")
				.commandDescription(description("Opens the pot creator."))
				.permission(Permissions.POTS)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					sender.openInventory(this.potMenuProvider.generate());
				});

		commandManager.command(main);
	}

}
