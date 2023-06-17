package net.pl3x.rainglow.configuration;

import net.pl3x.rainglow.util.Color;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Config extends AbstractConfig {
    @Key("settings.color-mode")
    @Comment("""
            Color mode to use.""")
    public static Color.Mode COLOR_MODE = Color.Mode.ALL_COLOURS;

    @Key("messages.color-not-allowed")
    public static String COLOR_NOT_ALLOWED = "<red>That color is not allowed!";

    private static final Config CONFIG = new Config();

    public static void reload(Plugin plugin) {
        CONFIG.reload(plugin.getDataFolder().toPath().resolve("config.yml"), Config.class);
    }

    @Override
    protected @Nullable Object get(@NotNull String path) {
        Object value = getConfig().get(path);
        if (value == null) {
            return null;
        }
        if ("settings.color-mode".equals(path)) {
            return Color.Mode.get(value.toString());
        }
        return super.get(path);
    }

    @Override
    protected void set(@NotNull String path, @Nullable Object value) {
        if (value instanceof Color.Mode mode) {
            value = mode.toString();
        }
        getConfig().set(path, value);
    }
}
