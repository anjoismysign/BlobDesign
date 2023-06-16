package us.mytheria.blobdesign.director.command;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.manager.InventoryManager;
import us.mytheria.bloblib.BlobLibAssetAPI;
import us.mytheria.bloblib.entities.BlobExecutor;
import us.mytheria.bloblib.entities.display.DisplayDecorator;

import java.util.List;

public class DisplayEditor extends BlobExecutor {
    public DisplayEditor(BlobDesign plugin, DesignManagerDirector director) {
        super(plugin, "displayeditor");
        setCommand((sender, args) -> {
            if (!hasAdminPermission(sender))
                return true;
            if (args.length < 2) {
                if (args.length == 1) {
                    String arg = args[0];
                    if (arg.equalsIgnoreCase("previous")) {
                        if (!isInstanceOfPlayer(sender))
                            return true;
                        Player player = (Player) sender;
                        InventoryManager inventoryManager = director.getInventoryManager();
                        DisplayDecorator<BlockDisplay> blockDecorator = inventoryManager
                                .getBlockDisplay(player);
                        if (blockDecorator != null) {
                            inventoryManager.openBlockEditor(player);
                            return true;
                        }
                        DisplayDecorator<ItemDisplay> itemDecorator = inventoryManager
                                .getItemDisplay(player);
                        if (itemDecorator != null) {
                            inventoryManager.openItemEditor(player);
                            return true;
                        }
                        BlobLibAssetAPI.getMessage("BlobDesign.No-Previous-Work")
                                .handle(player);
                        return true;
                    }
                }
                sender.sendMessage("Usage: /displayeditor <block/item/previous> <radius>");
                return true;
            }
            String arg = args[0].toLowerCase();
            String input = args[1];
            switch (arg) {
                case "block" -> {
                    if (!isInstanceOfPlayer(sender))
                        return true;
                    float radius;
                    try {
                        radius = Float.parseFloat(input);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Usage: /displayeditor block <radius>");
                        return true;
                    }
                    Player player = (Player) sender;
                    director.getInventoryManager()
                            .openBlockNavigator(player, radius);
                    return true;
                }
                case "item" -> {
                    if (!isInstanceOfPlayer(sender))
                        return true;
                    float radius;
                    try {
                        radius = Float.parseFloat(input);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Usage: /displayeditor item <radius>");
                        return true;
                    }
                    Player player = (Player) sender;
                    director.getInventoryManager()
                            .openItemNavigator(player, radius);
                    return true;
                }
                default -> {
                    sender.sendMessage("Usage: /displayeditor <block/item> <radius>");
                    return true;
                }
            }
        });
        setTabCompleter((sender, args) -> {
            if (args.length == 1)
                return List.of("previous", "block", "item");
            return List.of();
        });
    }
}
