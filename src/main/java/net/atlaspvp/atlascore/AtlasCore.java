package net.atlaspvp.atlascore;

import net.atlaspvp.atlascore.Features.PlayerVaults.PlayerVaults;
import net.atlaspvp.atlascore.Struct.FeatureManager;
import net.atlaspvp.atlascore.Utils.Chat;
import org.bukkit.plugin.java.JavaPlugin;

public final class AtlasCore extends JavaPlugin {
    public static String prefix = "&#DAF7A6[AtlasCore]&r "; // Example prefix drk what we want to make it
    private static AtlasCore instance;
    private static JavaPlugin plug;

    @Override
    public void onEnable() {
        instance = this;
        FeatureManager.registerFeatures(instance);
        getServer().getPluginManager().registerEvents(new PlayerVaults(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void Log(String cont) {
        instance.getServer().getConsoleSender().sendMessage(Chat.AltFormat(prefix + cont));
    }

    public static AtlasCore getInstance() {
        return instance;
    }
}
