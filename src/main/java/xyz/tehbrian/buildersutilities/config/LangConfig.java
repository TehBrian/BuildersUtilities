package xyz.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.paper.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class LangConfig extends Lang<YamlConfigurateWrapper> {

    public static final Component EMPTY = MiniMessage.get().parse("<gray>");
    public static final Component RESET = MiniMessage.get().parse("<reset>");

    /**
     * @param logger     the logger
     * @param dataFolder the data folder
     */
    @Inject
    public LangConfig(
            final @NotNull Logger logger,
            final @NotNull @Named("dataFolder") Path dataFolder
    ) {
        super(logger, new YamlConfigurateWrapper(logger, dataFolder.resolve("lang.yml")));
    }

}
