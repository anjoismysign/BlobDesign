package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.blobdesign.entities.element.BlobDisplayElement;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

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

    public JavaPlugin getPlugin() {
        return operator.getPlugin();
    }

    public DisplayData getDisplayData() {
        return operator.getDisplayData();
    }

    public Transformation getTransformation() {
        return operator.getTransformation();
    }

    public ItemDisplay instantiate(Location location) {
        ItemDisplay itemDisplay = location.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setPersistent(false);
        itemDisplay.setItemStack(itemStack);
        itemDisplay.setItemDisplayTransform(transform);
        operator.getDisplayData().apply(itemDisplay);
        itemDisplay.setTransformation(operator.getTransformation());
        return itemDisplay;
    }

    public BlobDisplayElement<ItemDisplay> instantiateElement(Location location, JavaPlugin plugin) {
        return new BlobDisplayElement<>(new DisplayDecorator<>(instantiate(location), plugin),
                DisplayElementType.ITEM_DISPLAY, this);
    }

    public BlobDisplayElement<ItemDisplay> instantiateElement(Location location) {
        return instantiateElement(location, getPlugin());
    }
}
