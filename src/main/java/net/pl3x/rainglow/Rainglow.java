package net.pl3x.rainglow;

import net.pl3x.rainglow.configuration.Config;
import net.pl3x.rainglow.listener.BukkitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rainglow extends JavaPlugin {
    @Override
    public void onEnable() {
        try {
            Class.forName("io.papermc.paper.configuration.PaperConfigurations");
        } catch (ClassNotFoundException e) {
            getLogger().severe("This plugin requires Paper to function.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Config.reload(this);

        getServer().getPluginManager().registerEvents(new BukkitListener(this), this);
    }
}
