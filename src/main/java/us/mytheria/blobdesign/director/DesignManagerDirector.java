package us.mytheria.blobdesign.director;

import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.manager.ConfigManager;
import us.mytheria.blobdesign.director.manager.ListenerManager;
import us.mytheria.blobdesign.entities.BlockDisplayAsset;
import us.mytheria.blobdesign.entities.ItemDisplayAsset;
import us.mytheria.blobdesign.entities.inventory.BlockDisplayBuilder;
import us.mytheria.blobdesign.entities.inventory.ItemDisplayBuilder;
import us.mytheria.bloblib.entities.GenericManagerDirector;
import us.mytheria.bloblib.entities.ObjectDirector;

public class DesignManagerDirector extends GenericManagerDirector<BlobDesign> {
    public DesignManagerDirector(BlobDesign plugin) {
        super(plugin);
        addDirector("BlockDisplay", BlockDisplayAsset::fromFile);
        getBlockDisplayModelDirector().getBuilderManager()
                .setBuilderBiFunction(BlockDisplayBuilder::build);
        addDirector("ItemDisplay", ItemDisplayAsset::fromFile);
        getItemDisplayModelDirector().getBuilderManager()
                .setBuilderBiFunction(ItemDisplayBuilder::build);
        addManager("ConfigManager", new ConfigManager(this));
        addManager("ListenerManager", new ListenerManager(this));
    }

    /**
     * From top to bottom, follow the order.
     */
    @Override
    public void reload() {
        getBlockDisplayModelDirector().reload();
        getItemDisplayModelDirector().reload();
    }

    public final ObjectDirector<BlockDisplayAsset> getBlockDisplayModelDirector() {
        return getDirector("BlockDisplay", BlockDisplayAsset.class);
    }

    public final ObjectDirector<ItemDisplayAsset> getItemDisplayModelDirector() {
        return getDirector("ItemDisplay", ItemDisplayAsset.class);
    }

    public final ConfigManager getConfigManager() {
        return getManager("ConfigManager", ConfigManager.class);
    }

    public final ListenerManager getListenerManager() {
        return getManager("ListenerManager", ListenerManager.class);
    }
}