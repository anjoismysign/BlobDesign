package us.mytheria.blobdesign.director.manager;

import org.bukkit.Bukkit;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.DesignObjectDirector;
import us.mytheria.blobdesign.entities.element.DisplayElementAsset;
import us.mytheria.bloblib.entities.ObjectDirectorData;

import java.io.File;
import java.util.function.Function;

public class DisplayElementAssetDirector extends DesignObjectDirector<DisplayElementAsset<?>> {
    public DisplayElementAssetDirector(DesignManagerDirector managerDirector,
                                       ObjectDirectorData objectDirectorData,
                                       Function<File, DisplayElementAsset<?>> readFunction) {
        super(managerDirector, objectDirectorData, readFunction, false);
    }

    @Override
    public void reload() {
        Bukkit.getScheduler().runTask(getPlugin(), () -> {
            unload();
            super.reload();
        });
    }

    @Override
    public void unload() {
        getObjectManager().values().forEach(DisplayElementAsset::despawn);
    }

    public void ifExistsRemove(String key) {
        DisplayElementAsset<?> existent = getObjectManager().getObject(key);
        if (existent == null)
            return;
        getObjectManager().removeObject(key);
        existent.despawn();
    }
}
