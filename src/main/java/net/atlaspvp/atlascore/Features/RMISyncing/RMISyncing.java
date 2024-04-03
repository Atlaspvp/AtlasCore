package net.atlaspvp.atlascore.Features.RMISyncing;

import net.atlaspvp.atlascore.Struct.Feature;
import org.bukkit.plugin.Plugin;

public class RMISyncing extends Feature {

    private boolean state = false;

    @Override
    public void onEnable(Plugin plugin) {
        //setup rmi connection


        //register events

        //start syncing runnable
        int interval = 5 * 60 * 20; //5 minutes
        new SyncingRunnable().runTaskTimer(plugin, interval, interval);

        //set state to true

        this.state = true;

    }

    @Override
    public void onDisable() {

        //tell dataserver server is shutting down

        //disconnect from rmi server


        //set state to false
        this.state = false;

    }

    @Override
    public boolean getState() {
        return state;
    }
}
