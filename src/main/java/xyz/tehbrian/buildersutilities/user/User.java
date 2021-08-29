package xyz.tehbrian.buildersutilities.user;

import dev.tehbrian.tehlib.paper.user.PaperUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class User extends PaperUser {

    private boolean ironDoorToggleEnabled = true;
    private boolean doubleSlabBreakEnabled = true;
    private boolean glazedTerracottaRotateEnabled = true;

    private boolean nightVisionEnabled = false;
    private boolean noClipEnabled = false;
    private boolean advancedFlyEnabled = false;

    public User(final @NonNull UUID uuid) {
        super(uuid);
    }

    public @Nullable Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public boolean ironDoorToggleEnabled() {
        return this.ironDoorToggleEnabled;
    }

    public void ironDoorToggleEnabled(final boolean ironDoorToggleEnabled) {
        this.ironDoorToggleEnabled = ironDoorToggleEnabled;
    }

    public boolean toggleIronDoorToggleEnabled() {
        this.ironDoorToggleEnabled(!this.ironDoorToggleEnabled());
        return this.ironDoorToggleEnabled();
    }

    public boolean doubleSlabBreakEnabled() {
        return this.doubleSlabBreakEnabled;
    }

    public void doubleSlabBreakEnabled(final boolean doubleSlabBreakEnabled) {
        this.doubleSlabBreakEnabled = doubleSlabBreakEnabled;
    }

    public boolean toggleDoubleSlabBreakEnabled() {
        this.doubleSlabBreakEnabled(!this.doubleSlabBreakEnabled());
        return this.doubleSlabBreakEnabled();
    }

    public boolean glazedTerracottaRotateEnabled() {
        return this.glazedTerracottaRotateEnabled;
    }

    public void glazedTerracottaRotateEnabled(final boolean glazedTerracottaRotateEnabled) {
        this.glazedTerracottaRotateEnabled = glazedTerracottaRotateEnabled;
    }

    public boolean toggleGlazedTerracottaRotateEnabled() {
        this.glazedTerracottaRotateEnabled(!this.glazedTerracottaRotateEnabled());
        return this.glazedTerracottaRotateEnabled();
    }

    public boolean nightVisionEnabled() {
        return this.nightVisionEnabled;
    }

    public void nightVisionEnabled(final boolean nightVisionEnabled) {
        this.nightVisionEnabled = nightVisionEnabled;

        final @Nullable Player player = this.getPlayer();
        if (player == null) {
            return;
        }

        if (nightVisionEnabled) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
        } else {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public boolean toggleNightVisionEnabled() {
        this.nightVisionEnabled(!this.nightVisionEnabled());
        return this.nightVisionEnabled();
    }

    public boolean noClipEnabled() {
        return this.noClipEnabled;
    }

    public void noClipEnabled(final boolean noClipEnabled) {
        this.noClipEnabled = noClipEnabled;
    }

    public boolean toggleNoClipEnabled() {
        this.noClipEnabled(!this.noClipEnabled());
        return this.noClipEnabled();
    }

    public boolean advancedFlyEnabled() {
        return this.advancedFlyEnabled;
    }

    public void advancedFlyEnabled(final boolean advancedFlyEnabled) {
        this.advancedFlyEnabled = advancedFlyEnabled;
    }

    public boolean toggleAdvancedFlyEnabled() {
        this.advancedFlyEnabled(!this.advancedFlyEnabled());
        return this.advancedFlyEnabled();
    }

}
