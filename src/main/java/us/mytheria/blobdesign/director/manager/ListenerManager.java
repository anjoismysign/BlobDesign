package us.mytheria.blobdesign.director.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import us.mytheria.blobdesign.director.DesignManager;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.inventory.InventoryType;
import us.mytheria.blobdesign.listeners.presetplacer.PresetPlacerPlace;
import us.mytheria.blobdesign.listeners.presetplacer.PresetPlacerRemove;
import us.mytheria.blobdesign.listeners.stepparser.FullQuadRotation;
import us.mytheria.blobdesign.listeners.stepparser.FullTriRotation;
import us.mytheria.blobdesign.util.EditorUtil;
import us.mytheria.bloblib.BlobLibAPI;
import us.mytheria.bloblib.api.BlobLibSoundAPI;
import us.mytheria.bloblib.entities.display.DisplayDecorator;
import us.mytheria.bloblib.entities.inventory.InventoryButton;

import java.util.function.Consumer;

public class ListenerManager extends DesignManager implements Listener {
    private final InventoryManager inventoryManager;

    public ListenerManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
        inventoryManager = managerDirector.getInventoryManager();
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
        reload();
        new FullQuadRotation(getManagerDirector());
        new FullTriRotation(getManagerDirector());
        new PresetPlacerPlace(getManagerDirector());
        new PresetPlacerRemove(getManagerDirector());
    }

    @Override
    public void reload() {
        //logics
    }

    @EventHandler
    public void onItemDisplayEditor(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (inventoryManager.getTitle(InventoryType.ITEM_DISPLAY_EDITOR)
                .compareTo(title) != 0)
            return;
        event.setCancelled(true);
        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();
        DisplayDecorator<ItemDisplay> decorator = inventoryManager.getItemDisplay(player);
        if (decorator == null)
            throw new IllegalStateException("DisplayDecorator is null. Report to BlobDesign developer.");
        if (ifContainsSlot(player, slot, InventoryType.ITEM_DISPLAY_EDITOR, "ItemDisplayTransform", button -> {
            player.closeInventory();
            ItemDisplay display = decorator.call();
            ItemDisplay.ItemDisplayTransform transform = display.getItemDisplayTransform();
            display.setItemDisplayTransform(getNextEnum(transform));
        }))
            return;
        if (ifContainsSlot(player, slot, InventoryType.ITEM_DISPLAY_EDITOR, "Icon", button -> {
            player.closeInventory();
            BlobLibAPI.addDropListener(player, item -> {
                decorator.call().setItemStack(item);
            }, "Builder.Icon");
        }))
            return;
        EditorUtil.listenDisplayEditor(player, slot, decorator, getPlugin(),
                InventoryType.ITEM_DISPLAY_EDITOR);
    }

    @EventHandler
    public void onBlockDisplayEditor(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (inventoryManager.getTitle(InventoryType.BLOCK_DISPLAY_EDITOR)
                .compareTo(title) != 0)
            return;
        event.setCancelled(true);
        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();
        DisplayDecorator<BlockDisplay> decorator = inventoryManager.getBlockDisplay(player);
        if (decorator == null)
            throw new IllegalStateException("DisplayDecorator is null. Report to BlobDesign developer.");
        if (ifContainsSlot(player, slot, InventoryType.BLOCK_DISPLAY_EDITOR, "Icon", button -> {
            player.closeInventory();
            BlobLibAPI.addPositionListener(player, 300, block -> {
                decorator.call().setBlock(block.getBlockData());
            }, "Builder.Icon-Block-Timeout", "Builder.Icon-Block");
        }))
            return;
        EditorUtil.listenDisplayEditor(player, slot, decorator, getPlugin(),
                InventoryType.BLOCK_DISPLAY_EDITOR);
    }

    private boolean ifContainsSlot(Player player, int slot, InventoryType inventoryType, String buttonKey,
                                   Consumer<InventoryButton> consumer) {
        InventoryButton button = inventoryManager.getInventory(inventoryType)
                .getButton(buttonKey);
        if (button == null)
            throw new IllegalStateException("InventoryButton is null. Report to BlobDesign developer.");
        if (button.containsSlot(slot)) {
            BlobLibSoundAPI.getInstance().getSound("Builder.Button-Click").handle(player);
            consumer.accept(button);
            return true;
        }
        return false;
    }

    private static <E extends Enum<E>> E getNextEnum(E currentEnum) {
        E[] enums = currentEnum.getDeclaringClass().getEnumConstants();
        int currentOrdinal = currentEnum.ordinal();
        int nextOrdinal = (currentOrdinal + 1) % enums.length;
        return enums[nextOrdinal];
    }
}