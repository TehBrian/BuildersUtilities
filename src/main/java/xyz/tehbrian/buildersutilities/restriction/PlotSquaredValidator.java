package xyz.tehbrian.buildersutilities.restriction;

import com.plotsquared.core.plot.Plot;
import xyz.tehbrian.buildersutilities.BuildersUtilities;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class PlotSquaredValidator implements RestrictionValidator {

    @Override
    public boolean validate(UUID uuid, org.bukkit.Location bukkitLoc) {
        Logger logger = BuildersUtilities.getInstance().getLogger();

        Objects.requireNonNull(bukkitLoc);

        com.plotsquared.core.location.Location psLoc = new com.plotsquared.core.location.Location(
                bukkitLoc.getWorld().getName(),
                bukkitLoc.getBlockX(),
                bukkitLoc.getBlockY(),
                bukkitLoc.getBlockZ());

        if (psLoc.isPlotArea() || psLoc.isPlotRoad()) {
            // Location is in a plot area.

            // TODO If the player isn't in a proper plot (ie. the road) then plot will be null.
            // We need to figure out another method
            // of checking whether a player can build,
            // so that players who have proper permissions
            // will still be validated in roads.
            Plot plot = psLoc.getPlot();

            if (plot == null) {
                logger.finest("PS Validator - FALSE - Plot is null.");
                return false;
            }

            if (plot.isAdded(uuid)) {
                logger.finest("PS Validator - TRUE - Player is added to plot.");
                return true;
            } else {
                logger.finest("PS Validator - FALSE - Player is not added to plot.");
                return false;
            }
        } else {
            // Location is not in a plot area.
            logger.finest("PS Validator - TRUE - Location isn't PlotArea or PlotRoad.");
            return true;
        }
    }
}
