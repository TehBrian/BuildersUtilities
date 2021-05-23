package xyz.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Retrieves {@code String}s from a {@link org.bukkit.configuration.file.FileConfiguration}
 * and parses them using {@link MiniMessage}.
 */
public final class Lang {

    public static final Component EMPTY = MiniMessage.get().parse("<gray>");
    public static final Component RESET = MiniMessage.get().parse("<reset>");

    private final JavaPlugin javaPlugin;
    private final Logger logger;

    @Inject
    public Lang(
            final @NonNull JavaPlugin javaPlugin,
            final @NonNull Logger logger
    ) {
        this.javaPlugin = javaPlugin;
        this.logger = logger;
    }

    /**
     * Gets the value for {@code configPath} in {@link org.bukkit.configuration.file.FileConfiguration},
     * and parses it using {@link MiniMessage}.
     * <p>
     * For each entry in {@code replacements}, any substring in the parsed
     * {@code String} matching the key surrounded with angled brackets, that is
     * to say {@code <key>}, is replaced with the corresponding value.
     *
     * @param configPath   the config path
     * @param replacements the replacements
     * @return the component
     */
    public Component c(final String configPath, final Map<String, String> replacements) {
        return MiniMessage.get().parse(this.getAndVerifyString(configPath), replacements);
    }

    /**
     * Gets the value for {@code configPath} in {@link org.bukkit.configuration.file.FileConfiguration},
     * and parses it using {@link MiniMessage}.
     *
     * @param configPath the config path
     * @return the component
     */
    public Component c(final String configPath) {
        return MiniMessage.get().parse(this.getAndVerifyString(configPath));
    }

    /**
     * Gets the values for {@code configPath} in {@link org.bukkit.configuration.file.FileConfiguration},
     * and parses them using {@link MiniMessage}.
     *
     * @param configPath the config path
     * @return the components
     */
    public List<Component> cl(final String configPath) {
        List<Component> components = new ArrayList<>();

        for (String string : this.getAndVerifyStringList(configPath)) {
            components.add(MiniMessage.get().parse(string));
        }

        return components;
    }

    /**
     * Gets the value for {@code path} in {@link org.bukkit.configuration.file.FileConfiguration},
     * and verifies that it is not null.
     */
    private String getAndVerifyString(final String configPath) {
        String rawValue = this.javaPlugin.getConfig().getString(configPath);

        if (rawValue == null) {
            this.logger.error("Attempted to get value from non-existent config path {}!", configPath);
            throw new IllegalArgumentException("No value found in the config for that given path.");
        }

        return rawValue;
    }

    /**
     * Gets the values for {@code path} in {@link org.bukkit.configuration.file.FileConfiguration},
     * and verifies that they are not null.
     */
    private List<String> getAndVerifyStringList(final String configPath) {
        List<String> rawValues = this.javaPlugin.getConfig().getStringList(configPath);

        if (rawValues.isEmpty()) {
            this.logger.error("Attempted to get list of values from non-existent config path {}!", configPath);
            throw new IllegalArgumentException("No values found in the config for that given path.");
        }

        return rawValues;
    }
}
