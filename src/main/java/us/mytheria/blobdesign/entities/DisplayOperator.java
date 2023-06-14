package us.mytheria.blobdesign.entities;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.bloblib.entities.display.DisplayData;

/**
 * Represents an object that can hold Display entities
 * using initial displayData and a transformation.
 */
public interface DisplayOperator {
    /**
     * The plugin that manages this DisplayOperator.
     *
     * @return The plugin that manages this DisplayOperator.
     */
    JavaPlugin getPlugin();

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
