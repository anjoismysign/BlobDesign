package io.github.anjoismysign.blobdesign.director.command;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.entities.DesignDisplayOperator;
import io.github.anjoismysign.blobdesign.entities.ItemDisplayPresetAsset;
import io.github.anjoismysign.blobdesign.entities.proxy.DesignProxifier;
import io.github.anjoismysign.blobdesign.entities.proxy.ItemDisplayPresetAssetProxy;
import io.github.anjoismysign.bloblib.api.BlobLibMessageAPI;
import io.github.anjoismysign.bloblib.entities.BlobExecutor;
import io.github.anjoismysign.bloblib.entities.ObjectDirector;
import io.github.anjoismysign.bloblib.entities.ObjectManager;
import io.github.anjoismysign.bloblib.entities.display.DisplayData;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class HeadPresetBlock extends BlobExecutor {
    public HeadPresetBlock(DesignManagerDirector director) {
        super(director.getPlugin(), "headpresetblock");
        setCommand((sender, args) -> {
            if (!hasAdminPermission(sender))
                return true;
            if (!isInstanceOfPlayer(sender))
                return true;
            Player player = (Player) sender;
            if (args.length < 1) {
                sender.sendMessage("Usage: /headpresetblock <key>");
                return true;
            }
            String key = args[0];
            ObjectDirector<ItemDisplayPresetAssetProxy> assetDirector = director.getItemDisplayAssetDirector();
            ObjectManager<ItemDisplayPresetAssetProxy> objectManager = assetDirector.getObjectManager();
            ItemDisplayPresetAssetProxy existent = objectManager.getObject(key);
            if (existent != null) {
                sender.sendMessage("itemDisplay with key " + key + " already exists");
                return true;
            }
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand.getType() != Material.PLAYER_HEAD)
                return true;
            ItemDisplayPresetAssetProxy itemDisplay = DesignProxifier.PROXY(new ItemDisplayPresetAsset(key,
                    new DesignDisplayOperator() {
                        private final DisplayData displayData = new DisplayData(new Display.Brightness(15, 15),
                                Display.Billboard.FIXED, 0, 1, 1,
                                0, 0, Color.WHITE, false,
                                1, 1, 1);
                        private final Transformation transformation = new Transformation(
                                new Vector3f(0, 0, 0),
                                new Quaternionf(0, 0, 0, 1),
                                new Vector3f(1.885f, 1.885f, 1.885f),
                                new Quaternionf(0, 0, 0, 1));

                        @Override
                        public DisplayData getDisplayData() {
                            return displayData;
                        }

                        @Override
                        public Transformation getTransformation() {
                            return transformation;
                        }

                        @Override
                        public Plugin getPlugin() {
                            return director.getPlugin();
                        }
                    },
                    new ItemStack(hand),
                    ItemDisplay.ItemDisplayTransform.FIXED,
                    director));
            objectManager.addObject(key, itemDisplay);
            player.getInventory().setItemInMainHand(itemDisplay.createPlacer());
            BlobLibMessageAPI.getInstance()
                    .getMessage("BlobDesign.HeadPresetBlock-Instantiation-Successful",
                            player)
                    .handle(player);
            return true;
        });
        setTabCompleter((sender, args) -> {
            if (args.length != 1)
                return null;
            if (!hasAdminPermission(sender))
                return null;
            return List.of("Write key for", "the new itemDisplay");
        });
    }
}
