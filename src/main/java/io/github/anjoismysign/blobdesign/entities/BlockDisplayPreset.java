package io.github.anjoismysign.blobdesign.entities;

import io.github.anjoismysign.blobdesign.entities.element.BlobDisplayElement;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElement;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementType;
import io.github.anjoismysign.blobdesign.entities.presetblock.PresetBlock;
import io.github.anjoismysign.bloblib.entities.display.DisplayData;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public interface BlockDisplayPreset extends DisplayPreset<BlockDisplay> {

    default BlockDisplayPreset override(DesignDisplayOperator operator) {
        return new BlockDisplayPreset() {
            @Override
            public BlockData getBlockData() {
                return BlockDisplayPreset.this.getBlockData();
            }

            @Override
            public @NotNull DisplayElement<BlockDisplay> instantiateElement(Location location) {
                return new BlobDisplayElement<>(this.instantiateDecorator(location),
                        DisplayElementType.BLOCK_DISPLAY, this);
            }

            @Override
            public @NotNull BlockDisplay instantiate(Location location) {
                return BlockDisplayPreset.this.instantiate(location);
            }

            @NotNull
            public PresetBlock<BlockDisplay> instantiatePresetBlock(Location location, String key) {
                return BlockDisplayPreset.this.instantiatePresetBlock(location, key);
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
                BlockDisplayPreset.this.serialize(container);
            }

            @Override
            public @NotNull PresetData getPresetData() {
                return BlockDisplayPreset.this.getPresetData();
            }

            @Override
            public Plugin getPlugin() {
                return operator.getPlugin();
            }
        };
    }

    /**
     * Gets the BlockData of the BlockDisplay
     *
     * @return the BlockData of the BlockDisplay
     */
    BlockData getBlockData();
}
