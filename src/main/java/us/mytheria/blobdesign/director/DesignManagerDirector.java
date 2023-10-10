package us.mytheria.blobdesign.director;

import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.manager.*;
import us.mytheria.bloblib.entities.GenericManagerDirector;

public class DesignManagerDirector extends GenericManagerDirector<BlobDesign> {
    private final BlockDisplayPresetAssetDirector blockDisplayAssetDirector;
    private ItemDisplayPresetAssetDirector itemDisplayAssetDirector;
    private DisplayElementAssetDirector displayElementAssetDirector;
    private PresetBlockAssetDirector presetBlockAssetDirector;

    public DesignManagerDirector(BlobDesign plugin) {
        super(plugin);
        registerBlobInventory("BlockDisplayEditor",
                "BlockDisplayNavigator",
                "ItemDisplayEditor",
                "ItemDisplayNavigator");
        addManager("ConfigManager", new ConfigManager(this));
        addManager("InventoryManager", new InventoryManager(this));
        addManager("ListenerManager", new ListenerManager(this));
        this.blockDisplayAssetDirector = new BlockDisplayPresetAssetDirector(this);
        this.addManager("BlockDisplayDirector",
                blockDisplayAssetDirector);
        getBlockDisplayAssetDirector().whenObjectManagerFilesLoad(blockDisplayAssetObjectManager -> {
            itemDisplayAssetDirector = new ItemDisplayPresetAssetDirector(this);
            this.addManager("ItemDisplayDirector",
                    itemDisplayAssetDirector);
            getItemDisplayAssetDirector().whenObjectManagerFilesLoad(displayAssetProxy -> {
                displayElementAssetDirector = new DisplayElementAssetDirector(this);
                this.addManager("DisplayElementDirector",
                        displayElementAssetDirector);
                getDisplayElementAssetDirector().whenObjectManagerFilesLoad(displayElement -> {
                    presetBlockAssetDirector = new PresetBlockAssetDirector(this);
                    this.addManager("PresetBlockDirector",
                            presetBlockAssetDirector);
                });
            });
        });
    }

    /**
     * From top to bottom, follow the order.
     */
    @Override
    public void reload() {
        getBlockDisplayAssetDirector().reload();
        getBlockDisplayAssetDirector().whenObjectManagerFilesLoad(blockDisplay -> {
            getItemDisplayAssetDirector().reload();
            getItemDisplayAssetDirector().whenObjectManagerFilesLoad(itemDisplay -> {
                getDisplayElementAssetDirector().reload();
                getDisplayElementAssetDirector().whenObjectManagerFilesLoad(displayElement -> {
                    getPresetBlockAssetDirector().reload();
                });
            });
        });
    }

    @Override
    public void unload() {
        getDisplayElementAssetDirector().unload();
    }

    public final BlockDisplayPresetAssetDirector getBlockDisplayAssetDirector() {
        return blockDisplayAssetDirector;
    }

    public final ItemDisplayPresetAssetDirector getItemDisplayAssetDirector() {
        return itemDisplayAssetDirector;
    }

    public final DisplayElementAssetDirector getDisplayElementAssetDirector() {
        return displayElementAssetDirector;
    }

    public final PresetBlockAssetDirector getPresetBlockAssetDirector() {
        return presetBlockAssetDirector;
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