package us.mytheria.blobdesign.entities.inventory;

import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.ObjectDirector;
import us.mytheria.bloblib.entities.inventory.BlobInventory;
import us.mytheria.bloblib.entities.inventory.ObjectBuilder;

import java.util.UUID;

public abstract class DesignBuilder<T extends BlobObject> extends ObjectBuilder<T> {
    private final DesignManagerDirector managerDirector;

    public DesignBuilder(BlobInventory blobInventory, UUID builderId,
                         ObjectDirector<T> objectDirector,
                         DesignManagerDirector managerDirector) {
        super(blobInventory, builderId, objectDirector);
        this.managerDirector = managerDirector;
    }

    protected DesignManagerDirector getManagerDirector() {
        return managerDirector;
    }
}
