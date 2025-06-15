package io.github.anjoismysign.blobdesign.entities;

import io.github.anjoismysign.blobdesign.BlobDesign;
import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementType;
import io.github.anjoismysign.blobdesign.entities.presetblock.ItemDisplayPresetBlockAsset;
import io.github.anjoismysign.blobdesign.entities.proxy.DesignProxifier;
import io.github.anjoismysign.blobdesign.entities.proxy.ItemDisplayPresetAssetProxy;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.logging.Logger;

public class ItemDisplayPresetAsset
        extends AbstractItemDisplayPreset
        implements DesignDisplayPreset<ItemDisplay> {
    private final String key;
    private final DesignManagerDirector director;
    private final PresetData presetData;

    public ItemDisplayPresetAsset(String key,
                                  DesignDisplayOperator displayOperator,
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
        DesignDisplayOperator displayOperator;
        try {
            displayOperator = DesignDisplayOperatorReader.CONVERT(config, path, plugin);
        } catch (Exception e) {
            logger.severe(e.getMessage() + " in file " + path);
            return null;
        }
        ItemStack itemStack = config.getItemStack("ItemStack");
        ItemDisplay.ItemDisplayTransform transform = ItemDisplay.ItemDisplayTransform.FIXED;
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
    public ItemDisplayPresetBlockAsset instantiatePresetBlock(Location location, String key) {
        ItemDisplayPresetBlockAsset asset;
        try {
            asset = ItemDisplayPresetBlockAsset.of(this, key, location,
                    getManagerDirector());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        director.getPresetBlockAssetDirector().add(asset);
        return asset;
    }

    public void serialize(PersistentDataContainer container) {
        serialize(container, getOperator());
    }

    public @NotNull PresetData getPresetData() {
        return presetData;
    }
}
