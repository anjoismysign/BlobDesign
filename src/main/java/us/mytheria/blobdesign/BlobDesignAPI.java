package us.mytheria.blobdesign;

import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.director.DesignManagerDirector;
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
}
