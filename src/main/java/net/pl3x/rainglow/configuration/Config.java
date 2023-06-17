package net.pl3x.rainglow.configuration;

import java.util.Arrays;
import net.pl3x.rainglow.util.Color;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Config extends AbstractConfig {
    @Key("settings.color-mode.allay.spawn")
    @Comment("""
            Color mode sets which colors are allowed to be used when an allay
            spawns in the world naturally, from spawn egg, or from command.""")
    public static Color.Mode ALLAY_SPAWN_COLOR_MODE = Color.Mode.ALL_COLOURS;
    @Key("settings.color-mode.allay.dye")
    @Comment("""
            Color mode sets which colors are allowed to be used when a player
            uses dye item on an allay.""")
    public static Color.Mode ALLAY_DYE_COLOR_MODE = Color.Mode.ALL_COLOURS;
    @Key("settings.color-mode.glow_squid.spawn")
    @Comment("""
            Color mode sets which colors are allowed to be used when a glow
            squid spawns in the world naturally, from spawn egg, or from command.""")
    public static Color.Mode GLOW_SQUID_SPAWN_COLOR_MODE = Color.Mode.ALL_COLOURS;
    @Key("settings.color-mode.glow_squid.dye")
    @Comment("""
            Color mode sets which colors are allowed to be used when a player
            uses dye item on an glow squid.""")
    public static Color.Mode GLOW_SQUID_DYE_COLOR_MODE = Color.Mode.ALL_COLOURS;
    @Key("settings.color-mode.slime.spawn")
    @Comment("""
            Color mode sets which colors are allowed to be used when a glow
            squid spawns in the world naturally, from spawn egg, or from command.""")
    public static Color.Mode SLIME_SPAWN_COLOR_MODE = Color.Mode.ALL_COLOURS;
    @Key("settings.color-mode.slime.dye")
    @Comment("""
            Color mode sets which colors are allowed to be used when a player
            uses dye item on an glow squid.""")
    public static Color.Mode SLIME_DYE_COLOR_MODE = Color.Mode.ALL_COLOURS;

    @Key("messages.color-not-allowed")
    @Comment("""
            The message to display to users trying to use dye on a mob that
            isn't allowed by the current color mode or color permissions.
            (set to "" to disable the message)""")
    public static String COLOR_NOT_ALLOWED = "<red>That color is not allowed!";
    @Key("messages.mob-not-allowed")
    @Comment("""
            The message to display to users trying to use dye on a mob that
            isn't allowed by the entity permissions.
            (set to "" to disable the message)""")
    public static String ENTITY_NOT_ALLOWED = "<red>You cannot color this mob!";

    private static final Config CONFIG = new Config();

    public static void reload(Plugin plugin) {
        CONFIG.reload(plugin.getDataFolder().toPath().resolve("config.yml"), Config.class);
        CONFIG.getConfig().setComments("settings.color-mode", Arrays.stream("""
                Modes are: all_colours, aro_pride, ace_pride, bi_pride, gay_pride,
                genderfluid_pride, lesbian_pride, monochrome, enby_pride, pan_pride,
                rainbow, trans_pride, and vanilla.""".split("\n")).toList());
        CONFIG.save();
    }

    @Override
    protected @Nullable Object get(@NotNull String path) {
        Object value = getConfig().get(path);
        if (value == null) {
            return null;
        }
        if (path.startsWith("settings.color-mode.")) {
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
