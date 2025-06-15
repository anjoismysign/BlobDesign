package io.github.anjoismysign.blobdesign.entities;

import io.github.anjoismysign.bloblib.entities.display.DisplayData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;

public record ImmutableDisplayOperator(JavaPlugin plugin,
                                       DisplayData displayData,
                                       Transformation transformation) implements DesignDisplayOperator {

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public DisplayData getDisplayData() {
        return displayData;
    }

    public Transformation getTransformation() {
        return transformation;
    }
}
