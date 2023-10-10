package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.entities.element.BlobDisplayElement;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.bloblib.entities.display.DisplayData;

public abstract class AbstractItemDisplayPreset implements ItemDisplayPreset {
    private final ItemStack itemStack;
    private final ItemDisplay.ItemDisplayTransform transform;
    private final DisplayOperator operator;

    public AbstractItemDisplayPreset(ItemStack itemStack,
                                     ItemDisplay.ItemDisplayTransform transform,
                                     DisplayOperator operator) {
        this.itemStack = itemStack;
        this.transform = transform;
        this.operator = operator;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemDisplay.ItemDisplayTransform getTransform() {
        return transform;
    }

    public Plugin getPlugin() {
        return operator.getPlugin();
    }

    public DisplayData getDisplayData() {
        return operator.getDisplayData();
    }

    public Transformation getTransformation() {
        return operator.getTransformation();
    }

    public @NotNull ItemDisplay instantiate(Location location) {
        ItemDisplay itemDisplay = location.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setPersistent(false);
        itemDisplay.setItemStack(getItemStack());
        itemDisplay.setItemDisplayTransform(getTransform());
        getDisplayData().apply(itemDisplay);
        itemDisplay.setTransformation(getTransformation());
        return itemDisplay;
    }

    @NotNull
    public BlobDisplayElement<ItemDisplay> instantiateElement(Location location) {
        return new BlobDisplayElement<>(instantiateDecorator(location),
                DisplayElementType.ITEM_DISPLAY, this);
    }
}
