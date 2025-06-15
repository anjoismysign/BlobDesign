package io.github.anjoismysign.blobdesign.director.command;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.entities.BlockDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.ItemDisplayPreset;
import io.github.anjoismysign.bloblib.api.BlobLibMessageAPI;
import io.github.anjoismysign.bloblib.entities.BlobExecutor;
import io.github.anjoismysign.bloblib.entities.BlobObject;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
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
            String arg = args[0].toLowerCase(Locale.ROOT);
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
                        BlobLibMessageAPI.getInstance()
                                .getMessage("BlobDesign.Preset-Not-Found", player)
                                .handle(player);
                        return true;
                    }
                    asset.instantiate(player);
                    BlobLibMessageAPI.getInstance()
                            .getMessage("BlobDesign.Element-Instantiation-Successful", player)
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
                        BlobLibMessageAPI.getInstance()
                                .getMessage("BlobDesign.Preset-Not-Found", player)
                                .handle(player);
                        return true;
                    }
                    asset.instantiate(player);
                    BlobLibMessageAPI.getInstance()
                            .getMessage("BlobDesign.Element-Instantiation-Successful", player)
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
            if (!hasAdminPermission(sender))
                return null;
            switch (args.length) {
                case 1 -> {
                    return List.of("block", "item");
                }
                case 2 -> {
                    String arg = args[0].toLowerCase(Locale.ROOT);
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
