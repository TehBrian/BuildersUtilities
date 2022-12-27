package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.tehlib.paper.configurate.AbstractLangConfig;
import dev.tehbrian.tehlib.paper.configurate.NoSuchValueAtPathInConfigException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.configurate.NodePath;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class LangConfig extends AbstractLangConfig<YamlConfigurateWrapper> {

  private static MiniMessage legacyMiniMessage;

  /**
   * @param dataFolder the data folder
   */
  @Inject
  public LangConfig(final @Named("dataFolder") Path dataFolder) {
    super(new YamlConfigurateWrapper(dataFolder.resolve("lang.yml")));
  }

  private static MiniMessage legacyMiniMessage() {
    if (legacyMiniMessage != null) {
      return legacyMiniMessage;
    }

    final HashMap<String, StyleBuilderApplicable> legacyColorCodes = new HashMap<>();
    legacyColorCodes.put("0", NamedTextColor.BLACK);
    legacyColorCodes.put("1", NamedTextColor.DARK_BLUE);
    legacyColorCodes.put("2", NamedTextColor.DARK_GREEN);
    legacyColorCodes.put("3", NamedTextColor.DARK_AQUA);
    legacyColorCodes.put("4", NamedTextColor.DARK_RED);
    legacyColorCodes.put("5", NamedTextColor.DARK_PURPLE);
    legacyColorCodes.put("6", NamedTextColor.GOLD);
    legacyColorCodes.put("7", NamedTextColor.GRAY);
    legacyColorCodes.put("8", NamedTextColor.DARK_GRAY);
    legacyColorCodes.put("9", NamedTextColor.BLUE);
    legacyColorCodes.put("a", NamedTextColor.GREEN);
    legacyColorCodes.put("b", NamedTextColor.AQUA);
    legacyColorCodes.put("c", NamedTextColor.RED);
    legacyColorCodes.put("d", NamedTextColor.LIGHT_PURPLE);
    legacyColorCodes.put("e", NamedTextColor.YELLOW);
    legacyColorCodes.put("f", NamedTextColor.WHITE);

    final List<TagResolver> legacyResolvers = new ArrayList<>();
    for (final var color : legacyColorCodes.entrySet()) {
      legacyResolvers.add(TagResolver.resolver(color.getKey(), Tag.styling(color.getValue())));
    }

    legacyMiniMessage = MiniMessage.builder().editTags(t -> t.resolvers(legacyResolvers)).build();
    return legacyMiniMessage;
  }

  @Override
  public Component c(final NodePath path, final TagResolver tagResolver) throws NoSuchValueAtPathInConfigException {
    return legacyMiniMessage().deserialize(this.getAndVerifyString(path), tagResolver);
  }

  /**
   * Splits the input string by line and parses each line individually.
   * Since the lore of an ItemStack requires a list of components rather than
   * components with newlines, this method is useful for that, but it shouldn't
   * really need to be used for anything else.
   *
   * @param path the config path
   * @return the component
   */
  public List<Component> cl(final NodePath path) {
    final List<String> toParse = this.getAndVerifyString(path).lines().toList();
    final List<Component> parsed = new ArrayList<>();
    for (final String string : toParse) {
      parsed.add(legacyMiniMessage().deserialize(string));
    }
    return parsed;
  }

}
