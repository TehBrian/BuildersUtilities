package xyz.tehbrian.buildersutilities.managers;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerOptionsManager {

    private static final Set<UUID> disabledTrapdoorToggle = new HashSet<>();
    private static final Set<UUID> disabledSlabBreak = new HashSet<>();
    private static final Set<UUID> disabledTerracottaRotate = new HashSet<>();
    private static final Set<UUID> enabledNightVision = new HashSet<>();
    private static final Set<UUID> enabledNoClip = new HashSet<>();
    private static final Set<UUID> enabledAdvancedFly = new HashSet<>();

    private PlayerOptionsManager() {
    }

    public static boolean getEnabledTrapdoorToggle(Player player) {
        return !disabledTrapdoorToggle.contains(player.getUniqueId());
    }

    public static void setEnabledTrapdoorToggle(Player player, boolean bool) {
        if (bool) {
            disabledTrapdoorToggle.remove(player.getUniqueId());
        } else {
            disabledTrapdoorToggle.add(player.getUniqueId());
        }
    }

    public static boolean toggleTrapdoorToggle(Player player) {
        boolean bool = getEnabledTrapdoorToggle(player);

        setEnabledTrapdoorToggle(player, !bool);
        return !bool;
    }

    public static boolean getEnabledSlabBreak(Player player) {
        return !disabledSlabBreak.contains(player.getUniqueId());
    }

    public static void setEnabledSlabBreak(Player player, boolean bool) {
        if (bool) {
            disabledSlabBreak.remove(player.getUniqueId());
        } else {
            disabledSlabBreak.add(player.getUniqueId());
        }
    }

    public static boolean toggleSlabBreak(Player player) {
        boolean bool = getEnabledSlabBreak(player);

        setEnabledSlabBreak(player, !bool);
        return !bool;
    }

    public static boolean getEnabledTerracottaRotate(Player player) {
        return !disabledTerracottaRotate.contains(player.getUniqueId());
    }

    public static void setEnabledTerracottaRotate(Player player, boolean bool) {
        if (bool) {
            disabledTerracottaRotate.remove(player.getUniqueId());
        } else {
            disabledTerracottaRotate.add(player.getUniqueId());
        }
    }

    public static boolean toggleTerracottaRotate(Player player) {
        boolean bool = getEnabledTerracottaRotate(player);

        setEnabledTerracottaRotate(player, !bool);
        return !bool;
    }

    public static boolean getEnabledNightVision(Player player) {
        return enabledNightVision.contains(player.getUniqueId());
    }

    public static void setEnabledNightVision(Player player, boolean bool) {
        if (bool) {
            enabledNightVision.add(player.getUniqueId());
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
        } else {
            enabledNightVision.remove(player.getUniqueId());
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public static boolean toggleNightVision(Player player) {
        boolean bool = getEnabledNightVision(player);

        setEnabledNightVision(player, !bool);
        return !bool;
    }

    public static boolean getEnabledNoClip(Player player) {
        return enabledNoClip.contains(player.getUniqueId());
    }

    public static Set<UUID> getEnabledNoClipList() {
        return enabledNoClip;
    }

    public static void setEnabledNoClip(Player player, boolean bool) {
        if (bool) {
            enabledNoClip.add(player.getUniqueId());
        } else {
            enabledNoClip.remove(player.getUniqueId());

            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.setGameMode(GameMode.CREATIVE);
            }
        }
    }

    public static boolean toggleNoClip(Player player) {
        boolean bool = getEnabledNoClip(player);

        setEnabledNoClip(player, !bool);
        return !bool;
    }

    public static boolean getEnabledAdvancedFly(Player player) {
        return enabledAdvancedFly.contains(player.getUniqueId());
    }

    public static void setEnabledAdvancedFly(Player player, boolean bool) {
        if (bool) {
            enabledAdvancedFly.add(player.getUniqueId());
        } else {
            enabledAdvancedFly.remove(player.getUniqueId());
        }
    }

    public static boolean toggleAdvancedFly(Player player) {
        boolean bool = getEnabledAdvancedFly(player);

        setEnabledAdvancedFly(player, !bool);
        return !bool;
    }
}
