package io.github.anjoismysign.blobdesign.director;

import io.github.anjoismysign.blobdesign.BlobDesign;
import io.github.anjoismysign.bloblib.entities.GenericManager;

public class DesignManager extends GenericManager<BlobDesign, DesignManagerDirector> {
    public DesignManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
    }
}