package us.mytheria.blobdesign.entities.inventory;

import me.anjoismysign.anjo.entities.NamingConventions;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import us.mytheria.blobdesign.entities.BlockDisplayAsset;
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
public class BlockDisplayBuilder extends ObjectBuilder<BlockDisplayAsset> {

    public static BlockDisplayBuilder build(UUID builderId,
                                            ObjectDirector<BlockDisplayAsset> objectDirector) {
        return new BlockDisplayBuilder(
                BlobLibAssetAPI.getBlobInventory("BlockDisplayBuilder"),
                builderId, objectDirector);
    }

    private BlockDisplayBuilder(BlobInventory blobInventory, UUID builderId,
                                ObjectDirector<BlockDisplayAsset> objectDirector) {
        super(blobInventory, builderId, objectDirector);
        ObjectBuilderButton<String> keyButton = ObjectBuilderButtonBuilder.QUICK_STRING(
                "Key", 300, this);
        ObjectBuilderButton<Block> iconButton = ObjectBuilderButtonBuilder.QUICK_BLOCK("Icon",
                300, this);
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
        ObjectBuilderButton<Float> leftRotationX = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftRotationX",
                300, this);
        ObjectBuilderButton<Float> leftRotationY = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftRotationY",
                300, this);
        ObjectBuilderButton<Float> leftRotationZ = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftRotationZ",
                300, this);
        ObjectBuilderButton<Float> leftRotationW = ObjectBuilderButtonBuilder.QUICK_FLOAT("LeftRotationW",
                300, this);
        ObjectBuilderButton<Float> rightRotationX = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightRotationX",
                300, this);
        ObjectBuilderButton<Float> rightRotationY = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightRotationY",
                300, this);
        ObjectBuilderButton<Float> rightRotationZ = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightRotationZ",
                300, this);
        ObjectBuilderButton<Float> rightRotationW = ObjectBuilderButtonBuilder.QUICK_FLOAT("RightRotationW",
                300, this);
        Function<Float, Boolean> uniformScaleFunction = value -> {
            String placeholderRegexX = NamingConventions.toCamelCase("ScaleX");
            String placeholderRegexY = NamingConventions.toCamelCase("ScaleY");
            String placeholderRegexZ = NamingConventions.toCamelCase("ScaleZ");
            this.updateDefaultButton("ScaleX", "%" + placeholderRegexX + "%",
                    value == null ? "N/A" : "" + value);
            this.updateDefaultButton("ScaleY", "%" + placeholderRegexY + "%",
                    value == null ? "N/A" : "" + value);
            this.updateDefaultButton("ScaleZ", "%" + placeholderRegexZ + "%",
                    value == null ? "N/A" : "" + value);
            this.openInventory();
            return true;
        };
        Function<Float, Boolean> uniformTranslationFunction = value -> {
            String placeholderRegexX = NamingConventions.toCamelCase("TranslationX");
            String placeholderRegexY = NamingConventions.toCamelCase("TranslationY");
            String placeholderRegexZ = NamingConventions.toCamelCase("TranslationZ");
            this.updateDefaultButton("TranslationX", "%" + placeholderRegexX + "%",
                    value == null ? "N/A" : "" + value);
            this.updateDefaultButton("TranslationY", "%" + placeholderRegexY + "%",
                    value == null ? "N/A" : "" + value);
            this.updateDefaultButton("TranslationZ", "%" + placeholderRegexZ + "%",
                    value == null ? "N/A" : "" + value);
            this.openInventory();
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
                    BlockDisplayAsset build = builder.construct();
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
    public BlockDisplayAsset construct() {
        ObjectBuilderButton<String> keyButton = (ObjectBuilderButton<String>) getObjectBuilderButton("Key");
        ObjectBuilderButton<Block> blockDataButton = (ObjectBuilderButton<Block>) getObjectBuilderButton("Icon");
        ObjectBuilderButton<Float> scaleXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleX");
        ObjectBuilderButton<Float> scaleYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleY");
        ObjectBuilderButton<Float> scaleZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("ScaleZ");
        ObjectBuilderButton<Float> translationXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationX");
        ObjectBuilderButton<Float> translationYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationY");
        ObjectBuilderButton<Float> translationZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("TranslationZ");
        ObjectBuilderButton<Float> leftRotationXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftRotationX");
        ObjectBuilderButton<Float> leftRotationYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftRotationY");
        ObjectBuilderButton<Float> leftRotationZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftRotationZ");
        ObjectBuilderButton<Float> leftRotationWButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("LeftRotationW");
        ObjectBuilderButton<Float> rightRotationXButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightRotationX");
        ObjectBuilderButton<Float> rightRotationYButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightRotationY");
        ObjectBuilderButton<Float> rightRotationZButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightRotationZ");
        ObjectBuilderButton<Float> rightRotationWButton = (ObjectBuilderButton<Float>) getObjectBuilderButton("RightRotationW");

        if (!keyButton.isValuePresentAndNotNull() || !blockDataButton.isValuePresentAndNotNull() ||
                !scaleXButton.isValuePresentAndNotNull() || !scaleYButton.isValuePresentAndNotNull() || !scaleZButton.isValuePresentAndNotNull() ||
                !translationXButton.isValuePresentAndNotNull() || !translationYButton.isValuePresentAndNotNull() ||
                !translationZButton.isValuePresentAndNotNull() || !leftRotationXButton.isValuePresentAndNotNull() ||
                !leftRotationYButton.isValuePresentAndNotNull() || !leftRotationZButton.isValuePresentAndNotNull() ||
                !leftRotationWButton.isValuePresentAndNotNull() || !rightRotationXButton.isValuePresentAndNotNull() ||
                !rightRotationYButton.isValuePresentAndNotNull() || !rightRotationZButton.isValuePresentAndNotNull() ||
                !rightRotationWButton.isValuePresentAndNotNull())
            return null;
        String key = keyButton.orNull();
        BlockData blockData = blockDataButton.orNull().getBlockData();
        if (blockData.getMaterial().isAir()) {
            Player player = getPlayer();
            player.closeInventory();
            BlobLibAssetAPI.getMessage("BlobDesign.Block-Cannot-Be-Air")
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
        return new BlockDisplayAsset(key, blockData, scale, translation,
                leftRotation, rightRotation);
    }
}