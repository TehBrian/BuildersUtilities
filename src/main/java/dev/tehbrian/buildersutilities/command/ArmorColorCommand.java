package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.armorcolor.ArmorColorMenuProvider;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;

import static org.incendo.cloud.description.Description.description;

public final class ArmorColorCommand {

	private final ArmorColorMenuProvider armorColorMenuProvider;

	@Inject
	public ArmorColorCommand(
			final ArmorColorMenuProvider armorColorMenuProvider
	) {
		this.armorColorMenuProvider = armorColorMenuProvider;
	}

	public void register(final PaperCommandManager<Source> commandManager) {
		final var main = commandManager.commandBuilder("armorcolor", "acc")
				.commandDescription(description("Opens the armor color creator."))
				.permission(Permissions.ARMOR_COLOR)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					sender.openInventory(this.armorColorMenuProvider.generate());
				});

		commandManager.command(main);
	}

}
