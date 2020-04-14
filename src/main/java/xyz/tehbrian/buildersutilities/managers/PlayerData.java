package xyz.tehbrian.buildersutilities.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;

    private boolean ironTrapdoorToggleEnabled = true;
    private boolean doubleSlabBreakEnabled = true;
    private boolean glazedTerracottaRotateEnabled = true;

    private boolean nightVisionEnabled = false;
    private boolean noClipEnabled = false;
    private boolean advancedFlyEnabled = false;

    public PlayerData(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean hasIronTrapdoorToggleEnabled() {
        return ironTrapdoorToggleEnabled;
    }

    public void setIronTrapdoorToggleEnabled(boolean ironTrapdoorToggleEnabled) {
        this.ironTrapdoorToggleEnabled = ironTrapdoorToggleEnabled;
    }

    public boolean toggleIronTrapdoorToggleEnabled() {
        setIronTrapdoorToggleEnabled(!hasIronTrapdoorToggleEnabled());
        return hasIronTrapdoorToggleEnabled();
    }

    public boolean hasDoubleSlabBreakEnabled() {
        return doubleSlabBreakEnabled;
    }

    public void setDoubleSlabBreakEnabled(boolean doubleSlabBreakEnabled) {
        this.doubleSlabBreakEnabled = doubleSlabBreakEnabled;
    }

    public boolean toggleDoubleSlabBreakEnabled() {
        setDoubleSlabBreakEnabled(!hasDoubleSlabBreakEnabled());
        return hasDoubleSlabBreakEnabled();
    }

    public boolean hasGlazedTerracottaRotateEnabled() {
        return glazedTerracottaRotateEnabled;
    }

    public void setGlazedTerracottaRotateEnabled(boolean glazedTerracottaRotateEnabled) {
        this.glazedTerracottaRotateEnabled = glazedTerracottaRotateEnabled;
    }

    public boolean toggleGlazedTerracottaRotateEnabled() {
        setGlazedTerracottaRotateEnabled(!hasGlazedTerracottaRotateEnabled());
        return hasGlazedTerracottaRotateEnabled();
    }

    public boolean hasNightVisionEnabled() {
        return nightVisionEnabled;
    }

    public void setNightVisionEnabled(boolean nightVisionEnabled) {
        this.nightVisionEnabled = nightVisionEnabled;

        Player player = getPlayer();
        if (nightVisionEnabled) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
        } else {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public boolean toggleNightVisionEnabled() {
        setNightVisionEnabled(!hasNightVisionEnabled());
        return hasNightVisionEnabled();
    }

    public boolean hasNoClipEnabled() {
        return noClipEnabled;
    }

    public void setNoClipEnabled(boolean noClipEnabled) {
        this.noClipEnabled = noClipEnabled;
    }

    public boolean toggleNoClipEnabled() {
        setNoClipEnabled(!hasNoClipEnabled());
        return hasNoClipEnabled();
    }

    public boolean hasAdvancedFlyEnabled() {
        return advancedFlyEnabled;
    }

    public void setAdvancedFlyEnabled(boolean advancedFlyEnabled) {
        this.advancedFlyEnabled = advancedFlyEnabled;
    }

    public boolean toggleAdvancedFlyEnabled() {
        setAdvancedFlyEnabled(!hasAdvancedFlyEnabled());
        return hasAdvancedFlyEnabled();
    }
}
