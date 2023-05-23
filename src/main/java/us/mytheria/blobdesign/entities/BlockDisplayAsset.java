package us.mytheria.blobdesign.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import us.mytheria.bloblib.entities.BlobObject;

import java.io.File;

public record BlockDisplayAsset(String key, BlockData blockData, Vector3f scale,
                                Vector3f translation,
                                Quaternionf leftRotation,
                                Quaternionf rightRotation) implements BlobObject {
    @SuppressWarnings("DataFlowIssue")
    public static BlockDisplayAsset fromFile(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.isString("BlockData"))
            throw new IllegalArgumentException("BlockData is not valid");
        if (!config.isConfigurationSection("Scale"))
            throw new IllegalArgumentException("Scale is not valid");
        if (!config.isConfigurationSection("Translation"))
            throw new IllegalArgumentException("Translation is not valid");
        if (!config.isConfigurationSection("LeftRotation"))
            throw new IllegalArgumentException("LeftRotation is not valid");
        if (!config.isConfigurationSection("RightRotation"))
            throw new IllegalArgumentException("RightRotation is not valid");
        BlockData blockData = Bukkit.createBlockData(config.getString("BlockData"));
        ConfigurationSection scaleSection = config.getConfigurationSection("Scale");
        if (!scaleSection.isDouble("X") || !scaleSection.isDouble("Y") || !scaleSection.isDouble("Z"))
            throw new IllegalArgumentException("Scale is not valid");
        Vector3f scale = new Vector3f((float) scaleSection.getDouble("X"), (float) scaleSection.getDouble("Y"),
                (float) scaleSection.getDouble("Z"));
        ConfigurationSection translationSection = config.getConfigurationSection("Translation");
        if (!translationSection.isDouble("X") || !translationSection.isDouble("Y") || !translationSection.isDouble("Z"))
            throw new IllegalArgumentException("Translation is not valid");
        Vector3f translation = new Vector3f((float) translationSection.getDouble("X"),
                (float) translationSection.getDouble("Y"),
                (float) translationSection.getDouble("Z"));
        ConfigurationSection leftRotationSection = config.getConfigurationSection("LeftRotation");
        if (!leftRotationSection.isDouble("X") || !leftRotationSection.isDouble("Y") || !leftRotationSection.isDouble("Z")
                || !leftRotationSection.isDouble("W"))
            throw new IllegalArgumentException("LeftRotation is not valid");
        Quaternionf leftRotation = new Quaternionf((float) leftRotationSection.getDouble("X"),
                (float) leftRotationSection.getDouble("Y"),
                (float) leftRotationSection.getDouble("Z"),
                (float) leftRotationSection.getDouble("W"));
        ConfigurationSection rightRotationSection = config.getConfigurationSection("RightRotation");
        if (!rightRotationSection.isDouble("X") || !rightRotationSection.isDouble("Y") || !rightRotationSection.isDouble("Z")
                || !rightRotationSection.isDouble("W"))
            throw new IllegalArgumentException("RightRotation is not valid");
        Quaternionf rightRotation = new Quaternionf((float) rightRotationSection.getDouble("X"),
                (float) rightRotationSection.getDouble("Y"),
                (float) rightRotationSection.getDouble("Z"),
                (float) rightRotationSection.getDouble("W"));
        return new BlockDisplayAsset(file.getName().replace(".yml", ""),
                blockData, scale, translation, leftRotation, rightRotation);
    }

    /**
     * Will make an instance of BlockDisplay at given Location
     *
     * @param location Location to spawn BlockDisplay
     * @return BlockDisplay instance
     */
    public BlockDisplay instantiate(Location location) {
        BlockDisplay itemDisplay = location.getWorld().spawn(location, BlockDisplay.class);
        itemDisplay.setBlock(blockData);
        itemDisplay.setTransformation(new Transformation(translation, leftRotation,
                scale, rightRotation));
        return itemDisplay;
    }

    /**
     * Will make an instance of BlockDisplay at given Entity's Location
     *
     * @param entity Entity where to spawn BlockDisplay
     * @return BlockDisplay instance
     */
    public BlockDisplay instantiate(Entity entity) {
        Location location = entity.getLocation().getBlock().getLocation().clone();
        location.add(new Vector(0.5, 0, 0.5));
        return instantiate(location);
    }

    /**
     * Will make an instance of BlockDisplay at given Location
     *
     * @param block Block where to spawn BlockDisplay
     * @return BlockDisplay instance
     */
    public BlockDisplay instantiate(Block block) {
        Location location = block.getLocation().clone();
        location.add(new Vector(0.5, 0, 0.5));
        return instantiate(location);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public File saveToFile(File file) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.set("BlockData", blockData.getAsString(true));
        ConfigurationSection scaleSection = yamlConfiguration.createSection("Scale");
        scaleSection.set("X", scale.x);
        scaleSection.set("Y", scale.y);
        scaleSection.set("Z", scale.z);
        ConfigurationSection translationSection = yamlConfiguration.createSection("Translation");
        translationSection.set("X", translation.x);
        translationSection.set("Y", translation.y);
        translationSection.set("Z", translation.z);
        ConfigurationSection leftRotationSection = yamlConfiguration.createSection("LeftRotation");
        leftRotationSection.set("X", leftRotation.x);
        leftRotationSection.set("Y", leftRotation.y);
        leftRotationSection.set("Z", leftRotation.z);
        leftRotationSection.set("W", leftRotation.w);
        ConfigurationSection rightRotationSection = yamlConfiguration.createSection("RightRotation");
        rightRotationSection.set("X", rightRotation.x);
        rightRotationSection.set("Y", rightRotation.y);
        rightRotationSection.set("Z", rightRotation.z);
        rightRotationSection.set("W", rightRotation.w);
        try {
            yamlConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
