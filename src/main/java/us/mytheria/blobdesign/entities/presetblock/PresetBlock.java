package us.mytheria.blobdesign.entities.presetblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public interface PresetBlock<T extends Display> extends DisplayElement<T> {

    Location getLocation();

    /**
     * Will return the reference of the asset in the world.
     *
     * @return The reference of the asset
     */
    default BlockVector reference() {
        return getLocation().getBlock().getLocation().toVector().toBlockVector();
    }

    @Nullable
    default World getWorld() {
        return getLocation().getWorld();
    }

    /**
     * Will return the id of the asset in the server.
     *
     * @return The id of the asset
     */
    default String id() {
        return getWorld().getName() + "," + reference().toString();
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
        despawn(true);
    }

    /**
     * Will despawn the entity (as the Minecraft entity that's
     * inside the Minecraft World) associated with this asset.
     *
     * @param removeBlock Whether to remove the block that's in the
     *                    location of this asset.
     */
    default void despawn(boolean removeBlock) {
        if (removeBlock)
            getLocation().getBlock().setType(Material.AIR);
        DisplayDecorator<?> decorator = getDecorator();
        decorator.stopClock();
        decorator.call().remove();
    }
}
