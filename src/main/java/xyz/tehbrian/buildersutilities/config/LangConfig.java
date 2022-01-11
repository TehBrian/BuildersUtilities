package xyz.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.paper.configurate.AbstractLangConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *
     *
     * Splits the input string by line and parses each line individually.
     * Since the lore of an ItemStack requires a list of components rather than
     * components with newlines, this method is useful for that, but it shouldn't
     * really need to be used for anything else.
     *
     * @param path the config path
     * @return the component
     */
    public @NonNull List<@NonNull Component> cl(final NodePath path) {
        final List<String> toParse = this.getAndVerifyString(path).lines().toList();
        final List<Component> parsed = new ArrayList<>();
        for (final String string : toParse) {
            parsed.add(MiniMessage.miniMessage().deserialize(string));
        }
        return parsed;
    }

}
