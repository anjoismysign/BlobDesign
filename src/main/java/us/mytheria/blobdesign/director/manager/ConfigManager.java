package us.mytheria.blobdesign.director.manager;

import org.bukkit.configuration.file.FileConfiguration;
import us.mytheria.blobdesign.BlobDesign;
import us.mytheria.blobdesign.director.DesignManager;
import us.mytheria.blobdesign.director.DesignManagerDirector;
import us.mytheria.bloblib.entities.SimpleEventListener;

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