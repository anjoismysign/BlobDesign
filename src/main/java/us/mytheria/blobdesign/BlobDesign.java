package us.mytheria.blobdesign;

import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.command.DisplayEditor;
import us.mytheria.blobdesign.director.command.DisplayElementAssetCmd;
import us.mytheria.blobdesign.director.command.DisplaySpawner;
import us.mytheria.bloblib.entities.proxy.BlobProxifier;
import us.mytheria.bloblib.managers.BlobPlugin;
import us.mytheria.bloblib.managers.IManagerDirector;

public class BlobDesign extends BlobPlugin {
    protected DesignManagerDirector director;
    private IManagerDirector proxy;

    protected static BlobDesign instance;

    @Override
    public void onEnable() {
        instance = this;
        director = new DesignManagerDirector(this);
        proxy = BlobProxifier.PROXY(director);
        new DisplaySpawner(this, director);
        new DisplayEditor(this, director);
        new DisplayElementAssetCmd(this, director);
    }

    @Override
    public void onDisable() {
        unregisterFromBlobLib();
        director.unload();
    }

    public IManagerDirector getManagerDirector() {
        return proxy;
    }
}