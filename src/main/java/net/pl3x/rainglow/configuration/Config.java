package net.pl3x.rainglow.configuration;

import net.pl3x.rainglow.util.Color;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Config extends AbstractConfig {
    @Key("settings.color-mode")
    @Comment("""
            Color mode sets which colors are allowed to be used.
            Modes are: all_colours, aro_pride, ace_pride, bi_pride, gay_pride,
            genderfluid_pride, lesbian_pride, monochrome, enby_pride, pan_pride,
            rainbow, trans_pride, and vanilla.""")
    public static Color.Mode COLOR_MODE = Color.Mode.ALL_COLOURS;

    @Key("messages.color-not-allowed")
    @Comment("""
            The message to display to users trying to use dye on a mob that
            isn't allowed by the current color mode. (set to "" to disable)""")
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
