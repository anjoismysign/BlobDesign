package io.github.anjoismysign.blobdesign.entities.proxy;

import io.github.anjoismysign.blobdesign.entities.ItemDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.ItemDisplayPresetAsset;
import io.github.anjoismysign.blobdesign.entities.PresetData;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElement;
import io.github.anjoismysign.blobdesign.entities.presetblock.PresetBlock;
import io.github.anjoismysign.bloblib.entities.BlobObject;
import io.github.anjoismysign.bloblib.entities.display.DisplayData;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ItemDisplayPresetAssetProxy implements ItemDisplayPreset, BlobObject {
    private final ItemDisplayPresetAsset real;

    protected ItemDisplayPresetAssetProxy(ItemDisplayPresetAsset asset) {
        this.real = asset;
    }

    @Override
    public Plugin getPlugin() {
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
    public @NotNull DisplayElement<ItemDisplay> instantiateElement(Location location) {
        return real.instantiateElement(location);
    }

    @Override
    public @NotNull ItemDisplay instantiate(Location location) {
        return real.instantiate(location);
    }

    @NotNull
    public PresetBlock<ItemDisplay> instantiatePresetBlock(Location location, String key) {
        return real.instantiatePresetBlock(location, key);
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

    @Override
    public void serialize(PersistentDataContainer container) {
        real.serialize(container);
    }

    @Override
    public @NotNull PresetData getPresetData() {
        return real.getPresetData();
    }
}
