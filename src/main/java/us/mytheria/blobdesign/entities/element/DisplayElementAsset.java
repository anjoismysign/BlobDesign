package us.mytheria.blobdesign.entities.element;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import us.mytheria.blobdesign.BlobDesignAPI;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.*;
import us.mytheria.blobdesign.factory.DisplayPresetFactory;
import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.display.DisplayDecorator;
import us.mytheria.bloblib.utilities.BukkitUtil;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public record DisplayElementAsset<T extends Display>(DisplayElement<T> element,
                                                     String key,
                                                     Location spawnLocation)
        implements BlobObject {

    public static DisplayElementAsset<?> fromFile(File file, DesignManagerDirector director) {
        JavaPlugin plugin = director.getPlugin();
        Logger logger = plugin.getLogger();
        String path = file.getPath();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.isConfigurationSection("Spawn-Location")) {
            logger.severe("Spawn-Location is not valid inside " + path);
            return null;
        }
        ConfigurationSection spawnSection = config.getConfigurationSection("Spawn-Location");
        if (!spawnSection.isDouble("X") || !spawnSection.isDouble("Y") || !spawnSection.isDouble("Z")) {
            logger.severe("Spawn-Location is not valid inside " + path);
            return null;
        }
        Location location = BukkitUtil.deserializeLocationOrNull(spawnSection);
        if (location == null) {
            logger.severe("Spawn-Location is not valid inside " + path);
            return null;
        }
        if (!config.isString("Display-Element-Type")) {
            logger.severe("Display-Element-Type is not valid inside " + path);
            return null;
        }
        DisplayElementType type = DisplayElementType.valueOf(config.getString("Display-Element-Type"));
        if (!config.isString("Display-Element-Key")) {
            logger.severe("Display-Element-Key is not valid inside " + path);
            return null;
        }
        String displayElementKey = config.getString("Display-Element-Key");
        CompletableFuture<DisplayElement<?>> future = new CompletableFuture<>();
        boolean overridePreset = config.getBoolean("Override-Preset", false);
        if (!overridePreset)
            switch (type) {
                case BLOCK_DISPLAY -> {
                    DisplayPreset<BlockDisplay> asset = BlobDesignAPI.getBlockDisplayPreset(displayElementKey);
                    if (asset == null) {
                        logger.severe("BLOCK_DISPLAY with key '" + displayElementKey + "' does not exist (or didn't load) in file " + path);
                        return null;
                    }
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        future.complete(asset.instantiateElement(location));
                    });
                }
                case ITEM_DISPLAY -> {
                    DisplayPreset<ItemDisplay> asset = BlobDesignAPI.getItemDisplayPreset(displayElementKey);
                    if (asset == null) {
                        logger.severe("ITEM_DISPLAY with key '" + displayElementKey + "' does not exist (or didn't load) in file " + path);
                        return null;
                    }
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        future.complete(asset.instantiateElement(location));
                    });
                }
                default -> {
                    logger.severe("DisplayElementType '" + type.name() + "' is not valid in file " + path);
                    return null;
                }
            }
        else {
            DisplayOperator operator;
            try {
                operator = DisplayOperatorReader.READ(config, path, plugin);
            } catch (Exception e) {
                logger.severe(e.getMessage() + " in file " + path);
                return null;
            }
            switch (type) {
                case BLOCK_DISPLAY -> {
                    BlockDisplayPreset asset = BlobDesignAPI.getBlockDisplayPreset(displayElementKey);
                    if (asset == null) {
                        logger.severe("BlockDisplayAsset with key '" + displayElementKey + "' does not exist in file " + path);
                        return null;
                    }
                    Bukkit.getScheduler().runTask(plugin, () ->
                            future.complete(DisplayPresetFactory.BLOCK_DISPLAY(asset.getBlockData().clone(),
                                            operator)
                                    .instantiateElement(location))
                    );
                }
                case ITEM_DISPLAY -> {
                    ItemDisplayPreset asset = BlobDesignAPI.getItemDisplayPreset(displayElementKey);
                    if (asset == null) {
                        logger.severe("ItemDisplayAsset with key '" + displayElementKey + "' does not exist in file " + path);
                        return null;
                    }
                    Bukkit.getScheduler().runTask(plugin, () ->
                            future.complete(DisplayPresetFactory.ITEM_DISPLAY(new ItemStack(asset.getItemStack()),
                                            asset.getTransform(),
                                            operator)
                                    .instantiateElement(location))
                    );
                }
                default -> {
                    logger.severe("DisplayElementType '" + type.name() + "' is not valid.");
                    return null;
                }
            }
        }
        DisplayElement<?> displayElement = future.join();
        return new DisplayElementAsset<>(displayElement,
                file.getName().replace(".yml", ""),
                location);
    }

    /**
     * Will despawn the display element (as the Minecraft entity that's
     * inside the Minecraft World) associated with this asset.
     */
    public void despawn() {
        DisplayDecorator<T> decorator = element.getDecorator();
        decorator.stopClock();
        decorator.call().remove();
    }

    public String getKey() {
        return key;
    }

    public File saveToFile(File directory, boolean overridePreset) {
        File file = instanceFile(directory);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection spawnSection = yaml.createSection("Spawn-Location");
        BukkitUtil.serializeLocation(spawnLocation, spawnSection);
        yaml.set("DisplayElementType", element.getType().name());
        yaml.set("DisplayElementKey", key);
        if (overridePreset) {
            yaml.set("Override-Preset", true);
            element.getPreset().writePreset(yaml);
        }
        try {
            yaml.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public File saveToFile(File directory) {
        return saveToFile(directory, false);
    }
}
