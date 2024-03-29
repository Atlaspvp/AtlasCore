package net.atlaspvp.atlascore.Struct.Configs;

import net.atlaspvp.atlascore.Utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

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

    public static Component getMessage(String message, Player player){
        return Chat.format(message, player);
    }

    public static boolean getBoolean(String a) {
        return config.getBoolean("messages." + a);
    }





}
