package net.atlaspvp.atlascore.Features.RabbitMQ.Players;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OnlinePlayers {

    private static final HashMap<UUID, PlayerData> onlinePlayers = new HashMap<>();
    //keep track of which players are online on the linked network (all servers running the core)

    private static final Set<UUID> pendingJoins = new HashSet<>();
    //set of all uuids that are trying to join but cant yet because they are still online on an other server

    public static void logOffPlayer(UUID playerUUID){
        //remove the player from online layers map
        onlinePlayers.remove(playerUUID);

        //check if the player is waiting to join the server, if he is notify
        if(pendingJoins.contains(playerUUID)){
            pendingJoins.remove(playerUUID);
            playerUUID.notify();
        }
    }

    public static void logOnPlayer(UUID playerUUID){
        //check if in persistent storage

        //otherwise create a new playerdata entry for this player
        onlinePlayers.put(playerUUID, new PlayerData(playerUUID));

    }

    public static boolean safeToJoin(UUID playerUUID){
        //check if they player is safe to join this server, or if he is still online on another server
        return !onlinePlayers.containsKey(playerUUID);
    }



    public static PlayerData getPlayerData(UUID playerUUID){
        //only call async as it can take a while
        if(onlinePlayers.containsKey(playerUUID)){
            return getOnlinePlayerData(playerUUID);
        }else{
            //player is offline, get data from rabbitmq (persistent storage)
            return getOfflinePlayerData(playerUUID);
        }
    }

    public static PlayerData getOnlinePlayerData(UUID playerUUID){
        //safe to call from main thread
        return onlinePlayers.get(playerUUID);
    }

    private static PlayerData getOfflinePlayerData(UUID playerUUID){
        return null;
    }



}
