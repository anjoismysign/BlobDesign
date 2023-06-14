package us.mytheria.blobdesign.director.command;

import org.bukkit.entity.Player;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.entities.BlockDisplayPresetAsset;
import us.mytheria.blobdesign.entities.ItemDisplayPresetAsset;
import us.mytheria.bloblib.BlobLibAssetAPI;
import us.mytheria.bloblib.entities.BlobExecutor;
import us.mytheria.bloblib.entities.BlobObject;

import java.util.List;
import java.util.stream.Collectors;

public class DisplaySpawner extends BlobExecutor {
    public DisplaySpawner(BlobDesign plugin) {
        super(plugin, "displayspawner");
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
                    BlockDisplayPresetAsset asset = plugin.getManagerDirector()
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
                    ItemDisplayPresetAsset asset = plugin.getManagerDirector()
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
                            return plugin.getManagerDirector()
                                    .getBlockDisplayAssetDirector().getObjectManager()
                                    .values().stream()
                                    .map(BlobObject::getKey)
                                    .collect(Collectors.toList());
                        }
                        case "item" -> {
                            return plugin.getManagerDirector()
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
