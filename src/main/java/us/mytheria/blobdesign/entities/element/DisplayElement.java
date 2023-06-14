package us.mytheria.blobdesign.entities.element;

import org.bukkit.entity.Display;
import us.mytheria.blobdesign.entities.DisplayOperator;
import us.mytheria.blobdesign.entities.DisplayPreset;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public interface DisplayElement<T extends Display> extends DisplayOperator {

    /**
     * Will get the decorator that holds the Display entity.
     *
     * @return The decorator that holds the Display entity.
     */
    DisplayDecorator<T> getDecorator();

    /**
     * Will get the type of DisplayElement.
     *
     * @return The type of DisplayElement.
     */
    DisplayElementType getType();

    /**
     * Will get the preset that was used to create this element.
     *
     * @return The preset that was used to create this element.
     */
    DisplayPreset<T> getPreset();
}
