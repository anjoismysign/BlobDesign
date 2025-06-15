package io.github.anjoismysign.blobdesign.entities.proxy;

import io.github.anjoismysign.blobdesign.entities.BlockDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.BlockDisplayPresetAsset;
import io.github.anjoismysign.blobdesign.entities.PresetData;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElement;
import io.github.anjoismysign.blobdesign.entities.presetblock.PresetBlock;
import io.github.anjoismysign.bloblib.entities.BlobObject;
import io.github.anjoismysign.bloblib.entities.display.DisplayData;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class BlockDisplayPresetAssetProxy implements BlockDisplayPreset, BlobObject {
    private final BlockDisplayPresetAsset real;

    protected BlockDisplayPresetAssetProxy(BlockDisplayPresetAsset asset) {
        this.real = asset;
    }

    public BlockData getBlockData() {
        return real.getBlockData();
    }

    public Plugin getPlugin() {
        return real.getPlugin();
    }

    public DisplayData getDisplayData() {
        return real.getDisplayData();
    }

    public Transformation getTransformation() {
        return real.getTransformation();
    }

    public @NotNull DisplayElement<BlockDisplay> instantiateElement(Location location) {
        return real.instantiateElement(location);
    }

    public @NotNull BlockDisplay instantiate(Location location) {
        return real.instantiate(location);
    }

    @NotNull
    public PresetBlock<BlockDisplay> instantiatePresetBlock(Location location, String key) {
        return real.instantiatePresetBlock(location, key);
    }

    public String getKey() {
        return real.getKey();
    }

    public File saveToFile(File file) {
        return real.saveToFile(file);
    }

    public void serialize(PersistentDataContainer container) {
        real.serialize(container);
    }

    public @NotNull PresetData getPresetData() {
        return real.getPresetData();
    }
}
