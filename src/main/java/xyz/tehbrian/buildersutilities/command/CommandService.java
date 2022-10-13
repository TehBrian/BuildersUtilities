package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudService;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.function.Function;

public class CommandService extends PaperCloudService<CommandSender> {

  private final BuildersUtilities buildersUtilities;

  @Inject
  public CommandService(
      final BuildersUtilities buildersUtilities
  ) {
    this.buildersUtilities = buildersUtilities;
  }

  /**
   * Instantiates {@link #commandManager}.
   *
   * @throws IllegalStateException if {@link #commandManager} is already instantiated
   * @throws Exception             if something goes wrong during instantiation
   */
  public void init() throws Exception {
    if (this.commandManager != null) {
      throw new IllegalStateException("The CommandManager is already instantiated.");
    }

    this.commandManager = new PaperCommandManager<>(
        this.buildersUtilities,
        CommandExecutionCoordinator.simpleCoordinator(),
        Function.identity(),
        Function.identity()
    );
  }

}
