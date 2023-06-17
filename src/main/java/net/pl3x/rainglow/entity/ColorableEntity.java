package net.pl3x.rainglow.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.pl3x.rainglow.util.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public abstract class ColorableEntity {
    private static final NamespacedKey PDC_COLOR = new NamespacedKey("rainglow", "colour");

    private final Entity bukkit;
    private final net.minecraft.world.entity.Entity entity;

    public ColorableEntity(Entity entity) {
        this.bukkit = entity;
        this.entity = ((CraftEntity) entity).getHandle();
    }

    public abstract Color getDefaultColor();

    protected abstract EntityDataAccessor<String> getKey();

    public abstract Color.Mode getColorMode(boolean dye);

    public void register() {
        if (!this.entity.getEntityData().hasItem(getKey())) {
            this.entity.getEntityData().registrationLocked = false;
            this.entity.getEntityData().define(getKey(), getDefaultColor().toString());
            this.entity.getEntityData().registrationLocked = true;
        }
        Color color = getColorMode(false).random();
        setColor(getPDCColor(color == null ? getDefaultColor() : color));
    }

    public Color getColor() {
        return Color.get(this.entity.getEntityData().get(getKey()));
    }

    public void setColor(Color color) {
        setColor(color.getName());
    }

    public void setColor(String color) {
        this.entity.getEntityData().set(getKey(), color);
        setPDCColor(color);
    }

    public void resetColor() {
        String color = getPDCColor();
        setColor("");
        setColor(color);
    }

    public boolean canColor(Player player) {
        return player.hasPermission("rainglow.entity.*");
    }

    private String getPDCColor() {
        return getPDCColor(getDefaultColor());
    }

    private String getPDCColor(Color def) {
        return this.bukkit.getPersistentDataContainer().getOrDefault(PDC_COLOR, PersistentDataType.STRING, def.getName());
    }

    private void setPDCColor(String color) {
        this.bukkit.getPersistentDataContainer().set(PDC_COLOR, PersistentDataType.STRING, color);
    }

    public static ColorableEntity get(Entity entity) {
        return switch (entity.getType()) {
            case ALLAY -> new ColorableAllay(entity);
            case GLOW_SQUID -> new ColorableGlowSquid(entity);
            case SLIME -> new ColorableSlime(entity);
            default -> null;
        };
    }
}
