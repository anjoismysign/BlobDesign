package us.mytheria.blobdesign.entities.proxy;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.blobdesign.entities.ItemDisplayPreset;
import us.mytheria.blobdesign.entities.ItemDisplayPresetAsset;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.display.DisplayData;

import java.io.File;

public class ItemDisplayPresetAssetProxy implements ItemDisplayPreset, BlobObject {
    private final ItemDisplayPresetAsset real;

    protected ItemDisplayPresetAssetProxy(ItemDisplayPresetAsset asset) {
        this.real = asset;
    }

    @Override
    public JavaPlugin getPlugin() {
        return real.getPlugin();
    }

    @Override
    public DisplayData getDisplayData() {
        return real.getDisplayData();
    }

    @Override
    public Transformation getTransformation() {
        return real.getTransformation();
    }

    @Override
    public DisplayElement<ItemDisplay> instantiateElement(Location location) {
        return real.instantiateElement(location);
    }

    @Override
    public ItemDisplay instantiate(Location location) {
        return real.instantiate(location);
    }

    @Override
    public ItemStack getItemStack() {
        return real.getItemStack();
    }

    @Override
    public ItemDisplay.ItemDisplayTransform getTransform() {
        return real.getTransform();
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
