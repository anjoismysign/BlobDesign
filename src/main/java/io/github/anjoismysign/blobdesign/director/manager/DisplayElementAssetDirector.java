package io.github.anjoismysign.blobdesign.director.manager;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.director.DesignObjectDirector;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementAsset;
import io.github.anjoismysign.bloblib.entities.ObjectDirectorData;
import org.bukkit.Bukkit;

public class DisplayElementAssetDirector extends DesignObjectDirector<DisplayElementAsset<?>> {
    public DisplayElementAssetDirector(DesignManagerDirector managerDirector) {
        super(managerDirector, ObjectDirectorData.simple(managerDirector.getRealFileManager(),
                        "DisplayElement"), file ->
                        DisplayElementAsset.fromFile(file, managerDirector),
                false);
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
        getObjectManager().values().forEach(asset -> asset.element().despawn());
    }

    public void ifExistsRemove(String key) {
        DisplayElementAsset<?> existent = getObjectManager().getObject(key);
        if (existent == null)
            return;
        getObjectManager().removeObject(key);
        existent.element().despawn();
    }
}
