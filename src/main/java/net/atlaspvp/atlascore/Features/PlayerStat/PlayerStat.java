package net.atlaspvp.atlascore.Features.PlayerStat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerStat implements Listener {

    private static final HashMap<String, Stat> playerStatHashMap = new HashMap<>();

    public static Stat getStat(String id, Player player){
        String key = player.getUniqueId().toString()+";"+id;

        if(!playerStatHashMap.containsKey(key)){
            playerStatHashMap.put(key, Stats.getDefaultStat(id));
        }
        return playerStatHashMap.get(key);
    }



    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        for(String id : Stats.getRegisteredStats()){
            playerStatHashMap.remove(e.getPlayer().getUniqueId().toString()+";"+id);
        }
    }
}
