package us.mytheria.blobdesign.entities.element;

import org.bukkit.entity.Display;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.blobdesign.entities.DisplayPreset;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public class BlobDisplayElement<T extends Display> implements DisplayElement<T> {
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

    public DisplayDecorator<T> getDecorator() {
        return decorator;
    }

    public DisplayElementType getType() {
        return type;
    }

    /**
     * Will get the preset that was used to create this element.
     *
     * @return The preset that was used to create this element.
     */
    public DisplayPreset<T> getPreset() {
        return preset;
    }

    /**
     * Will get the JavaPlugin that created this DisplayElement.
     *
     * @return The JavaPlugin that created this DisplayElement.
     */
    public JavaPlugin getPlugin() {
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