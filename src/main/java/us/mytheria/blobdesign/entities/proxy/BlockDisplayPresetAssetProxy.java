package us.mytheria.blobdesign.entities.proxy;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.entities.BlockDisplayPreset;
import us.mytheria.blobdesign.entities.BlockDisplayPresetAsset;
import us.mytheria.blobdesign.entities.PresetData;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.blobdesign.entities.presetblock.PresetBlock;
import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.display.DisplayData;

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
