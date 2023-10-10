package us.mytheria.blobdesign.entities.blockasset;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.ItemDisplayPreset;
import us.mytheria.bloblib.entities.BukkitPluginOperator;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public class ItemDisplayPresetBlockAsset
        extends PresetBlockAsset<ItemDisplay>
        implements BukkitPluginOperator {
    private final ItemDisplayPreset displayPreset;

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
        super(key, decorator, director);
        this.displayPreset = displayPreset;
    }

    public Location getLocation() {
        return getDecorator().call().getLocation();
    }

    @Override
    public ItemDisplayPreset getDisplayPreset() {
        return displayPreset;
    }

    public Plugin getPlugin() {
        return displayPreset.getPlugin();
    }
}
