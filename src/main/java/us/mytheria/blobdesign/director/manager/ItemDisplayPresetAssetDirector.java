package us.mytheria.blobdesign.director.manager;

import me.anjoismysign.anjo.entities.Result;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.DesignObjectDirector;
import us.mytheria.blobdesign.entities.ItemDisplayPreset;
import us.mytheria.blobdesign.entities.ItemDisplayPresetAsset;
import us.mytheria.blobdesign.entities.inventory.ItemDisplayBuilder;
import us.mytheria.blobdesign.entities.proxy.ItemDisplayPresetAssetProxy;
import us.mytheria.bloblib.api.BlobLibMessageAPI;
import us.mytheria.bloblib.entities.BlobChildCommand;
import us.mytheria.bloblib.entities.BlobExecutor;
import us.mytheria.bloblib.entities.ObjectDirectorData;

import java.util.ArrayList;
import java.util.List;

public class ItemDisplayPresetAssetDirector extends DesignObjectDirector<ItemDisplayPresetAssetProxy> {
    public ItemDisplayPresetAssetDirector(DesignManagerDirector managerDirector) {
        super(managerDirector, ObjectDirectorData.simple(managerDirector.getRealFileManager(),
                "ItemDisplay"), file ->
                ItemDisplayPresetAsset.fromFile(file, managerDirector), true);
        getBuilderManager()
                .setBuilderBiFunction((uuid, objectDirector) ->
                        ItemDisplayBuilder.build(uuid, objectDirector,
                                managerDirector));
        addAdminChildCommand(executorData -> {
            BlobExecutor executor = executorData.executor();
            String[] args = executorData.args();
            Result<BlobChildCommand> result = executor
                    .isChildCommand("placer", executorData.args());
            return result.isValid() && executor.ifInstanceOfPlayer(executorData.sender(),
                    player -> {
                        if (args.length < 2) {
                            player.sendMessage("Usage: /itemdisplay placer <key>");
                            return;
                        }
                        String key = args[1];
                        ItemDisplayPreset preset =
                                this.getObjectManager().getObject(key);
                        if (preset == null) {
                            player.sendMessage("No itemDisplay with key " + key + " found.");
                            return;
                        }
                        preset.givePlacer(player);
                        BlobLibMessageAPI.getInstance()
                                .getMessage("BlobDesign.Placer-Given", player)
                                .handle(player);
                    });
        });
        addAdminChildTabCompleter(executorData -> {
            String[] args = executorData.args();
            if (args.length != 1)
                return null;
            List<String> list = new ArrayList<>();
            list.add("placer");
            return list;
        });
        addAdminChildTabCompleter(executorData -> {
            String[] args = executorData.args();
            if (args.length != 2) {
                return null;
            }
            String argument = args[0];
            if (!argument.equalsIgnoreCase("placer")) {
                return null;
            }
            return new ArrayList<>(getObjectManager().keys());
        });
    }
}
