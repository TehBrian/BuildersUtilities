package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.paper.configurate.AbstractLangConfig;
import net.kyori.adventure.text.Component;
import org.spongepowered.configurate.NodePath;

import java.nio.file.Path;
import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public final class LangConfig extends AbstractLangConfig<YamlConfigurateWrapper> {

	@Inject
	public LangConfig(final @Named("dataFolder") Path dataFolder) {
		super(new YamlConfigurateWrapper(dataFolder.resolve("lang.yml")));
	}

	/**
	 * Splits the input string by line and parses each line individually.
	 *
	 * <p>This method is useful for item lore because that requires a list of
	 * components rather than a single component with newlines.</p>
	 *
	 * @param path the config path
	 * @return the component
	 */
	public List<Component> cl(final NodePath path) {
		return this.getAndVerifyString(path).lines()
				.map(miniMessage()::deserialize).toList();
	}

}
