package us.mytheria.blobdesign;

import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.command.DisplayEditor;
import us.mytheria.blobdesign.director.command.DisplayElementAssetCmd;
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
        new DisplayElementAssetCmd(this);
    }

    @Override
    public void onDisable() {
        unregisterFromBlobLib();
        director.unload();
    }

    @Override
    public DesignManagerDirector getManagerDirector() {
        return director;
    }
}