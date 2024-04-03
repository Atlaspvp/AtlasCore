package net.atlaspvp.atlascore.Features.RabbitMQ.Players.Listener;

import net.atlaspvp.atlascore.AtlasCore;
import net.atlaspvp.atlascore.Features.RabbitMQ.Players.Events.PrePDPushEvent;
import net.atlaspvp.atlascore.Features.RabbitMQ.Players.OnlinePlayers;
import net.atlaspvp.atlascore.Features.RabbitMQ.Players.PlayerData;
import net.atlaspvp.atlascore.Features.RabbitMQ.RabbitMQ;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class OnQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        String currentServer = RabbitMQ.serverID;
        UUID playerUUID = event.getPlayer().getUniqueId();

        PlayerData playerData = OnlinePlayers.getPlayerData(playerUUID);

        new PrePDPushEvent(playerData);

        new BukkitRunnable(){

            @Override
            public void run() {
                //push data to database


            }
        }.runTaskAsynchronously(AtlasCore.getInstance());

        OnlinePlayers.logOffPlayer(playerUUID);
    }

}
