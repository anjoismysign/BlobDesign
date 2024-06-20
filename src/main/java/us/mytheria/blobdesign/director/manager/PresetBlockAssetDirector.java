package us.mytheria.blobdesign.director.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.DesignObjectDirector;
import us.mytheria.blobdesign.entities.presetblock.PresetBlockAsset;
import us.mytheria.blobdesign.entities.presetblock.WorldPresetBlockAssetManager;
import us.mytheria.bloblib.entities.ObjectDirectorData;

import java.util.HashMap;
import java.util.Map;

public class PresetBlockAssetDirector extends DesignObjectDirector<PresetBlockAsset<?>> {
    @NotNull
    private Map<String, WorldPresetBlockAssetManager> worldManagers;

    public PresetBlockAssetDirector(DesignManagerDirector managerDirector) {
        super(managerDirector, ObjectDirectorData.simple(managerDirector.getRealFileManager(),
                        "PresetBlock"),
                file -> PresetBlockAsset.fromFile(file, managerDirector),
                false);
        worldManagers = new HashMap<>();
    }

    @Override
    public void reload() {
        Bukkit.getScheduler().runTask(getPlugin(), () -> {
            unload();
            super.reload();
        });
    }

    @Override
    public void unload() {
        getObjectManager().values().forEach(PresetBlockAsset::despawn);
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
     * Will add the asset to the manager.
     * Will override any existent asset with the same key.
     *
     * @param asset The asset to add
     */
    public void add(PresetBlockAsset<?> asset) {
        World world = asset.getWorld();
        WorldPresetBlockAssetManager manager = worldManagers.get(world.getName());
        if (manager == null) {
            manager = WorldPresetBlockAssetManager.of(world, this);
            worldManagers.put(world.getName(), manager);
        }
        manager.add(asset);
    }

    /**
     * Will load a PresetBlockAsset to the blocks map.
     *
     * @param asset The asset to load
     */
    public void load(PresetBlockAsset<?> asset) {
        World world = asset.getWorld();
        WorldPresetBlockAssetManager manager = worldManagers.get(world.getName());
        if (manager == null) {
            manager = WorldPresetBlockAssetManager.of(world, this);
            worldManagers.put(world.getName(), manager);
        }
        manager.load(asset);
    }

    @Nullable
    public PresetBlockAsset<?> get(
            @NotNull World world,
            @NotNull BlockVector reference) {
        WorldPresetBlockAssetManager manager = worldManagers.get(world.getName());
        if (manager == null) {
            manager = WorldPresetBlockAssetManager.of(world, this);
            worldManagers.put(world.getName(), manager);
        }
        return manager.blocks().get(reference);
    }
}
