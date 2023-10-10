package us.mytheria.blobdesign.entities;

import org.bukkit.util.Transformation;
import us.mytheria.bloblib.entities.BukkitPluginOperator;
import us.mytheria.bloblib.entities.display.DisplayData;

/**
 * Represents an object that can hold Display entities
 * using initial displayData and a transformation.
 */
public interface DisplayOperator extends BukkitPluginOperator {

    /**
     * The initial DisplayData held by this DisplayOperator.
     *
     * @return The initial DisplayData held by this DisplayOperator.
     */
    DisplayData getDisplayData();

    /**
     * The initial transformation held by this DisplayOperator.
     *
     * @return The initial transformation held by this DisplayOperator.
     */
    Transformation getTransformation();

    default DisplayOperator getOperator() {
        return this;
    }
}
