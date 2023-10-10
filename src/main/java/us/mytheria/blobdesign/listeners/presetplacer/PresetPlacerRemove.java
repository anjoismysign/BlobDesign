package us.mytheria.blobdesign.listeners.presetplacer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import us.mytheria.blobdesign.BlobDesignAPI;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.manager.PresetBlockAssetDirector;
import us.mytheria.blobdesign.entities.DisplayPreset;
import us.mytheria.blobdesign.entities.PresetPlacer;
import us.mytheria.blobdesign.entities.blockasset.PresetBlockAsset;
import us.mytheria.blobdesign.listeners.DesignListener;

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
        event.setCancelled(true);
        PresetBlockAssetDirector assetDirector = getManagerDirector().getPresetBlockAssetDirector();
        PresetBlockAsset<?> asset = assetDirector
                .get(broken.getLocation().toVector().toBlockVector());
        if (asset == null)
            return;
        Location location = asset.getLocation().clone();
        assetDirector.remove(asset);
        location.getWorld().spawnParticle(Particle.CLOUD, location, 5);
    }
}
