package net.atlaspvp.atlascore.Features.MultiServer.CustomEvents;

import net.atlaspvp.atlascore.Features.MultiServer.SyncedPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SyncedPlayerUpdate extends Event {
    //fires when a syncedPlayer is updated with data from another server/rabbitmq
    //plugins should listen to this if they use syncedPlayer to pass information between servers
    //it is usually fired when a syncedPlayer joins from another server

    private final SyncedPlayer syncedPlayer;
    HandlerList handlers = new HandlerList();

    public SyncedPlayerUpdate(SyncedPlayer syncedPlayer) {
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
