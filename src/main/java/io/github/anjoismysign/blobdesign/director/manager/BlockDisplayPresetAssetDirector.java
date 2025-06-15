package io.github.anjoismysign.blobdesign.director.manager;

import io.github.anjoismysign.anjo.entities.Result;
import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.blobdesign.director.DesignObjectDirector;
import io.github.anjoismysign.blobdesign.entities.BlockDisplayPreset;
import io.github.anjoismysign.blobdesign.entities.BlockDisplayPresetAsset;
import io.github.anjoismysign.blobdesign.entities.inventory.BlockDisplayBuilder;
import io.github.anjoismysign.blobdesign.entities.proxy.BlockDisplayPresetAssetProxy;
import io.github.anjoismysign.bloblib.api.BlobLibMessageAPI;
import io.github.anjoismysign.bloblib.entities.BlobChildCommand;
import io.github.anjoismysign.bloblib.entities.BlobExecutor;
import io.github.anjoismysign.bloblib.entities.ObjectDirectorData;

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
            if (args.length != 2)
                return null;
            String argument = args[0];
            if (!argument.equalsIgnoreCase("placer"))
                return null;
            return new ArrayList<>(getObjectManager().keys());
        });
    }
}
