package io.github.anjoismysign.blobdesign.util;

import io.github.anjoismysign.blobdesign.BlobDesignAPI;
import io.github.anjoismysign.blobdesign.director.manager.InventoryManager;
import io.github.anjoismysign.blobdesign.entities.DisplayController;
import io.github.anjoismysign.blobdesign.entities.inventory.InventoryType;
import io.github.anjoismysign.bloblib.api.BlobLibListenerAPI;
import io.github.anjoismysign.bloblib.api.BlobLibMessageAPI;
import io.github.anjoismysign.bloblib.api.BlobLibSoundAPI;
import io.github.anjoismysign.bloblib.entities.display.DisplayDecorator;
import io.github.anjoismysign.bloblib.entities.display.DisplayInfo;
import io.github.anjoismysign.bloblib.entities.inventory.BlobInventory;
import io.github.anjoismysign.bloblib.entities.inventory.InventoryButton;
import io.github.anjoismysign.bloblib.utilities.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.function.Consumer;

public class EditorUtil {
    public static boolean containsSlot(Player player, int slot, InventoryType inventoryType, String buttonKey,
                                       Consumer<InventoryButton> consumer) {
        InventoryButton button = BlobDesignAPI.getInventory(inventoryType)
                .getButton(buttonKey);
        if (button == null)
            return false;
        if (button.containsSlot(slot)) {
            BlobLibSoundAPI.getInstance()
                    .getSound("Builder.Button-Click")
                    .handle(player);
            consumer.accept(button);
            return true;
        }
        return false;
    }

