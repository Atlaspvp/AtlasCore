package net.atlaspvp.atlascore.Features.MultiServer.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        //?does this fire after the player quit? how does that work with accessing inventory?
        //update synced player data


        //send data to other servers/disk

        //remove data from memory



    }

}
