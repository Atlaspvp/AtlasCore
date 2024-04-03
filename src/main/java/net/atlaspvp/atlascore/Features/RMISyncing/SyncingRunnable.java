package net.atlaspvp.atlascore.Features.RMISyncing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.Set;

public class SyncingRunnable extends BukkitRunnable {





    @Override
    public void run() {
        //get all player data online and send them to data server (test if this causes lag spikes)



       for(Player player : Bukkit.getOnlinePlayers()) {
           //get player data
           //probably want to combine all playerdata into 1 object and send that object to the data server
           //this will reduce the amount of rmi calls we need to make, which is recommended

       }


    }

}
