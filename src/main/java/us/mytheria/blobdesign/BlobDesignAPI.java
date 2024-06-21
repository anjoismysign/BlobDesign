package us.mytheria.blobdesign;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.*;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.blobdesign.entities.inventory.InventoryType;
import us.mytheria.blobdesign.entities.presetblock.PresetBlockAsset;
import us.mytheria.bloblib.entities.inventory.BlobInventory;

public class BlobDesignAPI {
    private static DesignManagerDirector director() {
        return BlobDesign.instance.director;
    }

    /**
     * Gets a copy the inventory for the given type.
     *
     * @param type the type to get the inventory for
     * @return the inventory for the given type
     */
    @NotNull
    public static BlobInventory getInventory(InventoryType type) {
        return director().getInventoryManager()
                .getInventory(type);
    }

    /**
     * Gets the display preset for the given key.
     * If cast to the implementation, be sure to never modify
     * the BlockData that BlockDisplayPreset holds since this
     * will affect all instances of such preset in game.
     *
     * @param key the key to get the display preset for
     * @return the display preset for the given key. null if not found
     */
    @Nullable
    public static BlockDisplayPreset getBlockDisplayPreset(String key) {
        return director().getBlockDisplayAssetDirector()
                .getObjectManager().getObject(key);
    }

    /**
     * Gets the display preset for the given key.
     * If cast to the implementation, be sure to never modify
     * the ItemStack that ItemDisplayPreset holds since this
     * will affect all instances of such preset in game.
     *
     * @param key the key to get the display preset for
     * @return the display preset for the given key. null if not found
     */
    @Nullable
    public static ItemDisplayPreset getItemDisplayPreset(String key) {
        return director().getItemDisplayAssetDirector()
                .getObjectManager().getObject(key);
    }

    /**
     * Will get a DisplayPreset for the given type and preset key.
     *
     * @param type      the type of preset
     * @param presetKey the key of the preset
     * @return the DisplayPreset for the given type and preset key
     */
    @Nullable
    public static DisplayPreset<?> getPreset(DisplayElementType type,
                                             String presetKey) {
        switch (type) {
            case BLOCK_DISPLAY -> {
                return getBlockDisplayPreset(presetKey);
            }
            case ITEM_DISPLAY -> {
                return getItemDisplayPreset(presetKey);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Will get a DisplayPreset for the given PresetData.
     *
     * @param presetData the PresetData to get the PresetPlacer for
     * @return the DisplayPreset for the given PresetData
     */
    @Nullable
    public static DisplayPreset<?> getPreset(PresetData presetData) {
        return getPreset(presetData.type(), presetData.key());
    }

    /**
     * Will deserialize a DisplayPreset from the given PersistentDataContainer.
     *
     * @param container the container to deserialize from
     * @return the DisplayPreset deserialized from the given container
     */
    @Nullable
    public static DisplayPreset<?> deserializePreset(PersistentDataContainer container) {
        PresetPlacer placer = PresetPlacer.deserialize(container, director().getPluginOperator());
        if (placer == null)
            return null;
        return getPreset(placer.getPresetData());
    }

    /**
     * Will check if the given location belongs to a preset block.
     *
     * @param location the location to check
     * @return the PresetBlockAsset if the location belongs to a preset block. null otherwise
     */
    @Nullable
    public static PresetBlockAsset<?> isPresetBlock(@Nullable Location location) {
        if (location == null)
            return null;
        World world = location.getWorld();
        if (world == null)
            return null;
        return director().getPresetBlockAssetDirector().get(world, location.getBlock().getLocation().toVector().toBlockVector());
    }
}
