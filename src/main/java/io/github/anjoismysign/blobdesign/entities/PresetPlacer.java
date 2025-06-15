package io.github.anjoismysign.blobdesign.entities;

import io.github.anjoismysign.blobdesign.BlobDesignAPI;
import io.github.anjoismysign.blobdesign.entities.element.DisplayElementType;
import io.github.anjoismysign.bloblib.entities.BukkitPluginOperator;
import io.github.anjoismysign.bloblib.itemstack.ItemStackModder;
import io.github.anjoismysign.bloblib.utilities.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface PresetPlacer {

    /**
     * Will return either the PresetPlacer or null if the ItemStack is not a preset placer.
     *
     * @param itemStack The ItemStack to check
     * @param operator  The operator to use for generating @{@link NamespacedKey NamespacedKeys}
     * @return null if not a preset placer, the PresetPlacer otherwise
     */
    @Nullable
    static PresetPlacer isPresetPlacer(@NotNull ItemStack itemStack,
                                       @NotNull BukkitPluginOperator operator) {
        PresetPlacer placer;
        try {
            placer = isPresetPlacerFailFast(itemStack, operator);
        } catch (Exception e) {
            return null;
        }
        return placer;
    }

    /**
     * Will fail fast if the ItemStack is not a preset placer.
     * Wrap with try catch!
     *
     * @param itemStack The ItemStack to check
     * @param operator  The operator to use for generating @{@link NamespacedKey NamespacedKeys}
     * @return The deserialized PresetPlacer
     * @throws NullPointerException If the ItemStack is not a preset placer
     */
    @NotNull
    static PresetPlacer isPresetPlacerFailFast(@NotNull ItemStack itemStack,
                                               @NotNull BukkitPluginOperator operator) throws NullPointerException {
        /*
        In a future server admins will be allowed to create their own ItemStacks
        for preset placers. Currently, command blocks are used as a
        high performance discriminator.
         */
        if (itemStack.getType() != Material.COMMAND_BLOCK)
            throw new IllegalStateException("Discriminated material");
        ItemMeta meta = Objects.requireNonNull(itemStack.getItemMeta());
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return Objects.requireNonNull(deserialize(container, operator), "Not a preset placer");
    }

    /**
     * Will deserialize a PresetPlacer from a PersistentDataContainer
     *
     * @param container The container to deserialize from
     * @param operator  The operator to use for generating @{@link NamespacedKey NamespacedKeys}
     * @return The deserialized PresetPlacer
     */
    @Nullable
    static PresetPlacer deserialize(@NotNull PersistentDataContainer container,
                                    @NotNull BukkitPluginOperator operator) {
        NamespacedKey x = operator
                .generateNamespacedKey("dilement_type");
        NamespacedKey y = operator
                .generateNamespacedKey("preset_key");
        if (!container.has(x, PersistentDataType.STRING))
            return null;
        if (!container.has(y, PersistentDataType.STRING))
            return null;
        String serializedDilementType = container.get(x, PersistentDataType.STRING);
        String presetKey = container.get(y, PersistentDataType.STRING);
        DisplayElementType dilementType = DisplayElementType
                .valueOf(serializedDilementType);
        return BlobDesignAPI.getPreset(dilementType, presetKey);
    }

    /**
     * Will serialize the PresetPlacer to a PersistentDataContainer
     *
     * @param container The container to serialize to
     */
    void serialize(PersistentDataContainer container);

    /**
     * Will get the PresetData from the PresetPlacer
     *
     * @return The PresetData
     */
    @NotNull
    PresetData getPresetData();

    /**
     * Will serialize the PresetPlacer to a PersistentDataContainer allowing
     * passing a custom BukkitPluginOperator for generating @{@link NamespacedKey NamespacedKeys}
     *
     * @param container The container to serialize to
     * @param operator  The operator to use for generating NamespacedKeys
     */
    default void serialize(PersistentDataContainer container,
                           BukkitPluginOperator operator) {
        PresetData data = getPresetData();
        container.set(operator.generateNamespacedKey("dilement_type"),
                PersistentDataType.STRING,
                data.type().name());
        container.set(operator.generateNamespacedKey("preset_key"),
                PersistentDataType.STRING,
                data.key());
    }

    /**
     * Will create a preset placer ItemStack
     *
     * @return The preset placer ItemStack
     * @apiNote Will fail fast if the PresetPlacer is null
     */
    @NotNull
    default ItemStack createPlacer() {
        ItemStack itemStack = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta itemMeta = Objects.requireNonNull(itemStack.getItemMeta(), "createPlacer itemMeta is null");
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        serialize(container);
        itemStack.setItemMeta(itemMeta);
        String places = getPresetData().type() == DisplayElementType.BLOCK_DISPLAY ? "BlockDisplayPreset" : "ItemDisplayPreset";
        ItemStackModder.mod(itemStack)
                .displayName(TextColor.PARSE("&cPlacer"))
                .lore(TextColor.PARSE("&7Places: &f" + places),
                        TextColor.PARSE("&7Preset key: &f" + getPresetData().key()),
                        "",
                        TextColor.PARSE("&c&lPRESET-PLACER"))
                .amount(64);
        return itemStack;
    }

    default void givePlacer(Inventory inventory) {
        inventory.addItem(createPlacer());
    }

    default void givePlacer(Player player) {
        givePlacer(player.getInventory());
    }
}
