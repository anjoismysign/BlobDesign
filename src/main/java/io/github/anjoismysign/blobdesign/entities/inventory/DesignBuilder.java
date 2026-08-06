package io.github.anjoismysign.blobdesign.entities.inventory;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.bloblib.domain.BlobObject;
import io.github.anjoismysign.bloblib.inventory.BlobInventory;
import io.github.anjoismysign.bloblib.inventory.ObjectBuilder;
import io.github.anjoismysign.bloblib.manager.ObjectDirector;

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