    public static void listenDisplayEditor(@NotNull Player player,
                                           int slot,
                                           @NotNull DisplayDecorator<?> decorator,
                                           @NotNull InventoryType inventoryType,
                                           @NotNull Runnable openEditor,
                                           @NotNull InventoryManager inventoryManager) {
        Objects.requireNonNull(player, "'player' cannot be null");
        Objects.requireNonNull(decorator, "'decorator' cannot be null");
        Objects.requireNonNull(inventoryType, "'inventoryType' cannot be null");
        Objects.requireNonNull(openEditor, "'openEditor' cannot be null");
        Objects.requireNonNull(inventoryManager, "'inventoryManager' cannot be null");
        Display call = decorator.call();
        if (containsSlot(player, slot, inventoryType, "UniformScale", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).uniformScale(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.UniformScale-Timeout", "Builder.UniformScale");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "ScaleX", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).scaleX(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.ScaleX-Timeout", "Builder.ScaleX");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "ScaleY", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).scaleY(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.ScaleY-Timeout", "Builder.ScaleY");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "ScaleZ", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).scaleZ(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.ScaleZ-Timeout", "Builder.ScaleZ");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "LeftX", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).leftX(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.LeftX-Timeout", "Builder.LeftX");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "LeftY", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).leftY(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.LeftY-Timeout", "Builder.LeftY");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "LeftZ", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).leftZ(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.LeftZ-Timeout", "Builder.LeftZ");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "RightX", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).rightX(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.RightX-Timeout", "Builder.RightX");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "RightY", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).rightY(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.RightY-Timeout", "Builder.RightY");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "RightZ", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).rightZ(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.RightZ-Timeout", "Builder.RightZ");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "Delete", button -> {
            player.closeInventory();
            decorator.call().remove();
            inventoryManager.removeMapping(player);
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "Clone", button -> {
            player.closeInventory();
            if (decorator.call() instanceof BlockDisplay current) {
                DisplayInfo currentInfo = DisplayInfo.of(current);
                Location location = current.getLocation().clone();
                BlockDisplay blockDisplay = (BlockDisplay) player.getWorld().spawnEntity(location,
                        EntityType.BLOCK_DISPLAY);
                currentInfo.apply(blockDisplay);
                blockDisplay.setBlock(current.getBlock());
                inventoryManager.addBlockDisplay(player, blockDisplay);
                inventoryManager.openBlockEditor(player);
                return;
            }
            if (decorator.call() instanceof ItemDisplay current) {
                DisplayInfo currentInfo = DisplayInfo.of(current);
                Location location = current.getLocation().clone();
                ItemDisplay itemDisplay = (ItemDisplay) player.getWorld().spawnEntity(location,
                        EntityType.ITEM_DISPLAY);
                currentInfo.apply(itemDisplay);
                itemDisplay.setItemStack(current.getItemStack());
                inventoryManager.addItemDisplay(player, itemDisplay);
                inventoryManager.openItemEditor(player);
                return;
            }
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "TranslationScaleFactor", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).translationScaleFactor(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.TranslationScaleFactor-Timeout", "Builder.TranslationScaleFactor");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "UniformTranslation", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).uniformTranslation(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.UniformTranslation-Timeout", "Builder.UniformTranslation");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "TranslationX", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).translationX(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.TranslationX-Timeout", "Builder.TranslationX");
        }))
            return;
        if (containsSlot(player, slot, inventoryType, "TranslationY", button -> {
            player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    DisplayController.of(call).translationY(input);
                    openEditor.run();
                } catch (NumberFormatException exception) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.TranslationY-Timeout", "Builder.TranslationY");
        }))
            return;
        containsSlot(player, slot, inventoryType, "TranslationZ", button -> {
                    player.closeInventory();
            BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                        try {
                            float input = Float.parseFloat(string);
                            DisplayController.of(call).translationZ(input);
                            openEditor.run();
                        } catch (NumberFormatException exception) {
                            BlobLibMessageAPI.getInstance()
                                    .getMessage("Builder.Number-Exception", player)
                                    .handle(player);
                        }
                    }, "Builder.TranslationZ-Timeout", "Builder.TranslationZ");
                }
        );
    }

    public static void updateEditorButtons(BlobInventory inventory,
                                           DisplayDecorator<?> decorator) {
        Display call = decorator.call();
        Transformation transformation = call.getTransformation();
        Vector3f scale = transformation.getScale();
        Quaternionf leftRotation = transformation.getLeftRotation();
        Vector3f left = leftRotation.getEulerAnglesXYZ(new Vector3f());
        Quaternionf rightRotation = transformation.getRightRotation();
        Vector3f right = rightRotation.getEulerAnglesXYZ(new Vector3f());
        InventoryButton scaleX = inventory.getButton("ScaleX");
        scaleX.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%scaleX%", scale.x + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton scaleY = inventory.getButton("ScaleY");
        scaleY.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%scaleY%", scale.y + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton scaleZ = inventory.getButton("ScaleZ");
        scaleZ.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%scaleZ%", scale.z + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton leftX = inventory.getButton("LeftX");
        leftX.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%leftRotationX%",
                    displayDegrees(Math.toDegrees(left.x)) + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton leftY = inventory.getButton("LeftY");
        leftY.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%leftRotationY%",
                    displayDegrees(Math.toDegrees(left.y)) + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton leftZ = inventory.getButton("LeftZ");
        leftZ.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%leftRotationZ%",
                    displayDegrees(Math.toDegrees(left.z)) + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton rightX = inventory.getButton("RightX");
        rightX.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%rightRotationX%",
                    displayDegrees(Math.toDegrees(right.x)) + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton rightY = inventory.getButton("RightY");
        rightY.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%rightRotationY%",
                    displayDegrees(Math.toDegrees(right.y)) + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton rightZ = inventory.getButton("RightZ");
        rightZ.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%rightRotationZ%",
                    displayDegrees(Math.toDegrees(right.z)) + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton delete = inventory.getButton("Delete");
        delete.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            inventory.setButton(slot, itemStack);
        });
        InventoryButton clone = inventory.getButton("Clone");
        clone.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            inventory.setButton(slot, itemStack);
        });
        InventoryButton translationScaleFactor = inventory.getButton("TranslationScaleFactor");
        translationScaleFactor.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%translationScaleFactor%",
                    DisplayController.of(call).translationScaleFactor().toString());
            inventory.setButton(slot, itemStack);
        });
        InventoryButton translationX = inventory.getButton("TranslationX");
        translationX.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%translationX%",
                    DisplayController.of(call).translationX().toString());
            inventory.setButton(slot, itemStack);
        });
        InventoryButton translationY = inventory.getButton("TranslationY");
        translationY.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%translationY%",
                    DisplayController.of(call).translationY().toString());
            inventory.setButton(slot, itemStack);
        });
        InventoryButton translationZ = inventory.getButton("TranslationZ");
        translationZ.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%translationZ%",
                    DisplayController.of(call).translationZ().toString());
            inventory.setButton(slot, itemStack);
        });
    }

    public static String displayDegrees(double degrees) {
        return degreesFormat.format(degrees);
    }

    private static final DecimalFormat degreesFormat = new DecimalFormat("#.#####");

    static {
        degreesFormat.setRoundingMode(RoundingMode.FLOOR);
        degreesFormat.setDecimalSeparatorAlwaysShown(false);
    }
}
