package xyz.tehbrian.buildersutilities.util;

import org.bukkit.ChatColor;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.List;

public class MessageUtils {

    private MessageUtils() {
    }

    public static String color(String string) {
        return string == null ? null : ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String getMessage(String configKey) {
        return color(BuildersUtilities.getInstance().getConfig().getString(configKey));
    }

    public static List<String> getMessageList(String configKey) {
        List<String> messages = BuildersUtilities.getInstance().getConfig().getStringList(configKey);
        messages.replaceAll(MessageUtils::color);
        return messages;
    }
}
