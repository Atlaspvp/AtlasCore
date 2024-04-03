package net.atlaspvp.atlascore.Features.RabbitMQ.Players.Events;

import net.atlaspvp.atlascore.Features.MultiServer.SyncedPlayer;
import net.atlaspvp.atlascore.Features.RabbitMQ.Players.PlayerData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PrePDPushEvent extends Event {
    //fires when a synced player is going to leave the current server and join another server
    //plugins should listen to this to save any date they want to pass to the other server


    private final PlayerData playerData;
    HandlerList handlers = new HandlerList();

    public PrePDPushEvent(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public PlayerData getPlayerData(){
        return playerData;
    }


}
