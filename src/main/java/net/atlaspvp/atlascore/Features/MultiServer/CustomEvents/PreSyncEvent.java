package net.atlaspvp.atlascore.Features.MultiServer.CustomEvents;

import net.atlaspvp.atlascore.Features.MultiServer.SyncedPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PreSyncEvent extends Event {
    //fires when a synced player is going to leave the current server and join another server
    //plugins should listen to this to save any date they want to pass to the other server


    private final SyncedPlayer syncedPlayer;
    HandlerList handlers = new HandlerList();

    public PreSyncEvent(SyncedPlayer syncedPlayer) {
        this.syncedPlayer = syncedPlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public SyncedPlayer getSyncedPlayer(){
        return syncedPlayer;
    }
}
