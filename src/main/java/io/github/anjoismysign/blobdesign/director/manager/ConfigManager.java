package io.github.anjoismysign.blobdesign.director.manager;

import io.github.anjoismysign.blobdesign.BlobDesign;
import io.github.anjoismysign.blobdesign.director.DesignManager;
import io.github.anjoismysign.blobdesign.director.DesignManagerDirector;
import io.github.anjoismysign.bloblib.entities.SimpleEventListener;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager extends DesignManager {
    private SimpleEventListener<Boolean> listenerExample;

    public ConfigManager(DesignManagerDirector managerDirector) {
        super(managerDirector);
        reload();
    }

    @Override
    public void reload() {
        BlobDesign main = getPlugin();
        FileConfiguration config = main.getConfig();
        config.options().copyDefaults(true);
//        ConfigurationSection listeners = config.getConfigurationSection("Listeners");
//        listenerExample = SimpleEventListener.BOOLEAN(
//                (listeners.getConfigurationSection("UseUUIDs")), "State");
        listenerExample = null;
        main.saveConfig();
    }

    public SimpleEventListener<Boolean> useUUIDs() {
        return listenerExample;
    }
}