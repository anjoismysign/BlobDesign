package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.bloblib.entities.display.DisplayWriter;

public interface DisplayPreset<T extends Display> extends DisplayOperator {

    /**
     * Will make an instance of the Display entity at given Location
     * as an Element
     *
     * @param location Location to spawn the Display entity
     * @return the Display entity instance
     */
    DisplayElement<T> instantiateElement(Location location);

    /**
     * Will make an instance of the Display entity at given Entity's Location
     * as an Element
     *
     * @param entity Entity where to spawn the Display entity
     * @return the Display entity instance
     */
    default DisplayElement<T> instantiateElement(Entity entity) {
        Block block = entity.getLocation().getBlock();
        return instantiateElement(block);
    }

    /**
     * Will make an instance of the Display entity at given Block's Location
     * as an Element
     *
     * @param block Block where to spawn the Display entity
     * @return the Display entity instance
     */
    default DisplayElement<T> instantiateElement(Block block) {
        Location location = block.getLocation().clone();
        location.add(new Vector(0.5, 0.5, 0.5));
        return instantiateElement(location);
    }

    /**
     * Will make an instance of the Display entity at given Location
     *
     * @param location Location to spawn the Display entity
     * @return the Display entity instance
     */
    T instantiate(Location location);

    /**
     * Will make an instance of the Display entity at given Entity's Location
     *
     * @param entity Entity where to spawn the Display entity
     * @return the Display entity instance
     */
    default T instantiate(Entity entity) {
        Block block = entity.getLocation().getBlock();
        return instantiate(block);
    }

    /**
     * Will make an instance of the Display entity at given Location
     *
     * @param block Block where to spawn the Display entity
     * @return the Display entity instance
     */
    default T instantiate(Block block) {
        Location location = block.getLocation().clone();
        location.add(new Vector(0.5, 0.5, 0.5));
        return instantiate(location);
    }

    /**
     * Will write the Transformation and DisplayData to the ConfigurationSection.
     *
     * @param section ConfigurationSection to write to
     */
    default void writePreset(ConfigurationSection section) {
        DisplayWriter.WRITE(section, getTransformation());
        getDisplayData().write(section.createSection("Display-Data"));
    }
}