package net.atlaspvp.atlascore.Features.PlayerStat;

import java.util.HashMap;

public class PlayerStat {


    public final HashMap<String, Stat> stats = new HashMap<>();

    public Stat getStat(String id){


        if(!stats.containsKey(id)){
            stats.put(id, new Stat(id, 0, 0,0));
        }
        return stats.get(id);
    }



}
