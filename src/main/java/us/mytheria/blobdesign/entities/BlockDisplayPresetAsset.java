package us.mytheria.blobdesign.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.blobdesign.entities.presetblock.BlockDisplayPresetBlockAsset;
import us.mytheria.blobdesign.entities.proxy.BlockDisplayPresetAssetProxy;
import us.mytheria.blobdesign.entities.proxy.DesignProxifier;

import java.io.File;
import java.util.logging.Logger;

public class BlockDisplayPresetAsset
        extends AbstractBlockDisplayPreset
        implements DesignDisplayPreset<BlockDisplay> {
    private final String key;
    private final DesignManagerDirector director;
    private final PresetData presetData;

    public BlockDisplayPresetAsset(String key,
                                   DesignDisplayOperator displayOperator,
                                   BlockData blockData,
                                   DesignManagerDirector director) {
        super(blockData, displayOperator);
        this.key = key;
        this.director = director;
        this.presetData = new PresetData(DisplayElementType.BLOCK_DISPLAY, key);
    }

    public static BlockDisplayPresetAssetProxy fromFile(File file,
                                                        DesignManagerDirector director) {
        BlobDesign plugin = director.getPlugin();
        Logger logger = plugin.getLogger();
        String path = file.getPath();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.isString("BlockData")) {
            logger.severe("BlockData is not valid in file " + path);
            return null;
        }
        BlockData blockData = Bukkit.createBlockData(config.getString("BlockData"));
        DesignDisplayOperator displayOperator;
        try {
            displayOperator = DesignDisplayOperatorReader.CONVERT(config, path, plugin);
        } catch (Exception e) {
            logger.severe(e.getMessage() + " in file " + path);
            return null;
        }
        return DesignProxifier.PROXY(new BlockDisplayPresetAsset(file.getName().replace(".yml", ""),
                displayOperator, blockData, director));
    }

    public String getKey() {
        return key;
    }

    public File saveToFile(File directory) {
        File file = instanceFile(directory);
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.set("BlockData", getBlockData().getAsString(true));
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
    public BlockDisplayPresetBlockAsset instantiatePresetBlock(Location location, String key) {
        BlockDisplayPresetBlockAsset asset;
        try {
            asset = BlockDisplayPresetBlockAsset.of(this, key, location,
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
