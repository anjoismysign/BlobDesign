package us.mytheria.blobdesign.entities.element;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Display;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobdesign.BlobDesignAPI;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.BlockDisplayPreset;
import us.mytheria.blobdesign.entities.DesignDisplayOperator;
import us.mytheria.blobdesign.entities.DesignDisplayOperatorReader;
import us.mytheria.blobdesign.entities.ItemDisplayPreset;
import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.positionable.Positionable;
import us.mytheria.bloblib.entities.positionable.PositionableIO;
import us.mytheria.bloblib.utilities.BukkitUtil;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public record DisplayElementAsset<T extends Display>(DisplayElement<T> element,
                                                     String key,
                                                     Location spawnLocation)
        implements BlobObject {

    /**
     * Will read a DisplayElementAsset from a file.
     * Entity will be spawned at the location specified in the file.
     * Whenever overriding the preset, expect a little bit (around 1 microsecond)
     * worse performance because it works very similar to a proxy,
     * so it would be proxied two times...
     * However, if you are not using their methods, this performance will
     * only be seen whenever spawning the entity, which only happens
     * once in their lifetime!
     *
     * @param file     The file to read from.
     * @param director The director to use.
     * @return The DisplayElementAsset, or null if there was an error.
     */
    public static DisplayElementAsset<?> fromFile(File file, DesignManagerDirector director) {
        JavaPlugin plugin = director.getPlugin();
        Logger logger = plugin.getLogger();
        String path = file.getPath();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.isConfigurationSection("Spawn-Location")) {
            logger.severe("Spawn-Location is not valid inside " + path);
            return null;
        }
        @Nullable ConfigurationSection spawnSection = config.getConfigurationSection("Spawn-Location");
        Objects.requireNonNull(spawnSection, "'Spawn-Location' cannot be null in " + path);
        Positionable positionable = PositionableIO.INSTANCE.read(spawnSection);
        if (!positionable.getPositionableType().isLocatable())
            throw new RuntimeException("'Spawn-Location' is missing 'World' attribute " + path);
        Location location = positionable.toLocation();
        if (!config.isString("Display-Element-Type")) {
            logger.severe("Display-Element-Type is not valid inside " + path);
            return null;
        }
        DisplayElementType type = DisplayElementType.valueOf(config.getString("Display-Element-Type"));
        if (!config.isString("Display-Element-Key")) {
            logger.severe("Display-Element-Key is not valid inside " + path);
            return null;
        }
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
                    BlockDisplayPreset asset = BlobDesignAPI.getBlockDisplayPreset(displayElementKey);
                    if (asset == null) {
                        logger.severe("BLOCK_DISPLAY with key '" + displayElementKey + "' does not exist (or didn't load) in file " + path);
                        return null;
                    }
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        try {
                            future.complete(asset.instantiateElement(location));
                        } catch (Exception e) {
                            future.completeExceptionally(e);
                        }
                    });
                }
                case ITEM_DISPLAY -> {
                    ItemDisplayPreset asset = BlobDesignAPI.getItemDisplayPreset(displayElementKey);
                    if (asset == null) {
                        logger.severe("ITEM_DISPLAY with key '" + displayElementKey + "' does not exist (or didn't load) in file " + path);
                        return null;
                    }
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        try {
                            future.complete(asset.instantiateElement(location));
                        } catch (Exception e) {
                            future.completeExceptionally(e);
                        }
                    });
                }
                default -> {
                    logger.severe("DisplayElementType '" + type.name() + "' is not valid in file " + path);
                    return null;
                }
            }
        else {
            DesignDisplayOperator operator;
            try {
                operator = DesignDisplayOperatorReader.CONVERT(config, path, plugin);
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
                    Bukkit.getScheduler().runTask(plugin, () -> {
                                try {
                                    future.complete(asset.override(operator)
                                            .instantiateElement(location));
                                } catch (Exception e) {
                                    future.completeExceptionally(e);
                                }
                            }
                    );
                }
                case ITEM_DISPLAY -> {
                    ItemDisplayPreset asset = BlobDesignAPI.getItemDisplayPreset(displayElementKey);
                    if (asset == null) {
                        logger.severe("ItemDisplayAsset with key '" + displayElementKey + "' does not exist in file " + path);
                        return null;
                    }
                    Bukkit.getScheduler().runTask(plugin, () -> {
                                try {
                                    future.complete(asset.override(operator)
                                            .instantiateElement(location));
                                } catch (Exception e) {
                                    future.completeExceptionally(e);
                                }
                            }
                    );
                }
                default -> {
                    logger.severe("DisplayElementType '" + type.name() + "' is not valid.");
                    return null;
                }
            }
        }
        DisplayElement<?> displayElement;
        try {
            displayElement = future.join();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown while loading DisplayElementAsset from file " + path, e);
            return null;
        }
        return new DisplayElementAsset<>(displayElement,
                file.getName().replace(".yml", ""),
                location);
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
            element.getDisplayPreset().writePreset(yaml);
        }
        try {
            yaml.save(file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return file;
    }

    public File saveToFile(File directory) {
        return saveToFile(directory, false);
    }
}
