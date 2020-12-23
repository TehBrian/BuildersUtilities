package xyz.tehbrian.buildersutilities.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerData getPlayerData(UUID uuid) {
        playerDataMap.computeIfAbsent(uuid, PlayerData::new);
        return playerDataMap.get(uuid);
    }

    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    public Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }
}
