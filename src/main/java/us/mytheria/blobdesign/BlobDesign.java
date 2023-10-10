package us.mytheria.blobdesign;

import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.command.DisplayEditor;
import us.mytheria.blobdesign.director.command.DisplayElementAssetCmd;
import us.mytheria.blobdesign.director.command.DisplaySpawner;
import us.mytheria.blobdesign.director.command.HeadPresetBlock;
import us.mytheria.bloblib.entities.PluginUpdater;
import us.mytheria.bloblib.managers.BlobPlugin;
import us.mytheria.bloblib.managers.IManagerDirector;

public class BlobDesign extends BlobPlugin {
    protected DesignManagerDirector director;
    private IManagerDirector proxy;
    private PluginUpdater updater;

    protected static BlobDesign instance;

    @Override
    public void onEnable() {
        instance = this;
        director = new DesignManagerDirector(this);
        proxy = director.proxy();
        updater = generateGitHubUpdater("anjoismysign", "BlobDesign");
        new DisplaySpawner(director);
        new DisplayEditor(director);
        new DisplayElementAssetCmd(director);
        new HeadPresetBlock(director);
    }

    public IManagerDirector getManagerDirector() {
        return proxy;
    }

    @Override
    @NotNull
    public PluginUpdater getPluginUpdater() {
        return updater;
    }
}