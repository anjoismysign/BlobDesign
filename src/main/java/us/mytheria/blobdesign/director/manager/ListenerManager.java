package us.mytheria.blobdesign.director.manager;

import us.mytheria.blobdesign.director.DesignManager;
import us.mytheria.blobdesign.director.DesignManagerDirector;

public class ListenerManager extends DesignManager {

    public ListenerManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
        reload();
    }

    @Override
    public void reload() {
        //logics
    }
}