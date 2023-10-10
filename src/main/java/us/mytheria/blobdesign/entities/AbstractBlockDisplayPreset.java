package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.entities.element.BlobDisplayElement;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.bloblib.entities.display.DisplayData;

public abstract class AbstractBlockDisplayPreset implements BlockDisplayPreset {
    private final BlockData blockData;
    private final DisplayOperator operator;

    public AbstractBlockDisplayPreset(BlockData blockData, DisplayOperator operator) {
        this.blockData = blockData;
        this.operator = operator;
    }

    public BlockData getBlockData() {
        return blockData;
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

    public @NotNull BlockDisplay instantiate(Location location) {
        BlockDisplay blockDisplay = location.getWorld().spawn(location, BlockDisplay.class);
        blockDisplay.setPersistent(false);
        blockDisplay.setBlock(getBlockData());
        getDisplayData().apply(blockDisplay);
        blockDisplay.setTransformation(getTransformation());
        return blockDisplay;
    }

    public @NotNull BlobDisplayElement<BlockDisplay> instantiateElement(Location location) {
        return new BlobDisplayElement<>(instantiateDecorator(location),
                DisplayElementType.BLOCK_DISPLAY, this);
    }
}
