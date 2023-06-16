package us.mytheria.blobdesign.entities.proxy;

import us.mytheria.blobdesign.entities.BlockDisplayPresetAsset;
import us.mytheria.blobdesign.entities.ItemDisplayPresetAsset;

public class DesignProxier {
    public static BlockDisplayPresetAssetProxy PROXY(BlockDisplayPresetAsset asset) {
        return new BlockDisplayPresetAssetProxy(asset);
    }

    public static ItemDisplayPresetAssetProxy PROXY(ItemDisplayPresetAsset asset) {
        return new ItemDisplayPresetAssetProxy(asset);
    }
}
