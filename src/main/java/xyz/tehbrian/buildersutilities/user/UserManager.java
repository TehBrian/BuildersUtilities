package xyz.tehbrian.buildersutilities.user;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class UserManager {

    private final Map<UUID, User> userMap = new HashMap<>();

    public User getUser(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, User::new);
        return this.userMap.get(uuid);
    }

    public User getUser(final Player player) {
        return this.getUser(player.getUniqueId());
    }

    public Map<UUID, User> getUserMap() {
        return this.userMap;
    }

}
