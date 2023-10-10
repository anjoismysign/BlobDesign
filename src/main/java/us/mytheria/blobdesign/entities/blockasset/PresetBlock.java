package us.mytheria.blobdesign.entities.blockasset;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.util.BlockVector;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public interface PresetBlock<T extends Display> extends DisplayElement<T> {

    Location getLocation();

    default BlockVector reference() {
        return getLocation().getBlock().getLocation().toVector().toBlockVector();
    }

    default PresetBlock<T> getPresetBlock() {
        return this;
    }

    /**
     * Will despawn the entity (as the Minecraft entity that's
     * inside the Minecraft World) associated with this asset.
     * Will also remove the block that's in the location of this
     * asset.
     */
    @Override
    default void despawn() {
        getLocation().getBlock().setType(Material.AIR);
        DisplayDecorator<?> decorator = getDecorator();
        decorator.stopClock();
        decorator.call().remove();
    }
}
