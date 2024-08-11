package dev.tehbrian.buildersutilities.command;

import cloud.commandframework.arguments.standard.DoubleArgument;
import cloud.commandframework.arguments.standard.EnumArgument;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.BuildersUtilities;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldEditAliases {

	private final BuildersUtilities buildersUtilities;

	@Inject
	public WorldEditAliases(
			final BuildersUtilities buildersUtilities
	) {
		this.buildersUtilities = buildersUtilities;
	}

	/**
	 * Registers WorldEdit aliases.
	 * <p>
	 * If {@code isFawe}, //1, //2, and //pa will not be registered.
	 *
	 * @param commandManager the command manager
	 * @param isFawe         whether the WorldEdit plugin is FastAsyncWorldEdit
	 */
	public void register(final PaperCommandManager<CommandSender> commandManager, final boolean isFawe) {
		if (!isFawe) {
			commandManager.command(commandManager.commandBuilder("/p1", "/1")
					.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
					.permission(Permissions.WORLDEDIT_ALIASES)
					.senderType(Player.class)
					.handler(c -> {
						final var sender = (Player) c.getSender();
						this.dispatch(sender, "/pos1");
					}));

			commandManager.command(commandManager.commandBuilder("/p2", "/2")
					.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
					.permission(Permissions.WORLDEDIT_ALIASES)
					.senderType(Player.class)
					.handler(c -> {
						final var sender = (Player) c.getSender();
						this.dispatch(sender, "/pos2");
					}));

			commandManager.command(commandManager.commandBuilder("/pa")
					.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
					.permission(Permissions.WORLDEDIT_ALIASES)
					.senderType(Player.class)
					.argument(StringArgument.<CommandSender>builder("args").greedy().asOptional())
					.handler(c -> {
						final var sender = (Player) c.getSender();
						this.dispatch(sender, "/paste " + c.getOrDefault("args", ""));
					}));
		}

		commandManager.command(commandManager.commandBuilder("/cuboid", "/cub")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.handler(c -> {
					final var sender = (Player) c.getSender();
					this.dispatch(sender, "/sel cuboid");
				}));

		commandManager.command(commandManager.commandBuilder("/convex", "/con")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.handler(c -> {
					final var sender = (Player) c.getSender();
					this.dispatch(sender, "/sel convex");
				}));

		commandManager.command(commandManager.commandBuilder("/s")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.argument(StringArgument.<CommandSender>builder("args").greedy().asOptional())
				.handler(c -> {
					final var sender = (Player) c.getSender();
					this.dispatch(sender, "/set " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/r")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.argument(StringArgument.<CommandSender>builder("args").greedy().asOptional())
				.handler(c -> {
					final var sender = (Player) c.getSender();
					this.dispatch(sender, "/replace " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/f")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.argument(StringArgument.<CommandSender>builder("args").greedy().asOptional())
				.handler(c -> {
					final var sender = (Player) c.getSender();
					this.dispatch(sender, "/flip " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/c")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.argument(StringArgument.<CommandSender>builder("args").greedy().asOptional())
				.handler(c -> {
					final var sender = (Player) c.getSender();
					this.dispatch(sender, "/copy " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/derot")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.argument(EnumArgument.of(Axis.class, "axis"))
				.argument(IntegerArgument.of("degrees"))
				.handler(c -> {
					final var sender = (Player) c.getSender();
					final double radians = Math.toRadians(c.<Integer>get("degrees"));
					switch (c.<Axis>get("axis")) {
						case X -> this.dispatch(sender, "/deform rotate(y,z," + radians + ")");
						case Y -> this.dispatch(sender, "/deform rotate(x,z," + radians + ")");
						case Z -> this.dispatch(sender, "/deform rotate(x,y," + radians + ")");
						default -> {
						}
					}
				}));

		commandManager.command(commandManager.commandBuilder("/twist")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.argument(EnumArgument.of(Axis.class, "axis"))
				.argument(IntegerArgument.of("degrees"))
				.handler(c -> {
					final var sender = (Player) c.getSender();
					final double radians = Math.toRadians(c.<Integer>get("degrees"));
					switch (c.<Axis>get("axis")) {
						case X -> this.dispatch(sender, "/deform rotate(y,z," + radians / 2 + "*(x+1))");
						case Y -> this.dispatch(sender, "/deform rotate(x,z," + radians / 2 + "*(y+1))");
						case Z -> this.dispatch(sender, "/deform rotate(x,y," + radians / 2 + "*(z+1))");
						default -> {
						}
					}
				}));

		commandManager.command(commandManager.commandBuilder("/scale")
				.meta(CommandMeta.DESCRIPTION, "WorldEdit alias.")
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(Player.class)
				.argument(DoubleArgument.of("size"))
				.handler(c -> {
					final var sender = (Player) c.getSender();
					final double size = c.<Double>get("size");
					this.dispatch(sender, "/deform x/=" + size + ";y/=" + size + ";z/=" + size);
				}));
	}

	private void dispatch(final CommandSender sender, final String command) {
		this.buildersUtilities.getServer().dispatchCommand(sender, command);
	}

	enum Axis {
		X,
		Y,
		Z
	}

}
