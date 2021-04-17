package xyz.tehbrian.buildersutilities.util;

import net.md_5.bungee.api.ChatColor;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* TODO Use adventure. */
public final class MessageUtils {

    public static final Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");

    private MessageUtils() {
    }

    public static String color(final String string) {
        return string == null ? null : replaceHex(ChatColor.translateAlternateColorCodes('&', string));
    }

    public static String replaceHex(String str) {
        Matcher matcher = HEX_PATTERN.matcher(str);
        while (matcher.find()) {
            str = str.replace(matcher.group(0), ChatColor.of(matcher.group(1)).toString());
        }
        return str;
    }

    public static String getMessage(final String configKey) {
        return color(BuildersUtilities.getInstance().getConfig().getString(configKey));
    }

    public static List<String> getMessageList(final String configKey) {
        List<String> messages = BuildersUtilities.getInstance().getConfig().getStringList(configKey);
        messages.replaceAll(MessageUtils::color);
        return messages;
    }
}
