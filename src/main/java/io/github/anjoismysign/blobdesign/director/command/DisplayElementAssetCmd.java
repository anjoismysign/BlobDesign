package io.github.anjoismysign.blobdesign.director.command;

import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.bloblib.api.BlobLibMessageAPI;
import io.github.anjoismysign.bloblib.entities.BlobExecutor;

import java.util.List;

public class DisplayElementAssetCmd extends BlobExecutor {
    public DisplayElementAssetCmd(DesignManagerDirector director) {
        super(director.getPlugin(), "dilement");
        setCommand((sender, args) -> {
            if (!hasAdminPermission(sender))
                return true;
            if (args.length < 1) {
                sender.sendMessage("Usage: /dilement reload");
                return true;
            }
            String arg = args[0];
            if (arg.equalsIgnoreCase("reload")) {
                director.getDisplayElementAssetDirector().reload();
                BlobLibMessageAPI.getInstance()
                        .getMessage("BlobDesign.Reloaded",
                                sender)
                        .toCommandSender(sender);
                return true;
            }
            sender.sendMessage("Usage: /dilement reload");
            return true;
        });
        setTabCompleter((sender, args) -> {
            if (args.length != 1)
                return null;
            if (!hasAdminPermission(sender))
                return null;
            return List.of("reload");
        });
    }
}
