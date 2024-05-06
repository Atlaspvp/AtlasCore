package net.atlaspvp.atlascore.Struct;

import com.google.common.collect.Maps;
import net.atlaspvp.atlascore.AtlasCore;
import net.atlaspvp.atlascore.Features.Essentials.Essentials;
import net.atlaspvp.atlascore.Features.PlayerVaults.PlayerVaults;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class FeatureManager {
    private static final Map<String, Feature> Features = Maps.newHashMap();

    public static void registerFeatures(JavaPlugin plugin) {
        Features.put("Essentials", new Essentials());
        Features.put("PlayerVaults", new PlayerVaults());
        loadFeature(plugin);
    }

    public static void loadFeature(JavaPlugin plugin) {
        for (Map.Entry<String, Feature> entry : Features.entrySet()) {
            String name = entry.getKey();
            Feature feature = entry.getValue();

            AtlasCore.Log("&7Attempting to Load " + name);

            try {
                feature.onEnable(plugin);
                if (feature.getState()) {
                    AtlasCore.Log("&aSuccessfully loaded " + name);
                } else {
                    AtlasCore.Log("&c" + name + " failed to load: State not set properly");
                }
            } catch (Exception e) {
                AtlasCore.Log("&cAn error occurred loading " + name + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
