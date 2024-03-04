package net.atlaspvp.atlascore.Struct.Configs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
        ConfigValues.printcontents = getBoolean("print-contents");
    }

    public static void Reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static Component getMessage(String message, Player player){
        String a = config.getString("messages." + message);
        Component b = MiniMessage.miniMessage().deserialize(a);

        if (player != null) {
            Component c = b.replaceText(TextReplacementConfig.builder().match("<player>").replacement(player.getName()).build());
            return c;
        }
        return b;
    }

    public static boolean getBoolean(String a) {
        return config.getBoolean("messages." + a);
    }





}
