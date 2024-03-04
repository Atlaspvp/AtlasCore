package net.atlaspvp.atlascore.Struct;

import net.atlaspvp.atlascore.AtlasCore;
import org.bukkit.plugin.Plugin;

public abstract class Feature {

    public abstract void onEnable(Plugin plugin);

    public abstract void onDisable();

    public abstract boolean getState();
}
