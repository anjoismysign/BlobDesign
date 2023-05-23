package us.mytheria.blobdesign.director;

import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.bloblib.entities.GenericManager;

public class DesignManager extends GenericManager<BlobDesign, DesignManagerDirector> {
    public DesignManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
    }
}