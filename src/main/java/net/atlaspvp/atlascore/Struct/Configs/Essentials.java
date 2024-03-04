package net.atlaspvp.atlascore.Struct.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Optional;

public class Essentials {

    private static org.bukkit.plugin.Plugin Plugin;
    private static File file;
    private static FileConfiguration config;

    public static void Initialize(Plugin plugin) {
        Plugin = plugin;
        file = new File(plugin.getDataFolder(), "Essentials.yml");

        if (!file.exists()) {
            plugin.saveResource("Essentials.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void Reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static String getMessage(String message, Player player){
        String a = config.getString("messages." + message);

        if (player != null) {
            return a.replace("{player}", player.getName());
        }
        return a;
    }





}
