package io.github.anjoismysign.blobdesign.entities.presetblock;

import io.github.anjoismysign.blobdesign.director.manager.PresetBlockAssetDirector;
import io.github.anjoismysign.bloblib.entities.ObjectManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

public record WorldPresetBlockAssetManager(@NotNull Map<BlockVector, PresetBlockAsset<?>> getSingle,
                                           @NotNull Map<String, Set<PresetBlockAsset<?>>> getChunks,
                                           @NotNull String getWorldName,
                                           @NotNull PresetBlockAssetDirector getAssetDirector) {

    public static WorldPresetBlockAssetManager of(@NotNull String worldName,
                                                  @NotNull PresetBlockAssetDirector assetDirector) {
        Objects.requireNonNull(worldName, "'worldName' cannot be null");
        Objects.requireNonNull(assetDirector, "PresetBlockAssetDirector cannot be null");
        return new WorldPresetBlockAssetManager(
                new HashMap<>(),
                new HashMap<>(),
                worldName,
                assetDirector);
    }

    public static WorldPresetBlockAssetManager of(@NotNull World world,
                                                  @NotNull PresetBlockAssetDirector assetDirector) {
        Objects.requireNonNull(world, "World cannot be null");
        return of(world.getName(), assetDirector);
    }

    @Nullable
    public World getWorld() {
        return Bukkit.getWorld(getWorldName);
    }

    public String toIdentifier(@NotNull Chunk chunk) {
        return chunk.getX() + "," + chunk.getZ();
    }

    /**
     * Will add the asset to the manager and will also add it to the
     * getSingle map.
     * Will override any existent asset with the same key.
     *
     * @param asset The asset to add
     */
    public void add(PresetBlockAsset<?> asset) {
        try {
            ObjectManager<PresetBlockAsset<?>> objectManager = getAssetDirector.getObjectManager();
            String key = asset.id();
            ifExistsRemove(key);
            objectManager.addObject(key, asset);
            Location location = asset.getLocation();
            Chunk chunk = location.getChunk();
            String identifier = toIdentifier(chunk);
            getChunks.computeIfAbsent(identifier, k -> new HashSet<>()).add(asset);
            getSingle.put(asset.reference(), asset);
        } catch (Exception e) {
            getAssetDirector.getPlugin().getLogger().log(Level.SEVERE,
                    "Failed to add PresetBlockAsset", e);
        }
    }

    /**
     * If there's a PresetBlockAsset with the following key,
     * it will despawn and remove it.
     *
     * @param key The key to check
     */
    public void ifExistsRemove(String key) {
        PresetBlockAsset<?> existent = getAssetDirector.getObjectManager().getObject(key);
        if (existent == null)
            return;
        remove(existent);
    }

    /**
     * Will despawn and remove a PresetBlockAsset
     *
     * @param asset The asset to remove
     */
    public void remove(PresetBlockAsset<?> asset) {
        BlockVector reference = asset.reference();
        Bukkit.getLogger().severe("Removing: " + reference);
        Location location = asset.getLocation();
        Chunk chunk = location.getChunk();
        getSingle.remove(reference);
        Set<PresetBlockAsset<?>> assets = getChunks.get(toIdentifier(chunk));
        if (assets != null)
            assets.remove(asset);
        getAssetDirector.getObjectManager().removeObject(asset.getKey());
        asset.despawn();
    }

    /**
     * Will load a PresetBlockAsset to the getSingle map.
     *
     * @param asset The asset to load
     */
    public void load(PresetBlockAsset<?> asset) {
        getSingle.put(asset.reference(), asset);
    }
}
