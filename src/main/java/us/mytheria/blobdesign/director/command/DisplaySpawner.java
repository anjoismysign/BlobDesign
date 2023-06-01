package us.mytheria.blobdesign.director.command;

import org.bukkit.entity.Player;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.entities.BlockDisplayAsset;
import us.mytheria.blobdesign.entities.ItemDisplayAsset;
import us.mytheria.bloblib.BlobLibAssetAPI;
import us.mytheria.bloblib.entities.BlobExecutor;
import us.mytheria.bloblib.entities.BlobObject;

import java.util.List;
import java.util.stream.Collectors;

public class DisplaySpawner extends BlobExecutor {
    public DisplaySpawner(BlobDesign plugin) {
        super(plugin, "displayspawner");
        setCommand((sender, args) -> {
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
                    BlockDisplayAsset asset = plugin.getManagerDirector()
                            .getBlockDisplayModelDirector().getObjectManager()
                            .getObject(key);
                    if (asset == null) {
                        BlobLibAssetAPI.getMessage("BlobDesign.Asset-Not-Found")
                                .handle(player);
                        return true;
                    }
                    asset.instantiate(player);
                    BlobLibAssetAPI.getMessage("BlobDesign.Asset-Instiation-Succesful")
                            .handle(player);
                    return true;
                }
                case "item" -> {
                    if (!isInstanceOfPlayer(sender))
                        return true;
                    Player player = (Player) sender;
                    ItemDisplayAsset asset = plugin.getManagerDirector()
                            .getItemDisplayModelDirector().getObjectManager()
                            .getObject(key);
                    if (asset == null) {
                        BlobLibAssetAPI.getMessage("BlobDesign.Asset-Not-Found")
                                .handle(player);
                        return true;
                    }
                    asset.instantiate(player);
                    BlobLibAssetAPI.getMessage("BlobDesign.Asset-Instiation-Succesful")
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
                case 0 -> {
                    return List.of();
                }
                case 1 -> {
                    return List.of("block", "item");
                }
                case 2 -> {
                    String arg = args[0].toLowerCase();
                    switch (arg) {
                        case "block" -> {
                            return plugin.getManagerDirector()
                                    .getBlockDisplayModelDirector().getObjectManager()
                                    .values().stream()
                                    .map(BlobObject::getKey)
                                    .collect(Collectors.toList());
                        }
                        case "item" -> {
                            return plugin.getManagerDirector()
                                    .getItemDisplayModelDirector().getObjectManager()
                                    .values().stream()
                                    .map(BlobObject::getKey)
                                    .collect(Collectors.toList());
                        }
                        default -> {
                            throw new IllegalStateException("Unexpected value: " + arg);
                        }
                    }
                }
                default -> {
                    throw new IllegalStateException("Unexpected value: " + args.length);
                }
            }
        });
    }
}
