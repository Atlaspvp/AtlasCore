package net.atlaspvp.atlascore.Features.PlayerVaults;

import com.google.common.collect.Maps;
import net.atlaspvp.atlascore.AtlasCore;
import net.atlaspvp.atlascore.Struct.Feature;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;


public class PlayerVaults extends Feature implements Listener {
    public static HashMap<Player, HashMap<String, Integer>> TOC; // Relocate into PlayerData for persistence
    public boolean state;

    public void onEnable(Plugin plugin) {
        state = true;
        TOC = Maps.newHashMap();
    }

    public void onDisable() {

    }

    public boolean getState() {
        return state;
    }

    public static void open(Player player, int target) {
        PersistentDataContainer pd = player.getPersistentDataContainer();
        NamespacedKey key = getVaultKey(target);
        for (NamespacedKey test : player.getPersistentDataContainer().getKeys()) {
            System.out.println(test);
        }

        if (pd.has(key, PersistentDataType.BYTE_ARRAY)) {
            ItemStack[] i = DSerial(pd.get(key, PersistentDataType.BYTE_ARRAY));
            if (i != null) {
                Inventory Vault = Bukkit.createInventory(null, 54, "Vault " + target);
                Vault.setContents(i);
                player.openInventory(Vault);
            }
        } else {
            player.openInventory(Bukkit.createInventory(null, 54, "Vault " + target));
        }
    }

    @EventHandler
    public void ClosePV(InventoryCloseEvent e) {
        String title = e.getView().getTitle();
        if (title.startsWith("Vault")) {
            try {
                System.out.println("Saving " + title + " for " + e.getPlayer().getName());
                int target = Integer.parseInt(title.replaceAll("[^0-9]", "")); // Extract only numeric part
                save((Player) e.getPlayer(), title, e.getInventory().getContents());
                addToTOC((Player) e.getPlayer(), target, e.getInventory().getContents());
            } catch (NumberFormatException ex) {
                System.out.println("Error parsing vault number from title: " + title);
            }
        } else {
            System.out.println("Not a Vault");
        }
    }

    private static void save(Player player, String vault, ItemStack[] i) {
        String[] parts = vault.split("\\s+");
        String vaultInt = parts[1];
        PersistentDataContainer pd = player.getPersistentDataContainer();
        pd.set(getVaultKey(Integer.parseInt(vaultInt)),PersistentDataType.BYTE_ARRAY ,Serial(i));
        System.out.println(getVaultKey(Integer.parseInt(vaultInt)));
    }

    public static byte[] Serial(ItemStack[] i) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new BukkitObjectOutputStream(bos)) {
            out.writeObject(i);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static ItemStack[] DSerial(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new BukkitObjectInputStream(bis)) {
            return (ItemStack[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NamespacedKey getVaultKey(int vault) {
        return new NamespacedKey(AtlasCore.getInstance(), "vault" + vault);
    }

    public static HashSet<Integer> searchTOC(Player player, String itemName) {
        HashMap<String, Integer> playerTOC = TOC.get(player);
        HashSet<Integer> foundVaults = new HashSet<>();
        if (playerTOC != null) {
            for (Map.Entry<String, Integer> entry : playerTOC.entrySet()) {
                if (entry.getKey().contains(itemName)) {
                    int vaultNumber = entry.getValue();
                    player.sendMessage("Item '" + entry + "' found in Vault " + vaultNumber);
                    foundVaults.add(vaultNumber);
                }
            }
            player.sendMessage("Item '" + itemName + "' not found in any vault.");
        } else {
            player.sendMessage("You don't have a Table of Contents (TOC) yet.");
        }

        return foundVaults;
    }

    private static void addToTOC(Player player, int vaultNumber, ItemStack[] contents) {
        HashMap<String, Integer> playerTOC = TOC.computeIfAbsent(player, k -> new HashMap<>());

        for (ItemStack item : contents) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String itemName = item.getItemMeta().getDisplayName();
                playerTOC.put(itemName, vaultNumber);
            } else {
                if (item != null) {
                    playerTOC.put(item.getType().name(), vaultNumber);
                }
            }
        }
    }
}
