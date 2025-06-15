package io.github.anjoismysign.blobdesign.director.command;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.director.manager.InventoryManager;
import io.github.anjoismysign.bloblib.entities.BlobExecutor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

import java.util.List;
import java.util.Locale;

public class DisplaySummoner extends BlobExecutor {
    public DisplaySummoner(DesignManagerDirector director) {
        super(director.getPlugin(), "displaysummoner");
        setCommand((sender, args) -> {
            if (!hasAdminPermission(sender))
                return true;
            if (args.length < 1) {
                sender.sendMessage("Usage: /disu <item_display/block_display>");
                return true;
            }
            String arg = args[0].toLowerCase(Locale.ROOT);
            switch (arg) {
                case "block_display" -> {
                    if (!isInstanceOfPlayer(sender))
                        return true;
                    Player player = (Player) sender;
                    Location center = player.getLocation().getBlock().getLocation()
                            .add(0.5, 0.5, 0.5);
                    BlockDisplay blockDisplay = (BlockDisplay) player.getWorld().spawnEntity(center,
                            EntityType.BLOCK_DISPLAY);
                    blockDisplay.setBlock(Material.GLASS.createBlockData());
                    InventoryManager inventoryManager = director.getInventoryManager();
                    inventoryManager.addBlockDisplay(player, blockDisplay);
                    Transformation transformation = blockDisplay.getTransformation();
                    blockDisplay.setTransformation(new Transformation(new Vector3f(
                            -0.5f, -0.5f, -0.5f),
                            transformation.getLeftRotation(),
                            transformation.getScale(),
                            transformation.getRightRotation()));
                    inventoryManager.openBlockEditor(player);
                    return true;
                }
                case "item_display" -> {
                    if (!isInstanceOfPlayer(sender))
                        return true;
                    Player player = (Player) sender;
                    Location center = player.getLocation().getBlock().getLocation()
                            .add(0.5, 0.5, 0.5);
                    ItemDisplay itemDisplay = (ItemDisplay) player.getWorld().spawnEntity(center,
                            EntityType.ITEM_DISPLAY);
                    itemDisplay.setItemStack(new ItemStack(Material.SKELETON_SKULL));
                    itemDisplay.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIXED);
                    InventoryManager inventoryManager = director.getInventoryManager();
                    inventoryManager.addItemDisplay(player, itemDisplay);
                    Transformation transformation = itemDisplay.getTransformation();
                    itemDisplay.setTransformation(new Transformation(new Vector3f(
                            transformation.getTranslation()),
                            transformation.getLeftRotation(),
                            new Vector3f(2.0f, 2.0f, 2.0f),
                            transformation.getRightRotation()));
                    inventoryManager.openItemEditor(player);
                    return true;
                }
                default -> {
                    sender.sendMessage("Usage: /disu <item_display/block_display>");
                    return true;
                }
            }
        });
        setTabCompleter((sender, args) -> {
            if (args.length != 1)
                return null;
            if (!hasAdminPermission(sender))
                return null;
            return List.of("block_display", "item_display");
        });
    }
}
