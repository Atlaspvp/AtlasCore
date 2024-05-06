package net.atlaspvp.atlascore.Features.PlayerVaults;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.atlaspvp.atlascore.AtlasCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.UUID;

public class GUI implements Listener {
    private Plugin plugin;
    private HashSet<UUID> playersInVaults = new HashSet<>();
    private HashSet<UUID> playersInSearch = new HashSet<>();

    public GUI() {
        this.plugin = AtlasCore.getInstance();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, "Player Vaults");

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        int slot = 0;
        for (int i = 1; i <= 27; i++) { // Assuming maximum 54 vaults for simplicity
            NamespacedKey key = PlayerVaults.getVaultKey(i);
            if (pdc.has(key, PersistentDataType.BYTE_ARRAY)) {
                ItemStack icon = createVaultIcon(i, false); // This method needs to be implemented to create an icon for the GUI
                gui.setItem(slot++, icon);
            }
        }
        gui.setItem(27, createSearchIcon());

        player.openInventory(gui);
    }

    public void openSearched(Player player, HashSet<Integer> vaults) {
        Inventory gui = Bukkit.createInventory(null, 54, "Player Vaults");

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        int slot = 0;
        for (int i = 1; i <= 27; i++) { // Assuming maximum 54 vaults for simplicity
            NamespacedKey key = PlayerVaults.getVaultKey(i);
            if (pdc.has(key, PersistentDataType.BYTE_ARRAY)) {
                ItemStack icon = createVaultIcon(i, vaults.contains(i)); // This method needs to be implemented to create an icon for the GUI
                gui.setItem(slot++, icon);
            }
        }
        gui.setItem(27, createSearchIcon());

        player.openInventory(gui);
    }

    private ItemStack createSearchIcon() {
        ItemStack icon = new ItemStack(Material.SPYGLASS);
        ItemMeta meta = icon.getItemMeta();

        meta.setDisplayName("Search");
        icon.setItemMeta(meta);
        return icon;
    }

    private ItemStack createVaultIcon(int vaultNumber, boolean SearchResult) {
        ItemStack icon = new ItemStack(Material.DIAMOND);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Vault " + vaultNumber);

        if (SearchResult) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        icon.setItemMeta(meta);
        return icon;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Player Vaults")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                String title = event.getCurrentItem().getItemMeta().getDisplayName();
                if (title.startsWith("Vault")) {
                    int vaultNumber = Integer.parseInt(title.split(" ")[1]);
                    Player player = (Player) event.getWhoClicked();
                    playersInVaults.add(player.getUniqueId()); // Track that this player opened a vault from the GUI
                    PlayerVaults.open(player, vaultNumber);
                } else if (title.equals("Search")){
                    Player player = (Player) event.getWhoClicked();
                    playersInSearch.add(player.getUniqueId());
                    player.sendMessage("Type into chat what you are looking for.");
                    event.getView().close();
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String title = event.getView().getTitle();
        if (title.startsWith("Vault") && playersInVaults.contains(player.getUniqueId()) && !playersInSearch.contains(player.getUniqueId())) {
            playersInVaults.remove(player.getUniqueId()); // Remove from tracking once they close the vault
            Bukkit.getScheduler().runTaskLater(plugin, () -> openGUI(player), 1L); // Re-open the GUI
        }
    }

    @EventHandler
    public void onChatForSearch(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (playersInSearch.contains(player.getUniqueId())) {
            System.out.println("Message is " + event.getMessage());
            openSearched(player, PlayerVaults.searchTOC(player, event.getMessage()));
            playersInSearch.remove(player.getUniqueId());
            event.setCancelled(true);
        }
    }
}
