package us.mytheria.blobdesign.entities;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;

public interface BlockDisplayPreset extends DisplayPreset<BlockDisplay> {
    /**
     * Gets the BlockData of the BlockDisplay
     *
     * @return the BlockData of the BlockDisplay
     */
    BlockData getBlockData();
}
