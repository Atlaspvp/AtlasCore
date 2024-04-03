package net.atlaspvp.atlascore.Features.RabbitMQ.Players.Listener;

import net.atlaspvp.atlascore.Features.RabbitMQ.Players.OnlinePlayers;
import net.atlaspvp.atlascore.Features.RabbitMQ.Players.PlayerData;
import net.atlaspvp.atlascore.Features.RabbitMQ.RabbitMQ;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class OnJoin implements Listener {


    @EventHandler
    public void preJoin(AsyncPlayerPreLoginEvent event) throws InterruptedException {
        //check if the player is already online on any server (and the quit is thus not processed yet)



        String currentServer = RabbitMQ.serverID;
        UUID playerUUID = event.getUniqueId();

        if(!OnlinePlayers.safeToJoin(playerUUID)){
            playerUUID.wait();
        }

        //safe to get data from persistent storage (if it is contained)

        OnlinePlayers.logOnPlayer(playerUUID);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){


    }
}
