package xyz.tehbrian.buildersutilities.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public final class User {

    private final UUID uuid;

    private boolean ironDoorToggleEnabled = true;
    private boolean doubleSlabBreakEnabled = true;
    private boolean glazedTerracottaRotateEnabled = true;

    private boolean nightVisionEnabled = false;
    private boolean noClipEnabled = false;
    private boolean advancedFlyEnabled = false;

    public User(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public boolean hasIronDoorToggleEnabled() {
        return this.ironDoorToggleEnabled;
    }

    public void setIronDoorToggleEnabled(final boolean ironDoorToggleEnabled) {
        this.ironDoorToggleEnabled = ironDoorToggleEnabled;
    }

    public boolean toggleIronDoorToggleEnabled() {
        this.setIronDoorToggleEnabled(!this.hasIronDoorToggleEnabled());
        return this.hasIronDoorToggleEnabled();
    }

    public boolean hasDoubleSlabBreakEnabled() {
        return this.doubleSlabBreakEnabled;
    }

    public void setDoubleSlabBreakEnabled(final boolean doubleSlabBreakEnabled) {
        this.doubleSlabBreakEnabled = doubleSlabBreakEnabled;
    }

    public boolean toggleDoubleSlabBreakEnabled() {
        this.setDoubleSlabBreakEnabled(!this.hasDoubleSlabBreakEnabled());
        return this.hasDoubleSlabBreakEnabled();
    }

    public boolean hasGlazedTerracottaRotateEnabled() {
        return this.glazedTerracottaRotateEnabled;
    }

    public void setGlazedTerracottaRotateEnabled(final boolean glazedTerracottaRotateEnabled) {
        this.glazedTerracottaRotateEnabled = glazedTerracottaRotateEnabled;
    }

    public boolean toggleGlazedTerracottaRotateEnabled() {
        this.setGlazedTerracottaRotateEnabled(!this.hasGlazedTerracottaRotateEnabled());
        return this.hasGlazedTerracottaRotateEnabled();
    }

    public boolean hasNightVisionEnabled() {
        return this.nightVisionEnabled;
    }

    public void setNightVisionEnabled(final boolean nightVisionEnabled) {
        this.nightVisionEnabled = nightVisionEnabled;

        Player player = this.getPlayer();
        if (nightVisionEnabled) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
        } else {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public boolean toggleNightVisionEnabled() {
        this.setNightVisionEnabled(!this.hasNightVisionEnabled());
        return this.hasNightVisionEnabled();
    }

    public boolean hasNoClipEnabled() {
        return this.noClipEnabled;
    }

    public void setNoClipEnabled(final boolean noClipEnabled) {
        this.noClipEnabled = noClipEnabled;
    }

    public boolean toggleNoClipEnabled() {
        this.setNoClipEnabled(!this.hasNoClipEnabled());
        return this.hasNoClipEnabled();
    }

    public boolean hasAdvancedFlyEnabled() {
        return this.advancedFlyEnabled;
    }

    public void setAdvancedFlyEnabled(final boolean advancedFlyEnabled) {
        this.advancedFlyEnabled = advancedFlyEnabled;
    }

    public boolean toggleAdvancedFlyEnabled() {
        this.setAdvancedFlyEnabled(!this.hasAdvancedFlyEnabled());
        return this.hasAdvancedFlyEnabled();
    }

}
