package us.mytheria.blobdesign.director.manager;

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
import us.mytheria.bloblib.api.BlobLibInventoryAPI;
import us.mytheria.bloblib.entities.BlobEditor;
import us.mytheria.bloblib.entities.display.DisplayDecorator;
import us.mytheria.bloblib.entities.inventory.BlobInventory;
import us.mytheria.bloblib.entities.inventory.InventoryBuilderCarrier;
import us.mytheria.bloblib.entities.inventory.InventoryButton;
import us.mytheria.bloblib.entities.inventory.InventoryDataRegistry;
import us.mytheria.bloblib.itemstack.ItemStackBuilder;
import us.mytheria.bloblib.utilities.ItemStackUtil;

import java.util.*;

public class InventoryManager extends DesignManager {
    private final Map<InventoryType, InventoryDataRegistry<InventoryButton>> registries;
    private final Map<InventoryType, InventoryBuilderCarrier<InventoryButton>> carriers;
    private final Map<InventoryType, String> carrierTitles;
    private final Map<String, BlobEditor<?>> editorMap;
    private final Map<String, DisplayDecorator<BlockDisplay>> blockDisplayMap;
    private final Map<String, DisplayDecorator<ItemDisplay>> itemDisplayMap;
    private final BlobLibInventoryAPI inventoryAPI;

    public InventoryManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
        inventoryAPI = BlobLibInventoryAPI.getInstance();
        registries = new HashMap<>();
        carriers = new HashMap<>();
        carrierTitles = new HashMap<>();
        editorMap = new HashMap<>();
        blockDisplayMap = new HashMap<>();
        itemDisplayMap = new HashMap<>();
        reload();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void reload() {
        BlobLibInventoryAPI inventoryAPI = BlobLibInventoryAPI.getInstance();
        InventoryBuilderCarrier<InventoryButton> blockNavigator = inventoryAPI.getInventoryBuilderCarrier("BlockDisplayNavigator");
        carriers.put(InventoryType.BLOCK_DISPLAY_NAVIGATOR, blockNavigator);
        carrierTitles.put(InventoryType.BLOCK_DISPLAY_NAVIGATOR, blockNavigator.title());
        InventoryBuilderCarrier<InventoryButton> blockEditor = inventoryAPI.getInventoryBuilderCarrier("BlockDisplayEditor");
        carriers.put(InventoryType.BLOCK_DISPLAY_EDITOR, blockEditor);
        carrierTitles.put(InventoryType.BLOCK_DISPLAY_EDITOR, blockEditor.title());
        InventoryBuilderCarrier<InventoryButton> itemNavigator = inventoryAPI.getInventoryBuilderCarrier("ItemDisplayNavigator");
        carriers.put(InventoryType.ITEM_DISPLAY_NAVIGATOR, itemNavigator);
        carrierTitles.put(InventoryType.ITEM_DISPLAY_NAVIGATOR, itemNavigator.title());
        InventoryBuilderCarrier<InventoryButton> itemEditor = inventoryAPI.getInventoryBuilderCarrier("ItemDisplayEditor");
        carriers.put(InventoryType.ITEM_DISPLAY_EDITOR, itemEditor);
        carrierTitles.put(InventoryType.ITEM_DISPLAY_EDITOR, itemEditor.title());
        registries.put(InventoryType.ITEM_DISPLAY_EDITOR, InventoryType.ITEM_DISPLAY_EDITOR.getRegistry());
        registries.put(InventoryType.BLOCK_DISPLAY_EDITOR, InventoryType.BLOCK_DISPLAY_EDITOR.getRegistry());
        registries.put(InventoryType.ITEM_DISPLAY_NAVIGATOR, InventoryType.ITEM_DISPLAY_NAVIGATOR.getRegistry());
        registries.put(InventoryType.BLOCK_DISPLAY_NAVIGATOR, InventoryType.BLOCK_DISPLAY_NAVIGATOR.getRegistry());
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
        return BlobInventory.ofInventoryBuilderCarrier(carrier);
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
     * Will cache a BlockDisplay
     *
     * @param player       The player to cache the editor
     * @param blockDisplay The block display to cache
     */
    public DisplayDecorator<BlockDisplay> addBlockDisplay(Player player, BlockDisplay blockDisplay) {
        itemDisplayMap.remove(player.getName());
        DisplayDecorator<BlockDisplay> decorator = new DisplayDecorator<>(blockDisplay, getPlugin());
        blockDisplayMap.put(player.getName(), decorator);
        return decorator;
    }

    /**
     * Will cache an ItemDisplay
     *
     * @param player      The player to cache the editor
     * @param itemDisplay The item display to cache
     */
    public DisplayDecorator<ItemDisplay> addItemDisplay(Player player, ItemDisplay itemDisplay) {
        blockDisplayMap.remove(player.getName());
        DisplayDecorator<ItemDisplay> decorator = new DisplayDecorator<>(itemDisplay, getPlugin());
        itemDisplayMap.put(player.getName(), decorator);
        return decorator;
    }

    /**
     * Will get all BlockDisplays that are in the given world inside
     * player's radius.
     *
     * @param player the player to get the BlockDisplays for
     */
    public void openBlockNavigator(Player player, double radius) {
        List<BlockDisplay> nearby = player.getWorld().getNearbyEntities(player.getLocation(),
                        radius, radius, radius).stream()
                .filter(entity -> entity.getType() == EntityType.BLOCK_DISPLAY)
                .map(BlockDisplay.class::cast)
                .toList();
        inventoryAPI.customSelector(
                InventoryType.BLOCK_DISPLAY_NAVIGATOR.getRegistryKey(),
                player,
                "BlockDisplay",
                "BlockDisplay",
                () -> nearby,
                blockDisplay -> {
                    addBlockDisplay(player, blockDisplay);
                    openBlockEditor(player);
                }, blockDisplay -> {
                    ItemStack current = new ItemStack(blockDisplay.getBlock().getMaterial());
                    String displayName = ItemStackUtil.display(current);
                    ItemStackBuilder builder = ItemStackBuilder.build(current);
                    builder.displayName(displayName);
                    return builder.build();
                },
                null,
                null,
                null);
    }

    public void openItemNavigator(Player player, double radius) {
        List<ItemDisplay> nearby = player.getWorld().getNearbyEntities(player.getLocation(),
                        radius, radius, radius).stream()
                .filter(entity -> entity.getType() == EntityType.ITEM_DISPLAY)
                .map(ItemDisplay.class::cast)
                .toList();
        inventoryAPI.customSelector(
                InventoryType.ITEM_DISPLAY_NAVIGATOR.getRegistryKey(),
                player,
                "ItemDisplay",
                "ItemDisplay",
                () -> nearby,
                itemDisplay -> {
                    addItemDisplay(player, itemDisplay);
                    openItemEditor(player);
                }, itemDisplay -> {
                    ItemStack current = itemDisplay.getItemStack();
                    if (current == null)
                        return null;
                    String displayName = ItemStackUtil.display(current);
                    ItemStackBuilder builder = ItemStackBuilder.build(current);
                    builder.displayName(displayName);
                    return builder.build();
                },
                null,
                null,
                null);
    }
}
