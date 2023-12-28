package us.mytheria.blobdesign.util;

import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import us.mytheria.blobdesign.BlobDesignAPI;
import us.mytheria.blobdesign.entities.inventory.InventoryType;
import us.mytheria.bloblib.BlobLibAPI;
import us.mytheria.bloblib.api.BlobLibMessageAPI;
import us.mytheria.bloblib.api.BlobLibSoundAPI;
import us.mytheria.bloblib.entities.display.DisplayDecorator;
import us.mytheria.bloblib.entities.inventory.BlobInventory;
import us.mytheria.bloblib.entities.inventory.InventoryButton;
import us.mytheria.bloblib.utilities.ItemStackUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Consumer;

public class EditorUtil {
    public static boolean ifContainsSlot(Player player, int slot, InventoryType inventoryType, String buttonKey,
                                         Consumer<InventoryButton> consumer) {
        InventoryButton button = BlobDesignAPI.getInventory(inventoryType)
                .getButton(buttonKey);
        if (button == null)
            throw new IllegalStateException("InventoryButton is null. Report to BlobDesign developer.");
        if (button.containsSlot(slot)) {
            BlobLibSoundAPI.getInstance()
                    .getSound("Builder.Button-Click")
                    .handle(player);
            consumer.accept(button);
            return true;
        }
        return false;
    }

    public static void listenDisplayEditor(Player player, int slot,
                                           DisplayDecorator<?> decorator,
                                           JavaPlugin plugin,
                                           InventoryType inventoryType) {
        Display call = decorator.call();
        if (ifContainsSlot(player, slot, inventoryType, "UniformScale", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    Transformation transformation = call.getTransformation();
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            new Vector3f(input),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.UniformScale-Timeout", "Builder.UniformScale");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "ScaleX", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    Transformation transformation = call.getTransformation();
                    Vector3f scale = transformation.getScale();
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            new Vector3f(input, scale.y, scale.z),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.ScaleX-Timeout", "Builder.ScaleX");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "ScaleY", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    Transformation transformation = call.getTransformation();
                    Vector3f scale = transformation.getScale();
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            new Vector3f(scale.x, input, scale.z),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.ScaleY-Timeout", "Builder.ScaleY");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "ScaleZ", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    Transformation transformation = call.getTransformation();
                    Vector3f scale = transformation.getScale();
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            new Vector3f(scale.x, scale.y, input),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.ScaleZ-Timeout", "Builder.ScaleZ");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "LeftX", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    input = (float) Math.toRadians(input);
                    Transformation transformation = call.getTransformation();
                    Quaternionf rotation = transformation.getLeftRotation();
                    Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            new Quaternionf().rotationXYZ(input, vector3f.y, vector3f.z),
                            transformation.getScale(),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.LeftX-Timeout", "Builder.LeftX");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "LeftY", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    input = (float) Math.toRadians(input);
                    Transformation transformation = call.getTransformation();
                    Quaternionf rotation = transformation.getLeftRotation();
                    Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            new Quaternionf().rotationXYZ(vector3f.x, input, vector3f.z),
                            transformation.getScale(),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.LeftY-Timeout", "Builder.LeftY");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "LeftZ", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    input = (float) Math.toRadians(input);
                    Transformation transformation = call.getTransformation();
                    Quaternionf rotation = transformation.getLeftRotation();
                    Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            new Quaternionf().rotationXYZ(vector3f.x, vector3f.y, input),
                            transformation.getScale(),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.LeftZ-Timeout", "Builder.LeftZ");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "RightX", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    input = (float) Math.toRadians(input);
                    Transformation transformation = call.getTransformation();
                    Quaternionf rotation = transformation.getRightRotation();
                    Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            transformation.getScale(),
                            new Quaternionf().rotationXYZ(input, vector3f.y, vector3f.z)));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.RightX-Timeout", "Builder.RightX");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "RightY", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    input = (float) Math.toRadians(input);
                    Transformation transformation = call.getTransformation();
                    Quaternionf rotation = transformation.getRightRotation();
                    Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            transformation.getScale(),
                            new Quaternionf().rotationXYZ(vector3f.x, input, vector3f.z)));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.RightY-Timeout", "Builder.RightY");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "RightZ", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    input = (float) Math.toRadians(input);
                    Transformation transformation = call.getTransformation();
                    Quaternionf rotation = transformation.getRightRotation();
                    Vector3f vector3f = rotation.getEulerAnglesXYZ(new Vector3f());
                    call.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            transformation.getScale(),
                            new Quaternionf().rotationXYZ(vector3f.x, vector3f.y, input)));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.RightZ-Timeout", "Builder.RightZ");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "UniformTranslation", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    call.setTransformation(new Transformation(
                            new Vector3f(input, input, input),
                            call.getTransformation().getLeftRotation(),
                            call.getTransformation().getScale(),
                            call.getTransformation().getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.UniformTranslation-Timeout", "Builder.UniformTranslation");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "TranslationX", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    Transformation transformation = call.getTransformation();
                    Vector3f Translation = transformation.getTranslation();
                    call.setTransformation(new Transformation(
                            new Vector3f(input, Translation.y, Translation.z),
                            transformation.getLeftRotation(),
                            transformation.getScale(),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.TranslationX-Timeout", "Builder.TranslationX");
        }))
            return;
        if (ifContainsSlot(player, slot, inventoryType, "TranslationY", button -> {
            player.closeInventory();
            BlobLibAPI.addChatListener(player, 300, string -> {
                try {
                    float input = Float.parseFloat(string);
                    Transformation transformation = call.getTransformation();
                    Vector3f Translation = transformation.getTranslation();
                    call.setTransformation(new Transformation(
                            new Vector3f(Translation.x, input, Translation.z),
                            transformation.getLeftRotation(),
                            transformation.getScale(),
                            transformation.getRightRotation()));
                } catch (NumberFormatException var6) {
                    BlobLibMessageAPI.getInstance()
                            .getMessage("Builder.Number-Exception", player)
                            .handle(player);
                }
            }, "Builder.TranslationY-Timeout", "Builder.TranslationY");
        }))
            return;
        ifContainsSlot(player, slot, inventoryType, "TranslationZ", button -> {
                    player.closeInventory();
                    BlobLibAPI.addChatListener(player, 300, string -> {
                        try {
                            float input = Float.parseFloat(string);
                            Transformation transformation = call.getTransformation();
                            Vector3f Translation = transformation.getTranslation();
                            call.setTransformation(new Transformation(
                                    new Vector3f(Translation.x, Translation.y, input),
                                    transformation.getLeftRotation(),
                                    transformation.getScale(),
                                    transformation.getRightRotation()));
                        } catch (NumberFormatException var6) {
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
        Vector3f translation = transformation.getTranslation();
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
        InventoryButton translationX = inventory.getButton("TranslationX");
        translationX.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%translationX%", translation.x + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton translationY = inventory.getButton("TranslationY");
        translationY.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%translationY%", translation.y + "");
            inventory.setButton(slot, itemStack);
        });
        InventoryButton translationZ = inventory.getButton("TranslationZ");
        translationZ.getSlots().forEach(slot -> {
            ItemStack itemStack = inventory.getButton(slot);
            ItemStackUtil.replace(itemStack, "%translationZ%", translation.z + "");
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
