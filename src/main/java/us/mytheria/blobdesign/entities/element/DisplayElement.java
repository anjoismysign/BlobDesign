package us.mytheria.blobdesign.entities.element;

import org.bukkit.entity.Display;
import us.mytheria.blobdesign.entities.DisplayPreset;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public interface DisplayElement<T extends Display> {

    /**
     * Will despawn the entity (as the Minecraft entity that's
     * inside the Minecraft World) associated with this asset.
     */
    default void despawn() {
        DisplayDecorator<T> decorator = getDecorator();
        decorator.stopClock();
        decorator.call().remove();
    }

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
    DisplayPreset<T> getDisplayPreset();

    /**
     * Will get the DisplayElement.
     *
     * @return The DisplayElement.
     */
    default DisplayElement<T> getDisplayElement() {
        return this;
    }
}
