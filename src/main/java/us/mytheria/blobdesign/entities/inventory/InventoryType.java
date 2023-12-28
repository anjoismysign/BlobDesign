package us.mytheria.blobdesign.entities.inventory;

import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.api.BlobLibInventoryAPI;
import us.mytheria.bloblib.entities.inventory.InventoryButton;
import us.mytheria.bloblib.entities.inventory.InventoryDataRegistry;

import java.util.Objects;

public enum InventoryType {
    BLOCK_DISPLAY_EDITOR("BlockDisplayEditor"),
    BLOCK_DISPLAY_NAVIGATOR("BlockDisplayNavigator"),
    ITEM_DISPLAY_EDITOR("ItemDisplayEditor"),
    ITEM_DISPLAY_NAVIGATOR("ItemDisplayNavigator");

    private final String registryKey;

    InventoryType(String carrierKey) {
        this.registryKey = carrierKey;
    }

    public String getRegistryKey() {
        return registryKey;
    }

    @NotNull
    public InventoryDataRegistry<InventoryButton> getRegistry() {
        return Objects.requireNonNull(BlobLibInventoryAPI.getInstance().getInventoryDataRegistry(registryKey));
    }
}