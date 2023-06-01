package us.mytheria.blobdesign.entities.element;

import org.bukkit.entity.Display;
import us.mytheria.blobdesign.entities.DisplayAsset;

public class Element<T extends Display> {
    private T element;
    private ElementType type;
    private DisplayAsset<T> asset;
}
