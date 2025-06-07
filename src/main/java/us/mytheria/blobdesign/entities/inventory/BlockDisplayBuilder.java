package us.mytheria.blobdesign.entities.inventory;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.entities.BlockDisplayPresetAsset;
import us.mytheria.blobdesign.entities.DesignDisplayOperator;
import us.mytheria.blobdesign.entities.ImmutableDisplayOperator;
import us.mytheria.blobdesign.entities.proxy.BlockDisplayPresetAssetProxy;
import us.mytheria.blobdesign.entities.proxy.DesignProxifier;
import us.mytheria.bloblib.BlobLibAPI;
import us.mytheria.bloblib.api.BlobLibInventoryAPI;
import us.mytheria.bloblib.api.BlobLibListenerAPI;
import us.mytheria.bloblib.api.BlobLibMessageAPI;
import us.mytheria.bloblib.api.BlobLibSoundAPI;
import us.mytheria.bloblib.entities.ObjectDirector;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.inventory.BlobInventory;
import us.mytheria.bloblib.entities.inventory.ObjectBuilderButton;
import us.mytheria.bloblib.entities.inventory.ObjectBuilderButtonBuilder;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@SuppressWarnings("ConcatenationWithEmptyString")
public class BlockDisplayBuilder extends DesignBuilder<BlockDisplayPresetAssetProxy> {

    public static BlockDisplayBuilder build(UUID builderId,
                                            ObjectDirector<BlockDisplayPresetAssetProxy> objectDirector,
                                            DesignManagerDirector director) {
        var carrier = BlobLibInventoryAPI.getInstance().getInventoryBuilderCarrier("BlockDisplayBuilder");
        Objects.requireNonNull(carrier, "BlockDisplayBuilder cannot be null");
        return new BlockDisplayBuilder(
                BlobInventory.fromInventoryBuilderCarrier(carrier),
                builderId, objectDirector, director);
    }

    private BlockDisplayBuilder(BlobInventory blobInventory, UUID builderId,
                                ObjectDirector<BlockDisplayPresetAssetProxy> objectDirector,
                                DesignManagerDirector director) {
        super(blobInventory, builderId, objectDirector, director);
        ObjectBuilderButton<String> keyButton = ObjectBuilderButtonBuilder.QUICK_STRING(
                "Key", 300, this);
        ObjectBuilderButton<Block> iconButton = ObjectBuilderButtonBuilder.QUICK_BLOCK("Icon-Block",
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
                .addObjectBuilderButton(new ObjectBuilderButton<>("UniformScale", Optional.empty(),
                        (button, player) -> BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                            try {
                                if (string.equalsIgnoreCase("null")) {
                                    button.set(null);
                                    return;
                                }
                                float input = Float.parseFloat(string);
                                if (uniformScaleFunction.apply(input))
                                    button.set(input);
                            } catch (NumberFormatException ignored) {
                                BlobLibMessageAPI.getInstance()
                                        .getMessage("Builder.Number-Exception", player)
                                        .handle(player);
                            }
                        }, "Builder.UniformScale-Timeout", "Builder.UniformScale"),
                        uniformScaleFunction) {
                }).addObjectBuilderButton(new ObjectBuilderButton<>("UniformTranslation", Optional.empty(),
                        (button, player) -> BlobLibListenerAPI.getInstance().addChatListener(player, 300, string -> {
                            try {
                                if (string.equalsIgnoreCase("null")) {
                                    button.set(null);
                                    return;
                                }
                                float input = Float.parseFloat(string);
                                if (uniformTranslationFunction.apply(input))
                                    button.set(input);
                            } catch (NumberFormatException ignored) {
                                BlobLibMessageAPI.getInstance()
                                        .getMessage("Builder.Number-Exception", player)
                                        .handle(player);
                            }
                        }, "Builder.UniformTranslation-Timeout", "Builder.UniformTranslation"),
                        uniformTranslationFunction) {
                })
                .setFunction(builder -> {
                    BlockDisplayPresetAssetProxy build = builder.construct();
                    if (build == null)
                        return null;
                    Player player = getPlayer();
                    BlobLibSoundAPI.getInstance()
                            .getSound("Builder.Build-Complete")
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
    public BlockDisplayPresetAssetProxy construct() {
        ObjectBuilderButton<String> keyButton = (ObjectBuilderButton<String>) getObjectBuilderButton("Key");
        ObjectBuilderButton<Block> iconBlockButton = (ObjectBuilderButton<Block>) getObjectBuilderButton("Icon-Block");
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

        if (!keyButton.isValuePresentAndNotNull() ||
                !iconBlockButton.isValuePresentAndNotNull() ||
                !scaleXButton.isValuePresentAndNotNull() ||
                !scaleYButton.isValuePresentAndNotNull() ||
                !scaleZButton.isValuePresentAndNotNull() ||
                !translationXButton.isValuePresentAndNotNull() ||
                !translationYButton.isValuePresentAndNotNull() ||
                !translationZButton.isValuePresentAndNotNull() ||
                !leftRotationXButton.isValuePresentAndNotNull() ||
                !leftRotationYButton.isValuePresentAndNotNull() ||
                !leftRotationZButton.isValuePresentAndNotNull() ||
                !leftRotationWButton.isValuePresentAndNotNull() ||
                !rightRotationXButton.isValuePresentAndNotNull() ||
                !rightRotationYButton.isValuePresentAndNotNull() ||
                !rightRotationZButton.isValuePresentAndNotNull() ||
                !rightRotationWButton.isValuePresentAndNotNull())
            return null;
        String key = keyButton.orNull();
        BlockData blockData = iconBlockButton.orNull().getBlockData();
        if (blockData.getMaterial().isAir()) {
            Player player = getPlayer();
            player.closeInventory();
            BlobLibMessageAPI.getInstance()
                    .getMessage("BlobDesign.Block-Cannot-Be-Air", player)
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
        Transformation transformation = new Transformation(translation, leftRotation, scale, rightRotation);
        JavaPlugin plugin = getManagerDirector().getPlugin();
        DisplayData displayData = DisplayData.DEFAULT;
        DesignDisplayOperator displayOperator = new ImmutableDisplayOperator(plugin, displayData, transformation);
        return DesignProxifier.PROXY(new BlockDisplayPresetAsset(key, displayOperator, blockData, getManagerDirector()));
    }
}