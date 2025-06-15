package io.github.anjoismysign.blobdesign.entities.element;

import io.github.anjoismysign.blobdesign.entities.DesignDisplayOperator;
import io.github.anjoismysign.blobdesign.entities.DisplayPreset;
import io.github.anjoismysign.bloblib.entities.display.DisplayData;
import io.github.anjoismysign.bloblib.entities.display.DisplayDecorator;
import org.bukkit.entity.Display;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public class BlobDisplayElement<T extends Display> implements DisplayElement<T>, DesignDisplayOperator {
    private final DisplayDecorator<T> decorator;
    private final DisplayElementType type;
    private final DisplayPreset<T> preset;

    /**
     * Represents a DisplayElement that was created from a DisplayPreset.
     *
     * @param decorator The decorator it holds.
     * @param type      The type of the element.
     * @param preset    The preset that was used to create this element.
     */
    public BlobDisplayElement(DisplayDecorator<T> decorator, DisplayElementType type, DisplayPreset<T> preset) {
        this.decorator = decorator;
        this.type = type;
        this.preset = preset;
    }

    public @NotNull DisplayDecorator<T> getDecorator() {
        return decorator;
    }

    public @NotNull DisplayElementType getType() {
        return type;
    }

    /**
     * Will get the preset that was used to create this element.
     *
     * @return The preset that was used to create this element.
     */
    public @NotNull DisplayPreset<T> getDisplayPreset() {
        return preset;
    }

    /**
     * Will get the Plugin that created this DisplayElement.
     *
     * @return The Plugin that created this DisplayElement.
     */
    public Plugin getPlugin() {
        return preset.getPlugin();
    }

    /**
     * Will get the initial DisplayData held by this DisplayElement.
     *
     * @return The DisplayData held by this DisplayElement.
     */
    @Override
    public DisplayData getDisplayData() {
        return preset.getDisplayData();
    }

    /**
     * Will get the initial Transformation held by this DisplayElement.
     *
     * @return The Transformation held by this DisplayElement.
     */
    @Override
    public Transformation getTransformation() {
        return preset.getTransformation();
    }
}