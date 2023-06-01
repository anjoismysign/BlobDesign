package us.mytheria.blobdesign.entities.inventory;

import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import us.mytheria.blobdesign.entities.ItemDisplayAsset;
import us.mytheria.bloblib.BlobLibAPI;
import us.mytheria.bloblib.BlobLibAssetAPI;
import us.mytheria.bloblib.entities.ObjectDirector;
import us.mytheria.bloblib.entities.inventory.BlobInventory;
import us.mytheria.bloblib.entities.inventory.ObjectBuilder;
import us.mytheria.bloblib.entities.inventory.ObjectBuilderButton;
import us.mytheria.bloblib.entities.inventory.ObjectBuilderButtonBuilder;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@SuppressWarnings("ConcatenationWithEmptyString")
public class ItemDisplayBuilder extends ObjectBuilder<ItemDisplayAsset> {
    public static ItemDisplayBuilder build(UUID builderId,
                                           ObjectDirector<ItemDisplayAsset> objectDirector) {
        return new ItemDisplayBuilder(
                BlobLibAssetAPI.getBlobInventory("ItemDisplayBuilder"),
                builderId, objectDirector);
    }

    private ItemDisplayBuilder(BlobInventory blobInventory, UUID builderId,
                               ObjectDirector<ItemDisplayAsset> objectDirector) {
        super(blobInventory, builderId, objectDirector);
        ObjectBuilderButton<String> keyButton = ObjectBuilderButtonBuilder.QUICK_STRING(
                "Key", 300, this);
        ObjectBuilderButton<ItemStack> iconButton = ObjectBuilderButtonBuilder.QUICK_ITEM("Icon",
                this);
        ObjectBuilderButton<Float> scaleX = ObjectBuilderButtonBuilder.QUICK_FLOAT("ScaleX",
                300, this);
        ObjectBuilderButton<Float> scaleY = ObjectBuilderButtonBuilder.QUICK_FLOAT("ScaleY",
                300, this);
        ObjectBuilderButton<Float> scaleZ = ObjectBuilderButtonBuilder.QUICK_FLOAT("ScaleZ",
                300, this);
        ObjectBuilderButton<Float> translationX = ObjectBuilderButtonBuilder.QUICK_FLOAT("TranslationX",
                300, this);
        ObjectBuilderButton<Float> translationY = ObjectBuilderButtonBuilder.QUICK_FLOAT("TranslationY",
                300, this);
        ObjectBuilderButton<Float> translationZ = ObjectBuilderButtonBuilder.QUICK_FLOAT("TranslationZ",
                300, this);
        ObjectBuilderButton<Float> leftRotationX = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftX",
                300, this);
        ObjectBuilderButton<Float> leftRotationY = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftY",
                300, this);
        ObjectBuilderButton<Float> leftRotationZ = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftZ",
                300, this);
        ObjectBuilderButton<Float> leftRotationW = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftW",
                300, this);
        ObjectBuilderButton<Float> rightRotationX = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightX",
                300, this);
        ObjectBuilderButton<Float> rightRotationY = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightY",
                300, this);
        ObjectBuilderButton<Float> rightRotationZ = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightZ",
                300, this);
        ObjectBuilderButton<Float> rightRotationW = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightW",
                300, this);
        ObjectBuilderButton<ItemDisplay.ItemDisplayTransform> itemDislayTransform = ObjectBuilderButtonBuilder.ENUM_NAVIGATOR("ItemDisplayTransform",
                ItemDisplay.ItemDisplayTransform.class, this);
        itemDislayTransform.set(ItemDisplay.ItemDisplayTransform.NONE);
        Function<Float, Boolean> uniformScaleFunction = value -> {
            ObjectBuilderButton<Float> uniformX = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleX");
            uniformX.set(value);
            ObjectBuilderButton<Float> uniformY = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleY");
            uniformY.set(value);
            ObjectBuilderButton<Float> uniformZ = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleZ");
            uniformZ.set(value);
            return true;
        };
        Function<Float, Boolean> uniformTranslationFunction = value -> {
            ObjectBuilderButton<Float> uniformX = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationX");
            uniformX.set(value);
            ObjectBuilderButton<Float> uniformY = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationY");
            uniformY.set(value);
            ObjectBuilderButton<Float> uniformZ = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationZ");
            uniformZ.set(value);
            return true;
        };
        addObjectBuilderButton(keyButton)
                .addObjectBuilderButton(iconButton)
                .addObjectBuilderButton(scaleX)
                .addObjectBuilderButton(scaleY)
                .addObjectBuilderButton(scaleZ)
                .addObjectBuilderButton(translationX)
                .addObjectBuilderButton(translationY)
                .addObjectBuilderButton(translationZ)
                .addObjectBuilderButton(leftRotationX)
                .addObjectBuilderButton(leftRotationY)
                .addObjectBuilderButton(leftRotationZ)
                .addObjectBuilderButton(leftRotationW)
                .addObjectBuilderButton(rightRotationX)
                .addObjectBuilderButton(rightRotationY)
                .addObjectBuilderButton(rightRotationZ)
                .addObjectBuilderButton(rightRotationW)
                .addObjectBuilderButton(itemDislayTransform)
                .addObjectBuilderButton(new ObjectBuilderButton<>("UniformScale", Optional.empty(),
                        (button, player) -> BlobLibAPI.addChatListener(player, 300, string -> {
                            try {
                                if (string.equalsIgnoreCase("null")) {
                                    button.set(null);
                                    return;
                                }
                                float input = Float.parseFloat(string);
                                if (uniformScaleFunction.apply(input))
                                    button.set(input);
                            } catch (NumberFormatException ignored) {
                                BlobLibAssetAPI.getMessage("Builder.Number-Exception").handle(player);
                            }
                        }, "Builder.UniformScale-Timeout", "Builder.UniformScale"),
                        uniformScaleFunction) {
                }).addObjectBuilderButton(new ObjectBuilderButton<>("UniformTranslation", Optional.empty(),
                        (button, player) -> BlobLibAPI.addChatListener(player, 300, string -> {
                            try {
                                if (string.equalsIgnoreCase("null")) {
                                    button.set(null);
                                    return;
                                }
                                float input = Float.parseFloat(string);
                                if (uniformTranslationFunction.apply(input))
                                    button.set(input);
                            } catch (NumberFormatException ignored) {
                                BlobLibAssetAPI.getMessage("Builder.Number-Exception").handle(player);
                            }
                        }, "Builder.UniformTranslation-Timeout", "Builder.UniformTranslation"),
                        uniformTranslationFunction) {
                })
                .setFunction(builder -> {
                    ItemDisplayAsset build = builder.construct();
                    if (build == null)
                        return null;
                    Player player = getPlayer();
                    BlobLibAssetAPI.getSound("Builder.Build-Complete")
                            .handle(player);
                    player.closeInventory();
                    build.saveToFile(objectDirector.getObjectManager().getLoadFilesDirectory());
                    objectDirector.getObjectManager().addObject(build.getKey(), build);
                    objectDirector.getBuilderManager().removeBuilder(player);
                    return build;
                });
    }

    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    @Nullable
    @Override
    public ItemDisplayAsset construct() {
        ObjectBuilderButton<String> keyButton = (ObjectBuilderButton<String>) getObjectBuilderButton("Key");
        ObjectBuilderButton<ItemStack> itemStackButton = (ObjectBuilderButton<ItemStack>) getObjectBuilderButton("Icon");
        ObjectBuilderButton<Float> scaleXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleX");
        ObjectBuilderButton<Float> scaleYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleY");
        ObjectBuilderButton<Float> scaleZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleZ");
        ObjectBuilderButton<Float> translationXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationX");
        ObjectBuilderButton<Float> translationYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationY");
        ObjectBuilderButton<Float> translationZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationZ");
        ObjectBuilderButton<Float> leftRotationXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftX");
        ObjectBuilderButton<Float> leftRotationYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftY");
        ObjectBuilderButton<Float> leftRotationZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftZ");
        ObjectBuilderButton<Float> leftRotationWButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftW");
        ObjectBuilderButton<Float> rightRotationXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightX");
        ObjectBuilderButton<Float> rightRotationYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightY");
        ObjectBuilderButton<Float> rightRotationZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightZ");
        ObjectBuilderButton<Float> rightRotationWButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightW");
        ObjectBuilderButton<ItemDisplay.ItemDisplayTransform> itemDislayTransformButton = (ObjectBuilderButton<ItemDisplay.ItemDisplayTransform>) getObjectBuilderButton("ItemDisplayTransform");

        if (!keyButton.isValuePresentAndNotNull() ||
                !itemStackButton.isValuePresentAndNotNull() ||
                !scaleXButton.isValuePresentAndNotNull() ||
                !scaleYButton.isValuePresentAndNotNull() || !scaleZButton.isValuePresentAndNotNull() ||
                !translationXButton.isValuePresentAndNotNull()
                || !translationYButton.isValuePresentAndNotNull() ||
                !translationZButton.isValuePresentAndNotNull() ||
                !leftRotationXButton.isValuePresentAndNotNull() ||
                !leftRotationYButton.isValuePresentAndNotNull() ||
                !leftRotationZButton.isValuePresentAndNotNull() ||
                !leftRotationWButton.isValuePresentAndNotNull() ||
                !rightRotationXButton.isValuePresentAndNotNull() ||
                !rightRotationYButton.isValuePresentAndNotNull() ||
                !rightRotationZButton.isValuePresentAndNotNull() ||
                !rightRotationWButton.isValuePresentAndNotNull() ||
                !itemDislayTransformButton.isValuePresentAndNotNull())
            return null;
        String key = keyButton.orNull();
        ItemStack icon = itemStackButton.orNull();
        if (icon.getType().isAir()) {
            Player player = getPlayer();
            player.closeInventory();
            BlobLibAssetAPI.getMessage("BlobDesign.Item-Cannot-Be-Air")
                    .handle(player);
            return null;
        }
        float scaleX = scaleXButton.orNull();
        float scaleY = scaleYButton.orNull();
        float scaleZ = scaleZButton.orNull();
        Vector3f scale = new Vector3f(scaleX, scaleY, scaleZ);
        float translationX = translationXButton.orNull();
        float translationY = translationYButton.orNull();
        float translationZ = translationZButton.orNull();
        Vector3f translation = new Vector3f(translationX, translationY, translationZ);
        float leftRotationX = leftRotationXButton.orNull();
        float leftRotationY = leftRotationYButton.orNull();
        float leftRotationZ = leftRotationZButton.orNull();
        float leftRotationW = leftRotationWButton.orNull();
        Quaternionf leftRotation = new Quaternionf(leftRotationX, leftRotationY, leftRotationZ, leftRotationW);
        float rightRotationX = rightRotationXButton.orNull();
        float rightRotationY = rightRotationYButton.orNull();
        float rightRotationZ = rightRotationZButton.orNull();
        float rightRotationW = rightRotationWButton.orNull();
        Quaternionf rightRotation = new Quaternionf(rightRotationX, rightRotationY, rightRotationZ, rightRotationW);
        ItemDisplay.ItemDisplayTransform itemDisplayTransform = itemDislayTransformButton.orNull();
        return new ItemDisplayAsset(key, icon, scale, translation,
                leftRotation, rightRotation, itemDisplayTransform);
    }
}