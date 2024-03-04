package net.atlaspvp.atlascore.Features.Essentials;

import net.atlaspvp.atlascore.Struct.Feature;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class Essentials extends Feature {
    private BukkitCommandHandler commandHandler;
    public boolean state;

    public void onEnable(Plugin plugin) {
        state = true;
        net.atlaspvp.atlascore.Struct.Configs.Essentials.Initialize(plugin);

        this.commandHandler = BukkitCommandHandler.create(plugin);
        commandHandler.register(new Commands());
    }

    public void onDisable() {

    }

    public boolean getState() {
        return state;
    }

}
