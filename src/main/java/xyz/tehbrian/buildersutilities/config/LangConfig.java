package xyz.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.paper.configurate.AbstractLangConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.file.Path;

public class LangConfig extends AbstractLangConfig<YamlConfigurateWrapper> {

    public static final Component EMPTY = MiniMessage.miniMessage().parse("<gray>");

    /**
     * @param dataFolder the data folder
     * @param logger     the logger
     */
    @Inject
    public LangConfig(final @NonNull @Named("dataFolder") Path dataFolder, final @NonNull Logger logger) {
        super(new YamlConfigurateWrapper(dataFolder.resolve("lang.yml")), logger);
    }

}
