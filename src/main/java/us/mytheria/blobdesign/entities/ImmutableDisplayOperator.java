package us.mytheria.blobdesign.entities;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.bloblib.entities.display.DisplayData;

public record ImmutableDisplayOperator(JavaPlugin plugin,
                                       DisplayData displayData,
                                       Transformation transformation) implements DisplayOperator {

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
