package xyz.tehbrian.buildersutilities.util;

import xyz.tehbrian.buildersutilities.BuildersUtilities;

// TODO Phase this out entirely.
public final class ConfigUtils {

    private ConfigUtils() {
    }

    public static String getString(final String configKey) {
        return BuildersUtilities.getInstance().getConfig().getString(configKey);
    }

}
