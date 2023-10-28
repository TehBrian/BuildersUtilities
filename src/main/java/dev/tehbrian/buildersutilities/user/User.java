package dev.tehbrian.buildersutilities.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class User {

  private final UUID uuid;

  private boolean ironDoorToggleEnabled = true;
  private boolean doubleSlabBreakEnabled = true;
  private boolean glazedTerracottaRotateEnabled = true;

  private boolean noclipEnabled = false;
  private boolean advancedFlyEnabled = false;

  public User(final UUID uuid) {
    this.uuid = uuid;
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
    final @Nullable Player player = this.getPlayer();
    if (player == null) {
      return false;
    }

    final @Nullable PotionEffect nightVision = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
    return nightVision != null && nightVision.isInfinite();
  }

  public void nightVisionEnabled(final boolean nightVisionEnabled) {
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

  public boolean noclipEnabled() {
    return this.noclipEnabled;
  }

  public void noclipEnabled(final boolean noclipEnabled) {
    this.noclipEnabled = noclipEnabled;
  }

  public boolean toggleNoclipEnabled() {
    this.noclipEnabled(!this.noclipEnabled());
    return this.noclipEnabled();
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
