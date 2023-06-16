package us.mytheria.blobdesign.entities;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.manager.DisplayElementAssetDirector;
import us.mytheria.blobdesign.entities.element.DisplayElement;
import us.mytheria.blobdesign.entities.element.DisplayElementAsset;
import us.mytheria.bloblib.entities.BlobObject;
import us.mytheria.bloblib.entities.ObjectManager;

public interface DesignDisplayPreset<T extends Display> extends DisplayPreset<T>,
        BlobObject {
    DesignManagerDirector getManagerDirector();

    /**
     * Will instantiate a DisplayElementAsset and save it to the ObjectManager.
     *
     * @param location       The location to instantiate the DisplayElement at.
     * @param key            The key to use for the DisplayElementAsset.
     * @param overridePreset Whether to override the preset file.
     * @return The DisplayElementAsset that was instantiated.
     */
    default DisplayElementAsset<T> instantiateElementAsset(Location location,
                                                           String key,
                                                           boolean overridePreset) {
        DisplayElement<T> element = instantiateElement(location);
        DisplayElementAssetDirector elementAssetDirector = getManagerDirector()
                .getDisplayElementAssetDirector();
        ObjectManager<DisplayElementAsset<?>> objectManager = elementAssetDirector
                .getObjectManager();
        DisplayElementAsset<T> asset =
                new DisplayElementAsset<>(element, key, location);
        elementAssetDirector.ifExistsRemove(key);
        objectManager.addObject(key, asset);
        asset.saveToFile(objectManager.getLoadFilesDirectory(), overridePreset);
        return asset;
    }

    /**
     * Will instantiate a DisplayElementAsset and save it to the ObjectManager.
     * Will not override the preset file, saving storage space by storing the reference.
     *
     * @param location The location to instantiate the DisplayElement at.
     * @param key      The key to use for the DisplayElementAsset.
     * @return The DisplayElementAsset that was instantiated.
     */
    default DisplayElementAsset<T> instantiateElementAsset(Location location,
                                                           String key) {
        return instantiateElementAsset(location, key, false);
    }
}
