package net.atlaspvp.atlascore.Features.RabbitMQ.Players;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerData {

    private final UUID playerUUID;

    private String currentServer;

    private ItemStack[] inventory = new ItemStack[36];

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public PlayerData(UUID playerUUID, String currentServer) {
        this.playerUUID = playerUUID;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getCurrentServer() {
        return currentServer;
    }

    public void setCurrentServer(String currentServer) {
        this.currentServer = currentServer;
    }


}
