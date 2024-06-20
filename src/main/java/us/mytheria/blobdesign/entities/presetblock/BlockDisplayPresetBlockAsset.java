package us.mytheria.blobdesign.entities.presetblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.BlockDisplayPreset;
import us.mytheria.blobdesign.entities.element.DisplayElementType;
import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.BukkitPluginOperator;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public class BlockDisplayPresetBlockAsset
        extends PresetBlockAsset<BlockDisplay>
        implements BlobObject, BukkitPluginOperator {
    private final BlockDisplayPreset displayPreset;

    /**
     * Will create a new BlockDisplayBlockAsset with the given preset, key, and location.
     * Will place a barrier block at the location.
     *
     * @param displayPreset the preset to use
     * @param key           the key to use
     * @param location      the location to use
     * @return the new BlockDisplayBlockAsset
     * @throws RuntimeException thrown when fail fasting
     */
    @NotNull
    public static BlockDisplayPresetBlockAsset of(@NotNull BlockDisplayPreset displayPreset,
                                                  @NotNull String key,
                                                  @NotNull Location location,
                                                  @NotNull DesignManagerDirector director) throws RuntimeException {
        displayPreset.runTask(() -> location.getBlock().setType(Material.BARRIER));
        DisplayDecorator<BlockDisplay> decorator = displayPreset.instantiateDecorator(location);
        return new BlockDisplayPresetBlockAsset(key,
                displayPreset,
                decorator, director);
    }

    private BlockDisplayPresetBlockAsset(String key,
                                         BlockDisplayPreset displayPreset,
                                         DisplayDecorator<BlockDisplay> decorator,
                                         DesignManagerDirector director) {
        super(key, decorator, director);
        this.displayPreset = displayPreset;
    }

    @Override
    public Location getLocation() {
        return getDecorator().call().getLocation();
    }

    @Override
    public DisplayElementType getType() {
        return DisplayElementType.BLOCK_DISPLAY;
    }

    @Override
    public BlockDisplayPreset getDisplayPreset() {
        return displayPreset;
    }

    public Plugin getPlugin() {
        return displayPreset.getPlugin();
    }
}
