package xyz.tehbrian.buildersutilities.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerDataManager {

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerData getPlayerData(final UUID uuid) {
        this.playerDataMap.computeIfAbsent(uuid, PlayerData::new);
        return this.playerDataMap.get(uuid);
    }

    public PlayerData getPlayerData(final Player player) {
        return this.getPlayerData(player.getUniqueId());
    }

    public Map<UUID, PlayerData> getPlayerDataMap() {
        return this.playerDataMap;
    }
}
