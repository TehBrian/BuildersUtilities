package dev.tehbrian.buildersutilities.command;

import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.armorcolor.ArmorColorMenuProvider;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class ArmorColorCommand {

	private final ArmorColorMenuProvider armorColorMenuProvider;

	@Inject
	public ArmorColorCommand(
			final ArmorColorMenuProvider armorColorMenuProvider
	) {
		this.armorColorMenuProvider = armorColorMenuProvider;
	}

	public void register(final PaperCommandManager<CommandSender> commandManager) {
		final var main = commandManager.commandBuilder("armorcolor", "acc")
				.meta(CommandMeta.DESCRIPTION, "Opens the armor color creator.")
				.permission(Permissions.ARMOR_COLOR)
				.senderType(Player.class)
				.handler(c -> {
					final var sender = (Player) c.getSender();
					sender.openInventory(this.armorColorMenuProvider.generate());
				});

		commandManager.command(main);
	}

}
