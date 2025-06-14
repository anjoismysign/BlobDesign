package io.github.anjoismysign.blobdesign.entities.element;

import io.github.anjoismysign.blobdesign.entities.DisplayPreset;
import io.github.anjoismysign.bloblib.entities.display.DisplayDecorator;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    DisplayDecorator<T> getDecorator();

    /**
     * Will get the type of DisplayElement.
     *
     * @return The type of DisplayElement.
     */
    @NotNull
    DisplayElementType getType();

    /**
     * Will get the preset that was used to create this element.
     *
     * @return The preset that was used to create this element.
     */
    @NotNull
    DisplayPreset<T> getDisplayPreset();

    /**
     * Will get the DisplayElement.
     *
     * @return The DisplayElement.
     */
    @NotNull
    default DisplayElement<T> getDisplayElement() {
        return this;
    }
}
