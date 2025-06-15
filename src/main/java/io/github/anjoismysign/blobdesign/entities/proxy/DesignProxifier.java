package io.github.anjoismysign.blobdesign.entities.proxy;

import io.github.anjoismysign.blobdesign.entities.BlockDisplayPresetAsset;
import io.github.anjoismysign.blobdesign.entities.ItemDisplayPresetAsset;

public class DesignProxifier {
    public static BlockDisplayPresetAssetProxy PROXY(BlockDisplayPresetAsset asset) {
        return new BlockDisplayPresetAssetProxy(asset);
    }

    public static ItemDisplayPresetAssetProxy PROXY(ItemDisplayPresetAsset asset) {
        return new ItemDisplayPresetAssetProxy(asset);
    }
}
