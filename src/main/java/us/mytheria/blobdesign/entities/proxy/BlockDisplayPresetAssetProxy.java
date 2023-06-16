package us.mytheria.blobdesign.entities.proxy;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.blobdesign.entities.BlockDisplayPreset;
import us.mytheria.blobdesign.entities.BlockDisplayPresetAsset;
import us.mytheria.blobdesign.entities.element.DisplayElement;
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

    public JavaPlugin getPlugin() {
        return real.getPlugin();
    }

    public DisplayData getDisplayData() {
        return real.getDisplayData();
    }

    public Transformation getTransformation() {
        return real.getTransformation();
    }

    public DisplayElement<BlockDisplay> instantiateElement(Location location) {
        return real.instantiateElement(location);
    }

    public BlockDisplay instantiate(Location location) {
        return real.instantiate(location);
    }

    @Override
    public String getKey() {
        return real.getKey();
    }

    @Override
    public File saveToFile(File file) {
        return real.saveToFile(file);
    }
}
