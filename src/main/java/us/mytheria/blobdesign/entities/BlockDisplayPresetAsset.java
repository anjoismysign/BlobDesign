package us.mytheria.blobdesign.entities;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.BlockDisplay;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.DesignManagerDirector;

import java.io.File;
import java.util.logging.Logger;

public class BlockDisplayPresetAsset
        extends AbstractBlockDisplayPreset
        implements DesignDisplayPreset<BlockDisplay> {
    private final String key;
    private final DesignManagerDirector director;

    public BlockDisplayPresetAsset(String key,
                                   DisplayOperator displayOperator,
                                   BlockData blockData,
                                   DesignManagerDirector director) {
        super(blockData, displayOperator);
        this.key = key;
        this.director = director;
    }

    public static BlockDisplayPresetAsset fromFile(File file, BlobDesign plugin) {
        Logger logger = plugin.getLogger();
        String path = file.getPath();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.isString("BlockData")) {
            logger.severe("BlockData is not valid in file " + path);
            return null;
        }
        BlockData blockData = Bukkit.createBlockData(config.getString("BlockData"));
        DisplayOperator displayOperator;
        try {
            displayOperator = DisplayOperatorReader.READ(config, path, plugin);
        } catch (Exception e) {
            logger.severe(e.getMessage() + " in file " + path);
            return null;
        }
        return new BlockDisplayPresetAsset(file.getName().replace(".yml", ""),
                displayOperator, blockData, plugin.getManagerDirector());
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

    @Override
    public DesignManagerDirector getManagerDirector() {
        return director;
    }
}
