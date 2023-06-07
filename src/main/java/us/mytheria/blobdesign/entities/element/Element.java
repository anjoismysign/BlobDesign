package us.mytheria.blobdesign.entities.element;

import org.bukkit.entity.Display;
import us.mytheria.blobdesign.entities.DisplayAsset;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

public record Element<T extends Display>(DisplayDecorator<T> element,
                                         ElementType type,
                                         DisplayAsset<T> asset) {
}
