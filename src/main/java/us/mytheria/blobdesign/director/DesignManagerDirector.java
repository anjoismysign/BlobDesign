package us.mytheria.blobdesign.director;

import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.manager.ConfigManager;
import us.mytheria.blobdesign.director.manager.DisplayElementAssetDirector;
import us.mytheria.blobdesign.director.manager.InventoryManager;
import us.mytheria.blobdesign.director.manager.ListenerManager;
import us.mytheria.blobdesign.entities.BlockDisplayPresetAsset;
import us.mytheria.blobdesign.entities.ItemDisplayPresetAsset;
import us.mytheria.blobdesign.entities.element.DisplayElementAsset;
import us.mytheria.blobdesign.entities.inventory.BlockDisplayBuilder;
import us.mytheria.blobdesign.entities.inventory.ItemDisplayBuilder;
import us.mytheria.blobdesign.entities.proxy.DesignProxier;
import us.mytheria.bloblib.entities.GenericManagerDirector;
import us.mytheria.bloblib.entities.ObjectDirector;
import us.mytheria.bloblib.entities.ObjectDirectorData;

public class DesignManagerDirector extends GenericManagerDirector<BlobDesign> {
    private DisplayElementAssetDirector displayElementAssetDirector;

    public DesignManagerDirector(BlobDesign plugin) {
        super(plugin);
        registerAndUpdateBlobInventory("BlockDisplayEditor");
        registerAndUpdateBlobInventory("BlockDisplayNavigator");
        registerAndUpdateBlobInventory("ItemDisplayEditor");
        registerAndUpdateBlobInventory("ItemDisplayNavigator");
        addManager("ConfigManager", new ConfigManager(this));
        addManager("InventoryManager", new InventoryManager(this));
        addManager("ListenerManager", new ListenerManager(this));
        addDirector("BlockDisplay", file ->
                DesignProxier.PROXY(BlockDisplayPresetAsset.fromFile(file, plugin)));
        getBlockDisplayAssetDirector().getBuilderManager()
                .setBuilderBiFunction((uuid, objectDirector) ->
                        BlockDisplayBuilder.build(uuid, objectDirector,
                                this));
        getBlockDisplayAssetDirector().whenObjectManagerFilesLoad(blockDisplayAssetObjectManager -> {
            addDirector("ItemDisplay", file -> DesignProxier
                    .PROXY(ItemDisplayPresetAsset.fromFile(file, plugin)));
            getItemDisplayAssetDirector().getBuilderManager()
                    .setBuilderBiFunction((uuid, objectDirector) ->
                            ItemDisplayBuilder.build(uuid, objectDirector,
                                    this));
            getItemDisplayAssetDirector().whenObjectManagerFilesLoad(itemDisplayAssetObjectManager -> {
                String objectName = "DisplayElement";
                ObjectDirectorData quickWarpData = ObjectDirectorData.simple(this.getFileManager(), objectName);
                DesignManagerDirector.this.displayElementAssetDirector = new DisplayElementAssetDirector(this,
                        quickWarpData, file ->
                        DisplayElementAsset.fromFile(file, this));
                this.addManager(objectName + "Director",
                        displayElementAssetDirector);
            });
        });
    }

    /**
     * From top to bottom, follow the order.
     */
    @Override
    public void reload() {
        getBlockDisplayAssetDirector().reload();
        getBlockDisplayAssetDirector().whenObjectManagerFilesLoad(blockDisplayAssetObjectManager -> {
            getItemDisplayAssetDirector().reload();
            getItemDisplayAssetDirector().whenObjectManagerFilesLoad(itemDisplayAssetObjectManager -> {
                getDisplayElementAssetDirector().reload();
            });
        });
    }

    @Override
    public void unload() {
        getDisplayElementAssetDirector().unload();
    }

    public final ObjectDirector<BlockDisplayPresetAsset> getBlockDisplayAssetDirector() {
        return getDirector("BlockDisplay", BlockDisplayPresetAsset.class);
    }

    public final ObjectDirector<ItemDisplayPresetAsset> getItemDisplayAssetDirector() {
        return getDirector("ItemDisplay", ItemDisplayPresetAsset.class);
    }

    public final DisplayElementAssetDirector getDisplayElementAssetDirector() {
        return displayElementAssetDirector;
    }

    public final ConfigManager getConfigManager() {
        return getManager("ConfigManager", ConfigManager.class);
    }

    public final InventoryManager getInventoryManager() {
        return getManager("InventoryManager", InventoryManager.class);
    }

    public final ListenerManager getListenerManager() {
        return getManager("ListenerManager", ListenerManager.class);
    }
}