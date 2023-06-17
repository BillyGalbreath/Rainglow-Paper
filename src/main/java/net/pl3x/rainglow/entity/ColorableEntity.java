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

    protected abstract Color getDefaultColor();

    protected abstract EntityDataAccessor<String> getKey();

    public void register() {
        if (!this.entity.getEntityData().hasItem(getKey())) {
            this.entity.getEntityData().registrationLocked = false;
            this.entity.getEntityData().define(getKey(), getDefaultColor().toString());
            this.entity.getEntityData().registrationLocked = true;
        }
        setColor(this.bukkit.getPersistentDataContainer().getOrDefault(PDC_COLOR, PersistentDataType.STRING, Color.random().getName()));
    }

    public void setColor(Color color) {
        setColor(color.getName());
    }

    public void setColor(String color) {
        this.entity.getEntityData().set(getKey(), color);
        this.bukkit.getPersistentDataContainer().set(PDC_COLOR, PersistentDataType.STRING, color);
    }

    public boolean canColor(Player player) {
        return player.hasPermission("rainglow.entity.*");
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
