package net.atlaspvp.atlascore.Features.MultiServer;

import net.atlaspvp.atlascore.Features.MultiServer.CustomEvents.SyncedPlayerUpdate;

import java.util.HashMap;
import java.util.UUID;


public class SyncedPlayer {


    private final UUID playerUUID;
    private HashMap<String, SerializablePlayerData> data = new HashMap<>();

    public SyncedPlayer(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }


    public void transferPlayerToServer(String targetServer){
        //syrialize data

        //sync data with rabbitmq

        //wait for verification that data is up to date with server

        //send player to target server
    }


    public void distributeData(){
        //push the data hashmap to the server/player
        //tell all plugins new data is coming in
        new SyncedPlayerUpdate(this);

    }


    public void pushData(){
        //push the data hashmap to rabbitmq so other servers can get




    }

    public void getData(){
        //get the data hashmap from rabbitmq

    }


}
