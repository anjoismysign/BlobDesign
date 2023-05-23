package us.mytheria.blobdesign.director.manager;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import us.mytheria.blobdesign.director.DesignManager;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DisplayManager extends DesignManager {
    private Map<UUID, DisplayDecorator<BlockDisplay>> blockDisplays;
    private Map<UUID, DisplayDecorator<ItemDisplay>> itemDisplays;

    public DisplayManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
        reload();
    }

    @Override
    public void reload() {
        blockDisplays = new HashMap<>();
        itemDisplays = new HashMap<>();
    }

    public void addBlockDisplay(UUID uuid, DisplayDecorator<BlockDisplay> display) {
        blockDisplays.put(uuid, display);
    }

    public void addItemDisplay(UUID uuid, DisplayDecorator<ItemDisplay> display) {
        itemDisplays.put(uuid, display);
    }

    public DisplayDecorator<BlockDisplay> getBlockDisplay(UUID uuid) {
        return blockDisplays.get(uuid);
    }

    public DisplayDecorator<BlockDisplay> getBlockDisplay(Player player) {
        return getBlockDisplay(player.getUniqueId());
    }

    public DisplayDecorator<ItemDisplay> getItemDisplay(UUID uuid) {
        return itemDisplays.get(uuid);
    }

    public DisplayDecorator<ItemDisplay> getItemDisplay(Player player) {
        return getItemDisplay(player.getUniqueId());
    }
}
