package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import us.mytheria.blobdesign.entities.element.BlobDisplayElement;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public abstract class BlockDisplayPreset implements DisplayPreset<BlockDisplay> {
    private final BlockData blockData;
    private final DisplayOperator operator;

    public BlockDisplayPreset(BlockData blockData, DisplayOperator operator) {
        this.blockData = blockData;
        this.operator = operator;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    @Override
    public JavaPlugin getPlugin() {
        return operator.getPlugin();
    }

    @Override
    public DisplayData getDisplayData() {
        return operator.getDisplayData();
    }

    @Override
    public Transformation getTransformation() {
        return operator.getTransformation();
    }

    public BlockDisplay instantiate(Location location) {
        BlockDisplay blockDisplay = location.getWorld().spawn(location, BlockDisplay.class);
        blockDisplay.setPersistent(false);
        blockDisplay.setBlock(blockData);
        operator.getDisplayData().apply(blockDisplay);
        blockDisplay.setTransformation(operator.getTransformation());
        return blockDisplay;
    }

    public BlobDisplayElement<BlockDisplay> instantiateElement(Location location, JavaPlugin plugin) {
        return new BlobDisplayElement<>(new DisplayDecorator<>(instantiate(location), plugin),
                DisplayElementType.BLOCK_DISPLAY, this);
    }

    public BlobDisplayElement<BlockDisplay> instantiateElement(Location location) {
        return instantiateElement(location, getPlugin());
    }
}
