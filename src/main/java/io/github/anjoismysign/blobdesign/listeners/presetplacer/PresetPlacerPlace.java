package io.github.anjoismysign.blobdesign.listeners.presetplacer;

import io.github.anjoismysign.blobdesign.BlobDesignAPI;
import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.entities.DisplayPreset;
import io.github.anjoismysign.blobdesign.entities.PresetPlacer;
import io.github.anjoismysign.blobdesign.entities.presetblock.PresetBlock;
import io.github.anjoismysign.blobdesign.listeners.DesignListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PresetPlacerPlace extends DesignListener {
    public PresetPlacerPlace(DesignManagerDirector director) {
        super(director);
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();
        PresetPlacer placer = PresetPlacer.isPresetPlacer(itemStack,
                getManagerDirector().getPluginOperator());
        if (placer == null)
            return;
        DisplayPreset<?> preset = BlobDesignAPI.getPreset(placer.getPresetData());
        if (preset == null)
            return;
        event.setCancelled(true);
        PresetBlock<?> presetBlock;
        try {
            Block placed = event.getBlockPlaced();
            Location location = placed.getLocation();
            presetBlock = preset.instantiatePresetBlock(placed,
                    location.getWorld().getName() + "," + location.toVector());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
