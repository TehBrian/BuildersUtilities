package xyz.tehbrian.buildersutilities.config;

import dev.tehbrian.tehlib.core.configurate.AbstractRawConfig;
import dev.tehbrian.tehlib.core.configurate.ConfigurateWrapper;
import dev.tehbrian.tehlib.paper.configurate.NoSuchValueAtPathInConfigException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.NodePath;

/**
 * Retrieves {@code String}s from a {@link ConfigurateWrapper}
 * and parses them using {@link MiniMessage}.
 *
 * @param <W> the wrapper type
 */
public abstract class LameAbstractLangConfig<W extends ConfigurateWrapper<?>> extends AbstractRawConfig<W> {

    /**
     * @param configurateWrapper the wrapper
     */
    public LameAbstractLangConfig(
            final @NonNull W configurateWrapper
    ) {
        super(configurateWrapper);
    }

    /**
     * Gets the value for {@code path} from {@link #configurateWrapper}
     * and parses it using {@link MiniMessage}.
     *
     * @param path the config path
     * @return the component
     * @throws NoSuchValueAtPathInConfigException if there is no value found at the specified path
     */
    public Component c(final NodePath path) throws NoSuchValueAtPathInConfigException {
        return MiniMessage.miniMessage().deserialize(this.getAndVerifyString(path));
    }

    /**
     * Gets the value for {@code path} from {@link #configurateWrapper}
     * and verifies that it is not null.
     *
     * @param path the path
     * @return the verified string
     * @throws NoSuchValueAtPathInConfigException if there is no value found at the specified path
     */
    protected String getAndVerifyString(final NodePath path) throws NoSuchValueAtPathInConfigException {
        final String rawValue = this.rootNode().node(path).getString();

        if (rawValue == null) {
            throw new NoSuchValueAtPathInConfigException("Attempted to get value at path " + path + " in config "
                    + configurateWrapper.filePath().getFileName() + " but found nothing.");
        }

        return rawValue;
    }

}
