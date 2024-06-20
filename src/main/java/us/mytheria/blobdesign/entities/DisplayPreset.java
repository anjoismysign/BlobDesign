package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.blobdesign.entities.presetblock.PresetBlock;
import us.mytheria.bloblib.entities.display.DisplayDecorator;
import us.mytheria.bloblib.entities.display.DisplayWriter;

public interface DisplayPreset<T extends Display> extends DesignDisplayOperator, PresetPlacer {

    /**
     * Will make an instance of the Display entity at given Location
     * as an Element. Will automatically save the element to the
     * ObjectManager. Else will fail fast.
     *
     * @param location Location to spawn the Display entity
     * @return the Display entity instance
     */
    @NotNull
    DisplayElement<T> instantiateElement(Location location);

    /**
     * Will make an instance of the Display entity at given Entity's Location
     * as an Element. Will automatically save the element to the
     * ObjectManager. Else will fail fast.
     *
     * @param entity Entity where to spawn the Display entity
     * @return the Display entity instance
     */
    @NotNull
    default DisplayElement<T> instantiateElement(Entity entity) {
        Block block = entity.getLocation().getBlock();
        return instantiateElement(block);
    }

    /**
     * Will make an instance of the Display entity at given Block's Location
     * as an Element. Will automatically save the element to the
     * ObjectManager. Else will fail fast.
     *
     * @param block Block where to spawn the Display entity
     * @return the Display entity instance
     */
    @NotNull
    default DisplayElement<T> instantiateElement(Block block) {
        Location location = block.getLocation().clone();
        location.add(new Vector(0.5, 0.5, 0.5));
        return instantiateElement(location);
    }

    /**
     * Will make an instance of the Display entity at given Location.
     * Else will fail fast.
     *
     * @param location Location to spawn the Display entity
     * @return the Display entity instance
     */
    @NotNull
    T instantiate(Location location);

    /**
     * Will make an instance of the Display entity at given Location.
     * Else will fail fast.
     *
     * @param location Location to spawn the Display entity
     * @return the Display entity instance
     */
    @NotNull
    default DisplayDecorator<T> instantiateDecorator(Location location) {
        return new DisplayDecorator<>(instantiate(location), getPlugin());
    }

    /**
     * Will make an instance of the Display entity at given Entity's Location.
     * Else will fail fast.
     *
     * @param entity Entity where to spawn the Display entity
     * @return the Display entity instance
     */
    @NotNull
    default T instantiate(Entity entity) {
        Block block = entity.getLocation().getBlock();
        return instantiate(block);
    }

    /**
     * Will make an instance of the Display entity at given Location.
     * Else will fail fast.
     *
     * @param block Block where to spawn the Display entity
     * @return the Display entity instance
     */
    @NotNull
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

    /**
     * Will make an instance of the Display entity at given Location
     * as a PresetBlock. Will automatically save the element to the
     * ObjectManager. Else will fail fast.
     *
     * @param location The location to instantiate the PresetBlock at.
     * @param key      The key to use for the PresetBlockAsset.
     * @return The PresetBlock that was instantiated.
     */
    @NotNull
    PresetBlock<T> instantiatePresetBlock(Location location,
                                          String key);

    /**
     * Will make an instance of the Display entity at given Entity's Location
     * as a PresetBlock. Will automatically save the element to the
     * ObjectManager. Else will fail fast.
     *
     * @param entity The entity to instantiate the PresetBlock at.
     * @param key    The key to use for the PresetBlockAsset.
     * @return The PresetBlock that was instantiated.
     */
    @NotNull
    default PresetBlock<T> instantiatePresetBlock(Entity entity,
                                                  String key) {
        return instantiatePresetBlock(entity.getLocation().getBlock(), key);
    }

    /**
     * Will make an instance of the Display entity at given Block's Location
     * as a PresetBlock. Will automatically save the element to the
     * ObjectManager. Else will fail fast.
     *
     * @param block The block to instantiate the PresetBlock at.
     * @param key   The key to use for the PresetBlockAsset.
     * @return The PresetBlock that was instantiated.
     */
    @NotNull
    default PresetBlock<T> instantiatePresetBlock(Block block,
                                                  String key) {
        Location location = block.getLocation().clone();
        location.add(new Vector(0.5, 0.5, 0.5));
        return instantiatePresetBlock(location, key);
    }
}