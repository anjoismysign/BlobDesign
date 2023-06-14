package us.mytheria.blobdesign.director;

import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.ObjectDirector;
import us.mytheria.bloblib.entities.ObjectDirectorData;

import java.io.File;
import java.util.function.Function;

public class DisplayObjectDirector<T extends BlobObject> extends ObjectDirector<T> {
    private final DesignManagerDirector managerDirector;

    public DisplayObjectDirector(DesignManagerDirector managerDirector,
                                 ObjectDirectorData objectDirectorData,
                                 Function<File, T> readFunction,
                                 boolean hasObjectBuilderManager) {
        super(managerDirector, objectDirectorData, readFunction, hasObjectBuilderManager);
        this.managerDirector = managerDirector;
    }

    public DisplayObjectDirector(DesignManagerDirector managerDirector,
                                 ObjectDirectorData objectDirectorData,
                                 Function<File, T> readFunction) {
        this(managerDirector, objectDirectorData, readFunction, true);
    }

    public DesignManagerDirector getManagerDirector() {
        return managerDirector;
    }
}
