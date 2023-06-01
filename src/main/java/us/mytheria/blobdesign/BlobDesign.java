package us.mytheria.blobdesign;

import org.bukkit.Bukkit;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.command.DisplayEditor;
import us.mytheria.blobdesign.director.command.DisplaySpawner;
import us.mytheria.bloblib.managers.BlobPlugin;

public class BlobDesign extends BlobPlugin {
    private DesignManagerDirector director;

    protected static BlobDesign instance;

    @Override
    public void onEnable() {
        instance = this;
        director = new DesignManagerDirector(this);
        new DisplaySpawner(this);
        new DisplayEditor(this);
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