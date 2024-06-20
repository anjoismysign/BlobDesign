package us.mytheria.blobdesign.entities;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.display.DisplayOperator;
import us.mytheria.bloblib.entities.display.DisplayOperatorReader;

public class DesignDisplayOperatorReader {

    public static DesignDisplayOperator CONVERT(ConfigurationSection section, String path, JavaPlugin plugin) {
        DisplayOperator displayOperator = DisplayOperatorReader.READ(section, path, plugin);
        return new DesignDisplayOperator() {
            @Override
            public DisplayData getDisplayData() {
                return displayOperator.getDisplayData();
            }

            @Override
            public Transformation getTransformation() {
                return displayOperator.getTransformation();
            }

            @Override
            public JavaPlugin getPlugin() {
                return plugin;
            }
        };
    }
}
