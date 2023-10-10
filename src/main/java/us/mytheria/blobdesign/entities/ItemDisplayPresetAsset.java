package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.blockasset.ItemDisplayPresetBlockAsset;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.blobdesign.entities.proxy.DesignProxifier;
import us.mytheria.blobdesign.entities.proxy.ItemDisplayPresetAssetProxy;

import java.io.File;
import java.util.logging.Logger;

public class ItemDisplayPresetAsset
        extends AbstractItemDisplayPreset
        implements DesignDisplayPreset<ItemDisplay> {
    private final String key;
    private final DesignManagerDirector director;
    private final PresetData presetData;

    public ItemDisplayPresetAsset(String key,
                                  DisplayOperator displayOperator,
                                  ItemStack itemStack,
                                  ItemDisplay.ItemDisplayTransform transform,
                                  DesignManagerDirector director) {
        super(itemStack, transform, displayOperator);
        this.key = key;
        this.director = director;
        presetData = new PresetData(DisplayElementType.ITEM_DISPLAY, key);
    }

    public static ItemDisplayPresetAssetProxy fromFile(File file, DesignManagerDirector director) {
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
        return DesignProxifier.PROXY(new ItemDisplayPresetAsset(file.getName().replace(".yml", ""),
                displayOperator, itemStack, transform, director));
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

    public DesignManagerDirector getManagerDirector() {
        return director;
    }

    @Override
    @NotNull
    public ItemDisplayPresetBlockAsset instantiateBlockAsset(Location location, String key) {
        ItemDisplayPresetBlockAsset asset;
        try {
            asset = ItemDisplayPresetBlockAsset.of(this, key, location,
                    getManagerDirector());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        director.getPresetBlockAssetDirector().add(asset, key);
        return asset;
    }

    public void serialize(PersistentDataContainer container) {
        serialize(container, getOperator());
    }

    public @NotNull PresetData getPresetData() {
        return presetData;
    }
}
