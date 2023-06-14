package us.mytheria.blobdesign;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.DisplayPreset;
import us.mytheria.blobdesign.entities.inventory.InventoryType;
import us.mytheria.bloblib.entities.inventory.BlobInventory;

public class BlobDesignAPI {
    private static DesignManagerDirector director() {
        return BlobDesign.instance.getManagerDirector();
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
    public static DisplayPreset<BlockDisplay> getBlockDisplayPreset(String key) {
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
    public static DisplayPreset<ItemDisplay> getItemDisplayPreset(String key) {
        return director().getItemDisplayAssetDirector()
                .getObjectManager().getObject(key);
    }
}
