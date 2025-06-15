package io.github.anjoismysign.blobdesign.entities;

import io.github.anjoismysign.blobdesign.entities.element.BlobDisplayElement;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElement;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementType;
import io.github.anjoismysign.blobdesign.entities.presetblock.PresetBlock;
import io.github.anjoismysign.bloblib.entities.display.DisplayData;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public interface ItemDisplayPreset extends DisplayPreset<ItemDisplay> {

    default ItemDisplayPreset override(DesignDisplayOperator operator) {
        return new ItemDisplayPreset() {

            @Override
            public ItemStack getItemStack() {
                return ItemDisplayPreset.this.getItemStack();
            }

            @Override
            public ItemDisplay.ItemDisplayTransform getTransform() {
                return ItemDisplayPreset.this.getTransform();
            }

            @Override
            public @NotNull DisplayElement<ItemDisplay> instantiateElement(Location location) {
                return new BlobDisplayElement<>(this.instantiateDecorator(location),
                        DisplayElementType.ITEM_DISPLAY, this);
            }

            @Override
            public @NotNull ItemDisplay instantiate(Location location) {
                return ItemDisplayPreset.this.instantiate(location);
            }

            @NotNull
            public PresetBlock<ItemDisplay> instantiatePresetBlock(Location location, String key) {
                return ItemDisplayPreset.this.instantiatePresetBlock(location, key);
            }

            @Override
            public DisplayData getDisplayData() {
                return operator.getDisplayData();
            }

            @Override
            public Transformation getTransformation() {
                return operator.getTransformation();
            }

            @Override
            public void serialize(PersistentDataContainer container) {
                ItemDisplayPreset.this.serialize(container);
            }

            @Override
            public @NotNull PresetData getPresetData() {
                return ItemDisplayPreset.this.getPresetData();
            }

            @Override
            public Plugin getPlugin() {
                return operator.getPlugin();
            }
        };
    }

    /**
     * Gets the ItemStack of the ItemDisplay
     *
     * @return the ItemStack of the ItemDisplay
     */
    ItemStack getItemStack();

    /**
     * Gets the ItemDisplayTransform of the ItemDisplay
     *
     * @return the ItemDisplayTransform of the ItemDisplay
     */
    ItemDisplay.ItemDisplayTransform getTransform();
}
