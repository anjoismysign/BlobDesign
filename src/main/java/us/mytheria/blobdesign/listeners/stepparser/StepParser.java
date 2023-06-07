package us.mytheria.blobdesign.listeners.stepparser;

import us.mytheria.blobdesign.BlobDesign;

public abstract class StepParser {
    protected BlobDesign plugin;

    public StepParser(BlobDesign plugin) {
        this.plugin = plugin;
    }
}
