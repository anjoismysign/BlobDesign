package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.entities.element.BlobDisplayElement;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.blobdesign.entities.presetblock.PresetBlock;
import us.mytheria.bloblib.entities.display.DisplayData;

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
            public PresetBlock<ItemDisplay> instantiateBlockAsset(Location location, String key) {
                return ItemDisplayPreset.this.instantiateBlockAsset(location, key);
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
