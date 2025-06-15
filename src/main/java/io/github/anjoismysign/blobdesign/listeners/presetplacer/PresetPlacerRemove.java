package io.github.anjoismysign.blobdesign.listeners.presetplacer;

import io.github.anjoismysign.blobdesign.BlobDesignAPI;
import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.director.manager.PresetBlockAssetDirector;
import io.github.anjoismysign.blobdesign.entities.DisplayPreset;
import io.github.anjoismysign.blobdesign.entities.PresetPlacer;
import io.github.anjoismysign.blobdesign.entities.presetblock.PresetBlockAsset;
import io.github.anjoismysign.blobdesign.listeners.DesignListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class PresetPlacerRemove extends DesignListener {
    public PresetPlacerRemove(DesignManagerDirector director) {
        super(director);
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onPlace(BlockBreakEvent event) {
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
        PresetPlacer placer = PresetPlacer.isPresetPlacer(itemStack,
                getManagerDirector().getPluginOperator());
        if (placer == null)
            return;
        DisplayPreset<?> preset = BlobDesignAPI.getPreset(placer.getPresetData());
        if (preset == null)
            return;
        Block broken = event.getBlock();
        World world = broken.getWorld();
        event.setCancelled(true);
        PresetBlockAssetDirector assetDirector = getManagerDirector().getPresetBlockAssetDirector();
        PresetBlockAsset<?> asset = assetDirector
                .get(world, broken.getLocation().toVector().toBlockVector());
        if (asset == null)
            return;
        Location location = asset.getLocation().clone();
        assetDirector.remove(asset);
        location.getWorld().spawnParticle(Particle.CLOUD, location, 5);
    }
}
