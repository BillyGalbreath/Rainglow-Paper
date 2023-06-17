package net.pl3x.rainglow.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import net.pl3x.rainglow.configuration.Config;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum Color {
    BLACK(Material.BLACK_DYE),
    BLUE(Material.BLUE_DYE),
    BROWN(Material.BROWN_DYE),
    CYAN(Material.CYAN_DYE),
    GREEN(Material.GREEN_DYE),
    GRAY(Material.GRAY_DYE),
    INDIGO,
    LIGHT_BLUE(Material.LIGHT_BLUE_DYE),
    LIGHT_GRAY(Material.LIGHT_GRAY_DYE),
    LIME(Material.LIME_DYE),
    MAGENTA(Material.MAGENTA_DYE),
    ORANGE(Material.ORANGE_DYE),
    PINK(Material.PINK_DYE),
    PURPLE(Material.PURPLE_DYE),
    RED(Material.RED_DYE),
    WHITE(Material.WHITE_DYE),
    YELLOW(Material.YELLOW_DYE);

    private final Material mat;
    private final String name;

    Color() {
        this.mat = null;
        this.name = name().toLowerCase(Locale.ROOT);
    }

    Color(Material mat) {
        this.mat = mat;
        String name = mat.name().toLowerCase(Locale.ROOT);
        this.name = name.substring(0, name.length() - 4);
    }

    public String getName() {
        return this.name;
    }

    public boolean canUse(Player player) {
        return Config.COLOR_MODE.colors.contains(this) &&
                (player.hasPermission("rainglow.color.*") ||
                        player.hasPermission("rainglow.color." + getName()));
    }

    private static final Map<Material, Color> BY_MAT = new HashMap<>();

    static {
        for (Color color : values()) {
            if (color.mat != null) {
                BY_MAT.put(color.mat, color);
            }
        }
    }

    public static Color get(Material mat) {
        return BY_MAT.get(mat);
    }

    public enum Mode {
        ALL_COLOURS(BLACK, BLUE, BROWN, CYAN, GRAY, GREEN, INDIGO, LIGHT_BLUE, LIGHT_GRAY, LIME, MAGENTA, ORANGE, PINK, PURPLE, RED, WHITE, YELLOW),
        ARO_PRIDE(BLACK, GRAY, WHITE, GREEN),
        ACE_PRIDE(BLACK, GRAY, WHITE, PURPLE),
        BI_PRIDE(BLUE, PINK, PURPLE),
        GAY_PRIDE(BLUE, CYAN, GREEN, WHITE),
        GENDERFLUID_PRIDE(PURPLE, WHITE, BLACK, PINK, BLUE),
        LESBIAN_PRIDE(RED, ORANGE, WHITE, PINK, PURPLE),
        MONOCHROME(BLACK, GRAY, WHITE),
        ENBY_PRIDE(YELLOW, WHITE, BLACK, PURPLE),
        PAN_PRIDE(PINK, YELLOW, BLUE),
        RAINBOW(RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, PURPLE),
        TRANS_PRIDE(BLUE, WHITE, PINK),
        VANILLA(BLUE);

        private static final Map<String, Mode> BY_NAME = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(mode -> BY_NAME.put(mode.name(), mode));
        }

        private final List<Color> colors = new ArrayList<>();

        Mode(Color... colors) {
            this.colors.addAll(Arrays.stream(colors).toList());
        }

        public static Mode get(String string) {
            Mode mode = BY_NAME.get(string.toUpperCase(Locale.ROOT));
            return mode == null ? RAINBOW : mode;
        }

        public Color random() {
            return this.colors.get(ThreadLocalRandom.current().nextInt(this.colors.size()));
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
