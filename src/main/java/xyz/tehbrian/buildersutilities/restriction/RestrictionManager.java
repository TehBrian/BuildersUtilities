package xyz.tehbrian.buildersutilities.restriction;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestrictionManager {

    private final List<RestrictionValidator> validators = new ArrayList<>();

    public boolean hasBuildPermission(UUID uuid, Location loc) {
        for (RestrictionValidator validator : validators) {
            if (!validator.validate(uuid, loc)) return false;
        }
        return true;
    }

    public void registerValidator(RestrictionValidator validator) {
        validators.add(validator);
    }

    public void unregisterValidator(RestrictionValidator validator) {
        validators.remove(validator);
    }
}
