package xyz.tehbrian.buildersutilities.restriction;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class WorldGuardValidator implements RestrictionValidator {

    @Override
    public boolean validate(UUID uuid, org.bukkit.Location bukkitLoc) {
        Logger logger = BuildersUtilities.getInstance().getLogger();

        Objects.requireNonNull(bukkitLoc);

        // Convert Bukkit objects to WorldEdit's proprietary objects
        com.sk89q.worldedit.util.Location weLoc = BukkitAdapter.adapt(bukkitLoc);
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(uuid));

        // WorldGuard API instance
        WorldGuard worldGuard = WorldGuard.getInstance();

        // Player has permission to bypass
        if (worldGuard.getPlatform().getSessionManager().hasBypass(localPlayer, localPlayer.getWorld())) {
            logger.finest("WG Validator - TRUE - Player has bypass.");
            return true;
        }

        // Check BUILD flag using query cache
        // Shamelessly copied from https://worldguard.enginehub.org/en/latest/developer/regions/protection-query/
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        if (!query.testState(weLoc, localPlayer, Flags.BUILD)) {
            logger.finest("WG Validator - FALSE - Player does not have BUILD flag.");
            return false;
        }

        logger.finest("WG Validator - TRUE - Default return value.");
        return true;
    }
}
