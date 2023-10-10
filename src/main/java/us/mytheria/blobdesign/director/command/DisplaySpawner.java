package us.mytheria.blobdesign.director.command;

import org.bukkit.entity.Player;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.BlockDisplayPreset;
import us.mytheria.blobdesign.entities.ItemDisplayPreset;
import us.mytheria.bloblib.BlobLibAssetAPI;
import us.mytheria.bloblib.entities.BlobExecutor;
import us.mytheria.bloblib.entities.BlobObject;

import java.util.List;
import java.util.stream.Collectors;

public class DisplaySpawner extends BlobExecutor {
    public DisplaySpawner(DesignManagerDirector director) {
        super(director.getPlugin(), "displayspawner");
        setCommand((sender, args) -> {
            if (!hasAdminPermission(sender))
                return true;
            if (args.length < 2) {
                sender.sendMessage("Usage: /displayspawner <block/item> <key>");
                return true;
            }
            String arg = args[0].toLowerCase();
            String key = args[1];
            switch (arg) {
                case "block" -> {
                    if (!isInstanceOfPlayer(sender))
                        return true;
                    Player player = (Player) sender;
                    BlockDisplayPreset asset = director
                            .getBlockDisplayAssetDirector().getObjectManager()
                            .getObject(key);
                    if (asset == null) {
                        BlobLibAssetAPI.getMessage("BlobDesign.Preset-Not-Found")
                                .handle(player);
                        return true;
                    }
                    asset.instantiate(player);
                    BlobLibAssetAPI.getMessage("BlobDesign.Element-Instantiation-Successful")
                            .handle(player);
                    return true;
                }
                case "item" -> {
                    if (!isInstanceOfPlayer(sender))
                        return true;
                    Player player = (Player) sender;
                    ItemDisplayPreset asset = director
                            .getItemDisplayAssetDirector().getObjectManager()
                            .getObject(key);
                    if (asset == null) {
                        BlobLibAssetAPI.getMessage("BlobDesign.Preset-Not-Found")
                                .handle(player);
                        return true;
                    }
                    asset.instantiate(player);
                    BlobLibAssetAPI.getMessage("BlobDesign.Element-Instantiation-Successful")
                            .handle(player);
                    return true;
                }
                default -> {
                    sender.sendMessage("Usage: /displayspawner <block/item>");
                    return true;
                }
            }
        });
        setTabCompleter((sender, args) -> {
            switch (args.length) {
                case 1 -> {
                    return List.of("block", "item");
                }
                case 2 -> {
                    String arg = args[0].toLowerCase();
                    switch (arg) {
                        case "block" -> {
                            return director
                                    .getBlockDisplayAssetDirector().getObjectManager()
                                    .values().stream()
                                    .map(BlobObject::getKey)
                                    .collect(Collectors.toList());
                        }
                        case "item" -> {
                            return director
                                    .getItemDisplayAssetDirector().getObjectManager()
                                    .values().stream()
                                    .map(BlobObject::getKey)
                                    .collect(Collectors.toList());
                        }
                        default -> {
                            return null;
                        }
                    }
                }
                default -> {
                    return null;
                }
            }
        });
    }
}
