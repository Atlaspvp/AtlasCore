package net.atlaspvp.atlascore.Features.RMISyncing.Events.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Join implements Listener {

    private final HashMap<UUID, Object> players = new HashMap<>();

    @EventHandler
    public void preJoin(AsyncPlayerPreLoginEvent event) {
        UUID playerUUID = event.getUniqueId();
        //get player data from database (blocking call, want to wait for this to finish before allowing the player to join)

        Object playerData = null;

        //save player data to local cache
        players.put(playerUUID, playerData);
    }



    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        //get player data from local cache
        UUID playerUUID = event.getPlayer().getUniqueId();
        Object playerData = players.get(playerUUID);

        //apply player data to player


        //remove from local cache
        players.remove(playerUUID);



    }

}
