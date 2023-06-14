package us.mytheria.blobdesign.entities;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import us.mytheria.bloblib.entities.BlobObject;

import java.io.File;
import java.util.logging.Logger;

public class ItemDisplayPresetAsset
        extends ItemDisplayPreset
        implements BlobObject {
    private final String key;

    public ItemDisplayPresetAsset(String key,
                                  DisplayOperator displayOperator,
                                  ItemStack itemStack,
                                  ItemDisplay.ItemDisplayTransform transform) {
        super(itemStack, transform, displayOperator);
        this.key = key;
    }

    public static ItemDisplayPresetAsset fromFile(File file, JavaPlugin plugin) {
        Logger logger = plugin.getLogger();
        String path = file.getPath();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.isItemStack("ItemStack")) {
            logger.severe("ItemStack is not valid in file " + path);
            return null;
        }
        DisplayOperator displayOperator;
        try {
            displayOperator = DisplayOperatorReader.READ(config, path, plugin);
        } catch (Exception e) {
            logger.severe(e.getMessage() + " in file " + path);
            return null;
        }
        ItemStack itemStack = config.getItemStack("ItemStack");
        ItemDisplay.ItemDisplayTransform transform = ItemDisplay.ItemDisplayTransform.NONE;
        if (config.isString("Transform"))
            transform = ItemDisplay.ItemDisplayTransform.valueOf(config.getString("Transform"));

        return new ItemDisplayPresetAsset(file.getName().replace(".yml", ""),
                displayOperator, itemStack, transform);
    }

    public String getKey() {
        return key;
    }

    public File saveToFile(File directory) {
        File file = instanceFile(directory);
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.set("ItemStack", getItemStack());
        yamlConfiguration.set("Transform", getTransform().name());
        writePreset(yamlConfiguration);
        try {
            yamlConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
