package us.mytheria.blobdesign.entities;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.DesignManagerDirector;

import java.io.File;
import java.util.logging.Logger;

public class ItemDisplayPresetAsset
        extends AbstractItemDisplayPreset
        implements DesignDisplayPreset<ItemDisplay> {
    private final String key;
    private final DesignManagerDirector designManagerDirector;

    public ItemDisplayPresetAsset(String key,
                                  DisplayOperator displayOperator,
                                  ItemStack itemStack,
                                  ItemDisplay.ItemDisplayTransform transform,
                                  DesignManagerDirector designManagerDirector) {
        super(itemStack, transform, displayOperator);
        this.key = key;
        this.designManagerDirector = designManagerDirector;
    }

    public static ItemDisplayPresetAsset fromFile(File file, DesignManagerDirector director) {
        BlobDesign plugin = director.getPlugin();
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
                displayOperator, itemStack, transform, director);
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

    @Override
    public DesignManagerDirector getManagerDirector() {
        return designManagerDirector;
    }
}
