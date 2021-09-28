package xyz.tehbrian.buildersutilities.command;

import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.tehbrian.tehlib.paper.cloud.PaperCloudService;
import org.apache.logging.log4j.Logger;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.function.Function;

public class CommandService extends PaperCloudService<CommandSender> {

    private final BuildersUtilities buildersUtilities;
    private final Logger logger;

    /**
     * @param buildersUtilities injected
     * @param logger            injected
     */
    @Inject
    public CommandService(
            final @NonNull BuildersUtilities buildersUtilities,
            final @NonNull Logger logger
    ) {
        this.buildersUtilities = buildersUtilities;
        this.logger = logger;
    }

    /**
     * Instantiates {@link #commandManager}.
     *
     * @throws IllegalStateException if {@link #commandManager} is already instantiated
     */
    public void init() throws IllegalStateException {
        if (this.commandManager != null) {
            throw new IllegalStateException("The CommandManager is already instantiated.");
        }

        try {
            this.commandManager = new PaperCommandManager<>(
                    this.buildersUtilities,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(),
                    Function.identity()
            );
        } catch (final Exception e) {
            this.logger.error("Failed to create the CommandManager.");
            this.logger.error("Disabling plugin.");
            this.logger.error("Printing stack trace, please send this to the developers:", e);
            this.buildersUtilities.disableSelf();
        }
    }

}
