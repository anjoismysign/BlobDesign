package io.github.anjoismysign.blobdesign.entities.presetblock;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.entities.BlockDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementType;
import io.github.anjoismysign.bloblib.entities.BlobObject;
import io.github.anjoismysign.bloblib.entities.BukkitPluginOperator;
import io.github.anjoismysign.bloblib.entities.display.DisplayDecorator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BlockDisplayPresetBlockAsset
        extends PresetBlockAsset<BlockDisplay>
        implements BlobObject, BukkitPluginOperator {

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
        super(key, decorator, displayPreset, director);
    }

    @Override
    public Location getLocation() {
        return getDecorator().call().getLocation();
    }

    @Override
    public @NotNull DisplayElementType getType() {
        return DisplayElementType.BLOCK_DISPLAY;
    }

    public Plugin getPlugin() {
        return getDisplayPreset().getPlugin();
    }
}
