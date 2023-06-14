package us.mytheria.blobdesign.entities;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.display.DisplayReader;

public class DisplayOperatorReader {

    public static DisplayOperator READ(ConfigurationSection section, String path, JavaPlugin plugin) {
        DisplayData displayData;
        try {
            displayData = DisplayData.of(section);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + " in file " + path);
        }
        Transformation transformation;
        try {
            transformation = DisplayReader.TRANSFORMATION_FAIL_FAST(section);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage() + " in file " + path);
        }
        return new ImmutableDisplayOperator(plugin, displayData, transformation);
    }
}
