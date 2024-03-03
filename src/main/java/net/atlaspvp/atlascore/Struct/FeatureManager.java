package net.atlaspvp.atlascore.Struct;

import com.google.common.collect.Maps;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class FeatureManager {
    private final Map<String, Feature> Features = Maps.newHashMap();

    public static void registerFeature(Plugin plugin) {

    }

    public static void loadFeature(Plugin plugin) {

    }

    public boolean isLoaded(Feature feature){
        return Features.containsKey(feature.name);
    }
}
