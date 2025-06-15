package io.github.anjoismysign.blobdesign;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.director.command.DisplayEditor;
import io.github.anjoismysign.blobdesign.director.command.DisplayElementAssetCmd;
import io.github.anjoismysign.blobdesign.director.command.DisplaySpawner;
import io.github.anjoismysign.blobdesign.director.command.DisplaySummoner;
import io.github.anjoismysign.blobdesign.director.command.HeadPresetBlock;
import io.github.anjoismysign.bloblib.entities.PluginUpdater;
import io.github.anjoismysign.bloblib.managers.BlobPlugin;
import io.github.anjoismysign.bloblib.managers.IManagerDirector;
import org.jetbrains.annotations.NotNull;

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
        new DisplaySummoner(director);
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