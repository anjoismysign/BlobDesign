package us.mytheria.blobdesign.entities;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import us.mytheria.bloblib.entities.BlobObject;

import java.io.File;
import java.util.logging.Logger;

public class BlockDisplayPresetAsset
        extends BlockDisplayPreset
        implements BlobObject {

    private final String key;

    public BlockDisplayPresetAsset(String key,
                                   DisplayOperator displayOperator,
                                   BlockData blockData) {
        super(blockData, displayOperator);
        this.key = key;
    }

    public static BlockDisplayPresetAsset fromFile(File file, JavaPlugin plugin) {
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
                displayOperator, blockData);
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
}
