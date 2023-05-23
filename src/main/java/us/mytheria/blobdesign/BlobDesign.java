package us.mytheria.blobdesign;

import org.bukkit.Bukkit;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.command.DisplaySpawner;
import us.mytheria.bloblib.managers.BlobPlugin;

public final class BlobDesign extends BlobPlugin {
    private DesignManagerDirector director;

    @Override
    public void onEnable() {
        director = new DesignManagerDirector(this);
        new DisplaySpawner(this);
        Bukkit.getScheduler().runTask(this, () ->
                director.postWorld());
    }

    @Override
    public void onDisable() {
        unregisterFromBlobLib();
    }

    @Override
    public DesignManagerDirector getManagerDirector() {
        return director;
    }
}