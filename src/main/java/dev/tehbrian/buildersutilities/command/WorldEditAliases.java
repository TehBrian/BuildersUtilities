package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.BuildersUtilities;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;

import static org.incendo.cloud.component.CommandComponent.builder;
import static org.incendo.cloud.description.Description.description;
import static org.incendo.cloud.parser.standard.DoubleParser.doubleParser;
import static org.incendo.cloud.parser.standard.EnumParser.enumParser;
import static org.incendo.cloud.parser.standard.IntegerParser.integerParser;
import static org.incendo.cloud.parser.standard.StringParser.greedyStringParser;

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
	public void register(final PaperCommandManager<Source> commandManager, final boolean isFawe) {
		if (!isFawe) {
			commandManager.command(commandManager.commandBuilder("/p1", "/1")
					.commandDescription(description("WorldEdit alias."))
					.permission(Permissions.WORLDEDIT_ALIASES)
					.senderType(PlayerSource.class)
					.handler(c -> {
						final var sender = c.sender().source();
						this.dispatch(sender, "/pos1");
					}));

			commandManager.command(commandManager.commandBuilder("/p2", "/2")
					.commandDescription(description("WorldEdit alias."))
					.permission(Permissions.WORLDEDIT_ALIASES)
					.senderType(PlayerSource.class)
					.handler(c -> {
						final var sender = c.sender().source();
						this.dispatch(sender, "/pos2");
					}));

			commandManager.command(commandManager.commandBuilder("/pa")
					.commandDescription(description("WorldEdit alias."))
					.permission(Permissions.WORLDEDIT_ALIASES)
					.senderType(PlayerSource.class)
					.argument(builder("args", greedyStringParser()).optional())
					.handler(c -> {
						final var sender = c.sender().source();
						this.dispatch(sender, "/paste " + c.getOrDefault("args", ""));
					}));
		}

		commandManager.command(commandManager.commandBuilder("/cuboid", "/cub")
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					this.dispatch(sender, "/sel cuboid");
				}));

		commandManager.command(commandManager.commandBuilder("/convex", "/con")
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					this.dispatch(sender, "/sel convex");
				}));

		commandManager.command(commandManager.commandBuilder("/s")
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.argument(builder("args", greedyStringParser()).optional())
				.handler(c -> {
					final var sender = c.sender().source();
					this.dispatch(sender, "/set " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/r")
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.argument(builder("args", greedyStringParser()).optional())
				.handler(c -> {
					final var sender = c.sender().source();
					this.dispatch(sender, "/replace " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/f")
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.argument(builder("args", greedyStringParser()).optional())
				.handler(c -> {
					final var sender = c.sender().source();
					this.dispatch(sender, "/flip " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/c")
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.argument(builder("args", greedyStringParser()).optional())
				.handler(c -> {
					final var sender = c.sender().source();
					this.dispatch(sender, "/copy " + c.getOrDefault("args", ""));
				}));

		commandManager.command(commandManager.commandBuilder("/derot")
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.argument(builder("axis", enumParser(Axis.class)))
				.argument(builder("degrees", integerParser()))
				.handler(c -> {
					final var sender = c.sender().source();
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
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.argument(builder("axis", enumParser(Axis.class)))
				.argument(builder("degrees", integerParser()))
				.handler(c -> {
					final var sender = c.sender().source();
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
				.commandDescription(description("WorldEdit alias."))
				.permission(Permissions.WORLDEDIT_ALIASES)
				.senderType(PlayerSource.class)
				.argument(builder("size", doubleParser()))
				.handler(c -> {
					final var sender = c.sender().source();
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
