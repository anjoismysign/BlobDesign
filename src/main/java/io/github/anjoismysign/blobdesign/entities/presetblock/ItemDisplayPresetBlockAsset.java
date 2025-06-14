package io.github.anjoismysign.blobdesign.entities.presetblock;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.entities.ItemDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementType;
import io.github.anjoismysign.bloblib.entities.BukkitPluginOperator;
import io.github.anjoismysign.bloblib.entities.display.DisplayDecorator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ItemDisplayPresetBlockAsset
        extends PresetBlockAsset<ItemDisplay>
        implements BukkitPluginOperator {

    /**
     * Will create a new ItemDisplayBlockAsset with the given preset, key, and location.
     * Will place a barrier block at the location.
     *
     * @param displayPreset the preset to use
     * @param key           the key to use
     * @param location      the location to use
     * @return the new ItemDisplayBlockAsset
     * @throws RuntimeException thrown when fail fasting
     */
    @NotNull
    public static ItemDisplayPresetBlockAsset of(@NotNull ItemDisplayPreset displayPreset,
                                                 @NotNull String key,
                                                 @NotNull Location location,
                                                 @NotNull DesignManagerDirector director) throws RuntimeException {
        displayPreset.runTask(() -> location.getBlock().setType(Material.BARRIER));
        DisplayDecorator<ItemDisplay> decorator = displayPreset.instantiateDecorator(location);
        return new ItemDisplayPresetBlockAsset(key,
                displayPreset,
                decorator, director);
    }

    private ItemDisplayPresetBlockAsset(String key,
                                        ItemDisplayPreset displayPreset,
                                        DisplayDecorator<ItemDisplay> decorator,
                                        DesignManagerDirector director) {
        super(key, decorator, displayPreset, director);
    }

    public Location getLocation() {
        return getDecorator().call().getLocation();
    }

    @Override
    public @NotNull DisplayElementType getType() {
        return DisplayElementType.ITEM_DISPLAY;
    }

    public Plugin getPlugin() {
        return getDisplayPreset().getPlugin();
    }
}
