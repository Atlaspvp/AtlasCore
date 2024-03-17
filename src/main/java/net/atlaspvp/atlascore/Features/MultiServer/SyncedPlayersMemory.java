package net.atlaspvp.atlascore.Features.MultiServer;

import java.util.HashMap;
import java.util.UUID;

public class SyncedPlayersMemory {

    private static final HashMap<UUID, SyncedPlayer> syncedPlayerHashMap = new HashMap<>();

    public static void addSyncedPlayer(UUID playerUUID ,SyncedPlayer syncedPlayer){
        syncedPlayerHashMap.put(playerUUID, syncedPlayer);
    }

    public static SyncedPlayer getSyncedPlayer(UUID playerUUID){
        return syncedPlayerHashMap.get(playerUUID);
    }

    public static void removeSyncedPlayer(UUID playerUUID){
        syncedPlayerHashMap.remove(playerUUID);
    }




}
