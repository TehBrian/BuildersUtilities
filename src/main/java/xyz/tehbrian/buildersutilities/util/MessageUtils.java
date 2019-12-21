package xyz.tehbrian.buildersutilities.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.List;

public class MessageUtils {

    private MessageUtils() {
    }

    public static String color(String string) {
        return string == null ? null : ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String getMessage(String configKey) {
        BuildersUtilities main = BuildersUtilities.getInstance();
        FileConfiguration config = main.getConfig();

        return color(config.getString(configKey));
    }

    public static List<String> getMessageList(String configKey) {
        BuildersUtilities main = BuildersUtilities.getInstance();
        FileConfiguration config = main.getConfig();

        List<String> messages = config.getStringList(configKey);
        messages.replaceAll(MessageUtils::color);
        return messages;
    }
}
