package io.github.anjoismysign.blobdesign.listeners;

import io.github.anjoismysign.blobdesign.BlobDesign;
import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import org.bukkit.event.Listener;

public abstract class DesignListener implements Listener {
    private final DesignManagerDirector director;

    public DesignListener(DesignManagerDirector director) {
        this.director = director;
    }

    public BlobDesign getPlugin() {
        return director.getPlugin();
    }

    protected DesignManagerDirector getManagerDirector() {
        return director;
    }
}
