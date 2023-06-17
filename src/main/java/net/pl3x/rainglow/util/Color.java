package net.pl3x.rainglow.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum Color {
    BLACK(Material.BLACK_DYE),
    BLUE(Material.BLUE_DYE),
    BROWN(Material.BROWN_DYE),
    CYAN(Material.CYAN_DYE),
    GREEN(Material.GREEN_DYE),
    GRAY(Material.GRAY_DYE),
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

    Color(Material mat) {
        this.mat = mat;
        String name = mat.name().toLowerCase(Locale.ROOT);
        this.name = name.substring(0, name.length() - 4);
    }

    public Material getMaterial() {
        return this.mat;
    }

    public String getName() {
        return this.name;
    }

    public boolean canUse(Player player) {
        return player.hasPermission("rainglow.color.*") ||
                player.hasPermission("rainglow.color." + getName());
    }

    private static final Map<Material, Color> BY_MAT = new HashMap<>();

    static {
        for (Color color : values()) {
            BY_MAT.put(color.getMaterial(), color);
        }
    }

    public static Color get(Material mat) {
        return BY_MAT.get(mat);
    }

    public static Color random() {
        return values()[ThreadLocalRandom.current().nextInt(16)];
    }
}
