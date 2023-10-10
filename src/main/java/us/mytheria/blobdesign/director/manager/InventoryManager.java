package us.mytheria.blobdesign.director.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobdesign.director.DesignManager;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.inventory.InventoryType;
import us.mytheria.blobdesign.util.EditorUtil;
import us.mytheria.bloblib.BlobLibAssetAPI;
import us.mytheria.bloblib.entities.BlobEditor;
import us.mytheria.bloblib.entities.BlobSelector;
import us.mytheria.bloblib.entities.display.DisplayDecorator;
import us.mytheria.bloblib.entities.inventory.BlobInventory;
import us.mytheria.bloblib.entities.inventory.InventoryBuilderCarrier;
import us.mytheria.bloblib.entities.inventory.InventoryButton;
import us.mytheria.bloblib.itemstack.ItemStackBuilder;
import us.mytheria.bloblib.utilities.ItemStackUtil;

import java.util.*;

public class InventoryManager extends DesignManager {
    private Map<InventoryType, InventoryBuilderCarrier<InventoryButton>> carriers;
    private Map<InventoryType, String> carrierTitles;
    private final Map<String, BlobEditor<?>> editorMap;
    private final Map<String, DisplayDecorator<BlockDisplay>> blockDisplayMap;
    private final Map<String, DisplayDecorator<ItemDisplay>> itemDisplayMap;

    public InventoryManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
        editorMap = new HashMap<>();
        blockDisplayMap = new HashMap<>();
        itemDisplayMap = new HashMap<>();
        reload();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void reload() {
        carriers = new HashMap<>();
        carrierTitles = new HashMap<>();
        InventoryBuilderCarrier<InventoryButton> blockNavigator = BlobLibAssetAPI.getInventoryBuilderCarrier("BlockDisplayNavigator");
        carriers.put(InventoryType.BLOCK_DISPLAY_NAVIGATOR, blockNavigator);
        carrierTitles.put(InventoryType.BLOCK_DISPLAY_NAVIGATOR, blockNavigator.title());
        InventoryBuilderCarrier<InventoryButton> blockEditor = BlobLibAssetAPI.getInventoryBuilderCarrier("BlockDisplayEditor");
        carriers.put(InventoryType.BLOCK_DISPLAY_EDITOR, blockEditor);
        carrierTitles.put(InventoryType.BLOCK_DISPLAY_EDITOR, blockEditor.title());
        InventoryBuilderCarrier<InventoryButton> itemNavigator = BlobLibAssetAPI.getInventoryBuilderCarrier("ItemDisplayNavigator");
        carriers.put(InventoryType.ITEM_DISPLAY_NAVIGATOR, itemNavigator);
        carrierTitles.put(InventoryType.ITEM_DISPLAY_NAVIGATOR, itemNavigator.title());
        InventoryBuilderCarrier<InventoryButton> itemEditor = BlobLibAssetAPI.getInventoryBuilderCarrier("ItemDisplayEditor");
        carriers.put(InventoryType.ITEM_DISPLAY_EDITOR, itemEditor);
        carrierTitles.put(InventoryType.ITEM_DISPLAY_EDITOR, itemEditor.title());
    }

    /**
     * Will remove the mapping for the given player.
     *
     * @param player the player to remove the mapping for
     */
    public void removeMapping(Player player) {
        editorMap.remove(player.getName());
        blockDisplayMap.remove(player.getName());
        itemDisplayMap.remove(player.getName());
    }

    /**
     * Gets the title for the given inventory type.
     * If no title is found, will throw an exception.
     *
     * @param type the type to get the title for
     * @return the title for the given inventory type
     */
    @NotNull
    public String getTitle(InventoryType type) {
        return Optional.ofNullable(carrierTitles.get(type))
                .orElseThrow(() -> new IllegalArgumentException
                        ("No title for inventory type " + type.name()));
    }

    /**
     * Gets a copy the inventory for the given type.
     *
     * @param type the type to get the inventory for
     * @return the inventory for the given type
     */
    @NotNull
    public BlobInventory getInventory(InventoryType type) {
        InventoryBuilderCarrier<InventoryButton> carrier = carriers.get(type);
        if (carrier == null)
            throw new IllegalArgumentException("No inventory for inventory type " + type.name());
        return BlobInventory.fromInventoryBuilderCarrier(carrier);
    }

