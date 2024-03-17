package net.atlaspvp.atlascore.Features.MultiServer.Listeners;

import net.atlaspvp.atlascore.Features.MultiServer.SyncedPlayer;
import net.atlaspvp.atlascore.Features.MultiServer.SyncedPlayersMemory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class OnJoin implements Listener {



    @EventHandler
    public void preJoinEvent(AsyncPlayerPreLoginEvent event){
        //all done async


        //create a syncedplayerobject
        UUID playerUUID = event.getUniqueId();
        SyncedPlayer syncedPlayer = new SyncedPlayer(playerUUID);

        //retrieve data from rabbitmq
        syncedPlayer.getData();
        //save to memory
        SyncedPlayersMemory.addSyncedPlayer(playerUUID, syncedPlayer);
        //we have now an uptodate, in memory syncedPlayer object

    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event){
        //sync the syncedPlayer object with all plugins and player info
        UUID playerUUID = event.getPlayer().getUniqueId();

        SyncedPlayer syncedPlayer = SyncedPlayersMemory.getSyncedPlayer(playerUUID);

        syncedPlayer.distributeData();


    }


}
