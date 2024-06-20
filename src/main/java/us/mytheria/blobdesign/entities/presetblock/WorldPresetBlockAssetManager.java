package us.mytheria.blobdesign.entities.presetblock;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobdesign.director.manager.PresetBlockAssetDirector;
import us.mytheria.bloblib.entities.ObjectManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

public record WorldPresetBlockAssetManager(@NotNull Map<BlockVector, PresetBlockAsset<?>> blocks,
                                           @NotNull Map<String, Set<PresetBlockAsset<?>>> chunk,
                                           @NotNull String getWorldName,
                                           @NotNull PresetBlockAssetDirector getAssetDirector) {

    public static WorldPresetBlockAssetManager of(@NotNull World world,
                                                  @NotNull PresetBlockAssetDirector assetDirector) {
        Objects.requireNonNull(world, "World cannot be null");
        Objects.requireNonNull(assetDirector, "PresetBlockAssetDirector cannot be null");
        return new WorldPresetBlockAssetManager(
                new HashMap<>(),
                new HashMap<>(),
                world.getName(),
                assetDirector);
    }

    @Nullable
    public World getWorld() {
        return Bukkit.getWorld(getWorldName);
    }

    /**
     * Will add the asset to the manager and will also add it to the
     * blocks map.
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
            blocks.put(asset.reference(), asset);
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
        getAssetDirector.getObjectManager().removeObject(asset.getKey());
        asset.despawn();
    }

    /**
     * Will load a PresetBlockAsset to the blocks map.
     *
     * @param asset The asset to load
     */
    public void load(PresetBlockAsset<?> asset) {
        blocks.put(asset.reference(), asset);
    }
}
