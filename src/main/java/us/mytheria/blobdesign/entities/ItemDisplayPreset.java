package us.mytheria.blobdesign.entities;

import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public interface ItemDisplayPreset extends DisplayPreset<ItemDisplay> {
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
