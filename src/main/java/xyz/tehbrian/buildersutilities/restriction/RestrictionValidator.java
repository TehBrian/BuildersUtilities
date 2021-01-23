package xyz.tehbrian.buildersutilities.restriction;

import org.bukkit.Location;

import java.util.UUID;

public interface RestrictionValidator {

    /**
     * Ensures that the {@link org.bukkit.entity.Player} associated with {@code uuid} has
     * sufficient permission to modify the world at {@code loc}.
     *
     * @param uuid the player's uuid
     * @param loc  the location to check
     * @return true if the player has permission to modify that location, false if not
     */
    boolean validate(UUID uuid, Location loc);
}
