package net.atlaspvp.atlascore.Features.PlayerStat;

import net.atlaspvp.atlascore.AtlasCore;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Stats {

    private static final HashMap<String, Stat> registeredStats = new HashMap<>();


    public static Stat getDefaultStat(String id){
        return registeredStats.get(id);
    }

    public static void registerStat(Stat stat) {
        if(registeredStats.containsKey(stat.getKey())){
            AtlasCore.Log("Stat " + stat.getKey() + " already registered");
            return;
        }
        registeredStats.put(stat.getKey(), stat);
        AtlasCore.Log("Stat " + stat.getKey() + " succesfully registered");
    }


    public static Set<String> getRegisteredStats(){
        return registeredStats.keySet();
    }
}
