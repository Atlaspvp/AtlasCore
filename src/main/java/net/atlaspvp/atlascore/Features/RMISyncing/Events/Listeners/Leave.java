package net.atlaspvp.atlascore.Features.RMISyncing.Events.Listeners;

import net.atlaspvp.atlascore.Features.RMISyncing.SyncingRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Leave implements Listener {

    private final SyncingRunnable syncingRunnable;

    public Leave(SyncingRunnable syncingRunnable) {
        this.syncingRunnable = syncingRunnable;
    }


    @EventHandler
    public void onLeave(org.bukkit.event.player.PlayerQuitEvent event) {
        //get playerdata from player

        Object playerData = null;

        //save player data to database (async call)

        //tell db player is leaving

        //drop player from syncing queue, we already synced
        syncingRunnable.dropPlayer(event.getPlayer());

    }
}
