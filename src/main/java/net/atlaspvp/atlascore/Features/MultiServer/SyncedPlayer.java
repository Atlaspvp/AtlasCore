package net.atlaspvp.atlascore.Features.MultiServer;

import net.atlaspvp.atlascore.Features.MultiServer.CustomEvents.SyncedPlayerUpdate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.*;


public class SyncedPlayer {


    private final UUID playerUUID;
    private HashMap<String, Serializable> customData = new HashMap<>();

    private ItemStack[] inventory = new ItemStack[36];

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


    private void getInventory(){
        //get the players inventory and put it into the inventory array
        PlayerInventory bukkitInventory = Bukkit.getPlayer(playerUUID).getInventory();

        bukkitInventory.getContents();


    }

    private void setInventory(){
        //set the players inventory from the inventory array
    }






}
