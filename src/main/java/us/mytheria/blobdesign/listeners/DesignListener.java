package us.mytheria.blobdesign.listeners;

import org.bukkit.event.Listener;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.DesignManagerDirector;

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