    /**
     * Returns the manage members editor for the given player.
     *
     * @param player the player to get the editor for
     * @return the manage members editor for the given player
     */
    @SuppressWarnings("unchecked")
    public BlobEditor<UUID> getUUIDEditor(Player player) {
        return (BlobEditor<UUID>) editorMap.get(player.getName());
    }

    /**
     * Will retrieve the entity that's currently being viewed by the given player.
     *
     * @param player the player to get the entity for
     * @return the entity that's currently being viewed by the given player
     */
    public DisplayDecorator<BlockDisplay> getBlockDisplay(Player player) {
        return blockDisplayMap.get(player.getName());
    }

    /**
     * Will retrieve the entity that's currently being viewed by the given player.
     *
     * @param player the player to get the entity for
     * @return the entity that's currently being viewed by the given player
     */
    public DisplayDecorator<ItemDisplay> getItemDisplay(Player player) {
        return itemDisplayMap.get(player.getName());
    }

    /**
     * Will open the given BlockDisplay editor for the given player.
     *
     * @param player the player to open the inventory for
     */
    public void openBlockEditor(Player player) {
        BlobInventory inventory = getInventory(InventoryType.BLOCK_DISPLAY_EDITOR);
        EditorUtil.updateEditorButtons(inventory, getBlockDisplay(player));
        inventory.open(player);
    }


    /**
     * Will open the given ItemDisplay editor for the given player.
     *
     * @param player the player to open the inventory for
     */
    public void openItemEditor(Player player) {
        BlobInventory inventory = getInventory(InventoryType.ITEM_DISPLAY_EDITOR);
        EditorUtil.updateEditorButtons(inventory, getItemDisplay(player));
        inventory.open(player);
    }

    /**
     * Will get all BlockDisplays that are in the given world inside
     * player's radius.
     *
     * @param player the player to get the BlockDisplays for
     */
    public void openBlockNavigator(Player player, double radius) {
        BlobInventory inventory = getInventory(InventoryType.BLOCK_DISPLAY_NAVIGATOR);
        List<BlockDisplay> nearby = player.getWorld().getNearbyEntities(player.getLocation(),
                        radius, radius, radius).stream()
                .filter(entity -> entity.getType() == EntityType.BLOCK_DISPLAY)
                .map(BlockDisplay.class::cast)
                .toList();
        Bukkit.getLogger().info("Found " + nearby.size() + " nearby BlockDisplays");
        BlobSelector<BlockDisplay> selector = BlobSelector.build(inventory, player.getUniqueId(),
                "BlockDisplay", nearby, null);
        selector.setItemsPerPage(selector.getSlots("BlockDisplay")
                == null ? 1 : selector.getSlots("BlockDisplay").size());
        selector.selectElement(player, blockDisplay -> {
            itemDisplayMap.remove(player.getName());
            blockDisplayMap.put(player.getName(), new DisplayDecorator<>(blockDisplay, getPlugin()));
            openBlockEditor(player);
        }, null, blockDisplay -> {
            ItemStack current = new ItemStack(blockDisplay.getBlock().getMaterial());
            String displayName = ItemStackUtil.display(current);
            ItemStackBuilder builder = ItemStackBuilder.build(current);
            builder.displayName(displayName);
            return builder.build();
        });
    }

    public void openItemNavigator(Player player, double radius) {
        BlobInventory inventory = getInventory(InventoryType.ITEM_DISPLAY_NAVIGATOR);
        List<ItemDisplay> nearby = player.getWorld().getNearbyEntities(player.getLocation(),
                        radius, radius, radius).stream()
                .filter(entity -> entity.getType() == EntityType.ITEM_DISPLAY)
                .map(ItemDisplay.class::cast)
                .toList();
        BlobSelector<ItemDisplay> selector = BlobSelector.build(inventory, player.getUniqueId(),
                "ItemDisplay", nearby, null);
        selector.setItemsPerPage(selector.getSlots("ItemDisplay")
                == null ? 1 : selector.getSlots("ItemDisplay").size());
        selector.selectElement(player, itemDisplay -> {
            blockDisplayMap.remove(player.getName());
            itemDisplayMap.put(player.getName(), new DisplayDecorator<>(itemDisplay, getPlugin()));
            openItemEditor(player);
        }, null, itemDisplay -> {
            ItemStack current = itemDisplay.getItemStack();
            if (current == null)
                return null;
            String displayName = ItemStackUtil.display(current);
            ItemStackBuilder builder = ItemStackBuilder.build(current);
            builder.displayName(displayName);
            return builder.build();
        });
    }
}
