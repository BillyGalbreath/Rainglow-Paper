package net.pl3x.rainglow;

import net.pl3x.rainglow.listener.BukkitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rainglow extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BukkitListener(this), this);
    }
}
