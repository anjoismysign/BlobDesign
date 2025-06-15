package io.github.anjoismysign.blobdesign.director;

import io.github.anjoismysign.bloblib.entities.BlobObject;
import io.github.anjoismysign.bloblib.entities.ObjectDirector;
import io.github.anjoismysign.bloblib.entities.ObjectDirectorData;

import java.io.File;
import java.util.function.Function;

public abstract class DesignObjectDirector<T extends BlobObject> extends ObjectDirector<T> {
    private final DesignManagerDirector managerDirector;

    public DesignObjectDirector(DesignManagerDirector managerDirector,
                                ObjectDirectorData objectDirectorData,
                                Function<File, T> readFunction,
                                boolean hasObjectBuilderManager) {
        super(managerDirector, objectDirectorData, readFunction, hasObjectBuilderManager);
        this.managerDirector = managerDirector;
    }

    public DesignObjectDirector(DesignManagerDirector managerDirector,
                                ObjectDirectorData objectDirectorData,
                                Function<File, T> readFunction) {
        this(managerDirector, objectDirectorData, readFunction, true);
    }

    public DesignManagerDirector getManagerDirector() {
        return managerDirector;
    }
}
