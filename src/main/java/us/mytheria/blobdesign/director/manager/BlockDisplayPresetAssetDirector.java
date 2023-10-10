package us.mytheria.blobdesign.director.manager;

import me.anjoismysign.anjo.entities.Result;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.blobdesign.director.DesignObjectDirector;
import us.mytheria.blobdesign.entities.BlockDisplayPreset;
import us.mytheria.blobdesign.entities.BlockDisplayPresetAsset;
import us.mytheria.blobdesign.entities.inventory.BlockDisplayBuilder;
import us.mytheria.blobdesign.entities.proxy.BlockDisplayPresetAssetProxy;
import us.mytheria.bloblib.BlobLibAssetAPI;
import us.mytheria.bloblib.entities.BlobChildCommand;
import us.mytheria.bloblib.entities.BlobExecutor;
import us.mytheria.bloblib.entities.ObjectDirectorData;

import java.util.ArrayList;
import java.util.List;

public class BlockDisplayPresetAssetDirector extends DesignObjectDirector<BlockDisplayPresetAssetProxy> {
    public BlockDisplayPresetAssetDirector(DesignManagerDirector managerDirector) {
        super(managerDirector, ObjectDirectorData.simple(managerDirector.getRealFileManager(),
                "BlockDisplay"), file ->
                BlockDisplayPresetAsset.fromFile(file, managerDirector), true);
        getBuilderManager()
                .setBuilderBiFunction((uuid, objectDirector) ->
                        BlockDisplayBuilder.build(uuid, objectDirector,
                                managerDirector));
        addAdminChildCommand(executorData -> {
            BlobExecutor executor = executorData.executor();
            String[] args = executorData.args();
            Result<BlobChildCommand> result = executor
                    .isChildCommand("placer", executorData.args());
            return result.isValid() && executor.ifInstanceOfPlayer(executorData.sender(),
                    player -> {
                        if (args.length < 2) {
                            player.sendMessage("Usage: /blockdisplay placer <key>");
                            return;
                        }
                        String key = args[1];
                        BlockDisplayPreset preset =
                                this.getObjectManager().getObject(key);
                        if (preset == null) {
                            player.sendMessage("No blockDisplay with key " + key + " found.");
                            return;
                        }
                        preset.givePlacer(player);
                        BlobLibAssetAPI.getMessage("BlobDesign.Placer-Given")
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
            if (args.length != 2)
                return null;
            String argument = args[0];
            if (!argument.equalsIgnoreCase("placer"))
                return null;
            return new ArrayList<>(getObjectManager().keys());
        });
    }
}
