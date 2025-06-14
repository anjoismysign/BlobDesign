package io.github.anjoismysign.blobdesign.entities.presetblock;

import io.github.anjoismysign.blobdesign.BlobDesignAPI;
import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.director.manager.PresetBlockAssetDirector;
import io.github.anjoismysign.blobdesign.entities.BlockDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.DisplayPreset;
import io.github.anjoismysign.blobdesign.entities.ItemDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementType;
import io.github.anjoismysign.bloblib.entities.BlobObject;
import io.github.anjoismysign.bloblib.entities.display.DisplayDecorator;
import io.github.anjoismysign.bloblib.entities.positionable.Positionable;
import io.github.anjoismysign.bloblib.entities.positionable.PositionableIO;
import io.github.anjoismysign.bloblib.utilities.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Display;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PresetBlockAsset<T extends Display>
        implements BlobObject,
        PresetBlock<T> {
    private DisplayDecorator<T> decorator;

    private final DisplayPreset<T> preset;
    private final String key;

    @Nullable
    public static PresetBlockAsset<?> fromFile(File file, DesignManagerDirector director) {
        JavaPlugin plugin = director.getPlugin();
        Logger logger = plugin.getLogger();
        String path = file.getPath();
        String key = file.getName().replace(".yml", "");
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
        PresetBlockAssetDirector assetDirector = director.getPresetBlockAssetDirector();
        String displayElementKey = config.getString("Display-Element-Key");
        CompletableFuture<PresetBlockAsset<?>> future = new CompletableFuture<>();
        switch (type) {
            case BLOCK_DISPLAY -> {
                BlockDisplayPreset preset = BlobDesignAPI.getBlockDisplayPreset(displayElementKey);
                if (preset == null) {
                    logger.severe("BLOCK_DISPLAY with key '" + displayElementKey + "' does not exist (or didn't load) in file " + path);
                    return null;
                }
                Bukkit.getScheduler().runTask(plugin, () -> {
                    BlockDisplayPresetBlockAsset asset;
                    try {
                        asset = BlockDisplayPresetBlockAsset.of(preset, key, location,
                                director);
                        future.complete(asset);
                    } catch (Exception e) {
                        future.completeExceptionally(e);
                    }
                });
            }
            case ITEM_DISPLAY -> {
                ItemDisplayPreset preset = BlobDesignAPI.getItemDisplayPreset(displayElementKey);
                if (preset == null) {
                    logger.severe("ITEM_DISPLAY with key '" + displayElementKey + "' does not exist (or didn't load) in file " + path);
                    return null;
                }
                Bukkit.getScheduler().runTask(plugin, () -> {
                    ItemDisplayPresetBlockAsset asset;
                    try {
                        asset = ItemDisplayPresetBlockAsset.of(preset, key, location,
                                director);
                        future.complete(asset);
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
        PresetBlockAsset<?> asset;
        try {
            asset = future.join();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown while loading PresetBlockAsset from file " + path, e);
            return null;
        }
        assetDirector.load(asset);
        return asset;
    }

    public PresetBlockAsset(String key,
                            DisplayDecorator<T> decorator,
                            DisplayPreset<T> preset,
                            DesignManagerDirector director) {
        this.key = key;
        this.decorator = decorator;
        this.preset = preset;
    }

    public String getKey() {
        return key;
    }

    public File saveToFile(File directory) {
        File file = instanceFile(directory);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection spawnSection = yaml.createSection("Spawn-Location");
        BukkitUtil.serializeLocation(getLocation(), spawnSection);
        yaml.set("Display-Element-Type", getType().name());
        yaml.set("Display-Element-Key", getPresetBlock().getDisplayPreset().getPresetData().key());
        try {
            yaml.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public @NotNull DisplayDecorator<T> getDecorator() {
        return decorator;
    }

    public @NotNull DisplayPreset<T> getDisplayPreset() {
        return preset;
    }

    /**
     * Respawns the entity at the spawn location and updates the decorator reference.
     *
     * @param respawnBlock Whether to respawn the block that provides hitbox
     */
    public void respawn(boolean respawnBlock) {
        if (respawnBlock) {
            despawn(true);
            this.decorator = preset.instantiateDecorator(getLocation());
            Bukkit.getScheduler().runTask(preset.getPlugin(), () -> {
                Block block = getLocation().getBlock();
                block.setType(Material.BARRIER);
            });
            return;
        }
        despawn(false);
        this.decorator = preset.instantiateDecorator(getLocation());
    }

    /**
     * Respawns the entity at the spawn location and updates the decorator reference.
     */
    public void respawn() {
        respawn(false);
    }
}
