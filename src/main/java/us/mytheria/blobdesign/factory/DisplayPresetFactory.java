package us.mytheria.blobdesign.factory;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import us.mytheria.blobdesign.entities.BlockDisplayPreset;
import us.mytheria.blobdesign.entities.DisplayOperator;
import us.mytheria.blobdesign.entities.DisplayPreset;
import us.mytheria.blobdesign.entities.ItemDisplayPreset;

public class DisplayPresetFactory {
    /**
     * Will create an anonymous class that implements DisplayPreset<ItemDisplay>.
     *
     * @param itemStack The item stack to use.
     * @param transform The transform to use.
     * @param operator  The operator to use.
     * @return The created DisplayPreset<ItemDisplay>.
     */
    public static DisplayPreset<ItemDisplay> ITEM_DISPLAY(ItemStack itemStack,
                                                          ItemDisplay.ItemDisplayTransform transform,
                                                          DisplayOperator operator) {
        return new ItemDisplayPreset(itemStack, transform, operator) {
        };
    }

    /**
     * Will create an anonymous class that implements DisplayPreset<ItemDisplay>.
     *
     * @param blockData The block data to use.
     * @param operator  The operator to use.
     * @return The created DisplayPreset<BlockDisplay>.
     */
    public static DisplayPreset<BlockDisplay> BLOCK_DISPLAY(BlockData blockData,
                                                            DisplayOperator operator) {
        return new BlockDisplayPreset(blockData, operator) {
        };
    }
}
