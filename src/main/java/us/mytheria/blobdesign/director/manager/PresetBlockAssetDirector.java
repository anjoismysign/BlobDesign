package us.mytheria.blobdesign.director.manager;

import org.bukkit.Bukkit;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.DesignObjectDirector;
import us.mytheria.blobdesign.entities.blockasset.PresetBlockAsset;
import us.mytheria.bloblib.entities.ObjectDirectorData;
import us.mytheria.bloblib.entities.ObjectManager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class PresetBlockAssetDirector extends DesignObjectDirector<PresetBlockAsset<?>> {
    private Map<BlockVector, PresetBlockAsset<?>> blocks;

    public PresetBlockAssetDirector(DesignManagerDirector managerDirector) {
        super(managerDirector, ObjectDirectorData.simple(managerDirector.getRealFileManager(),
                        "PresetBlock"),
                file -> PresetBlockAsset.fromFile(file, managerDirector),
                false);
        blocks = new HashMap<>();
    }

    @Override
    public void reload() {
        Bukkit.getScheduler().runTask(getPlugin(), () -> {
            unload();
            blocks = new HashMap<>();
            super.reload();
        });
    }

    @Override
    public void unload() {
        getObjectManager().values().forEach(PresetBlockAsset::despawn);
    }

    /**
     * If there's a PresetBlockAsset with the following key,
     * it will despawn and remove it.
     *
     * @param key The key to check
     */
    public void ifExistsRemove(String key) {
        PresetBlockAsset<?> existent = getObjectManager().getObject(key);
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
        getObjectManager().removeObject(asset.getKey());
        asset.despawn();
    }

    /**
     * Will add the asset to the manager and will also add it to the
     * blocks map.
     * Will override any existent asset with the same key.
     *
     * @param asset The asset to add
     * @param key   The key to add the asset with
     */
    public void add(PresetBlockAsset<?> asset, String key) {
        try {
            ObjectManager<PresetBlockAsset<?>> objectManager = getObjectManager();
            ifExistsRemove(key);
            objectManager.addObject(key, asset);
            blocks.put(asset.reference(), asset);
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.SEVERE,
                    "Failed to add PresetBlockAsset", e);
        }
    }

    /**
     * Will load a PresetBlockAsset to the blocks map.
     *
     * @param asset The asset to load
     */
    public void load(PresetBlockAsset<?> asset) {
        blocks.put(asset.reference(), asset);
    }

    @Nullable
    public PresetBlockAsset<?> get(BlockVector reference) {
        return blocks.get(reference);
    }
}
