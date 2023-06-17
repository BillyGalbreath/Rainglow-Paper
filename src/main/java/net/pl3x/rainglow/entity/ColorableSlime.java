package net.pl3x.rainglow.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.Slime;
import net.pl3x.rainglow.util.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class ColorableSlime extends ColorableEntity {
    private static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(Slime.class, EntityDataSerializers.STRING);

    public ColorableSlime(Entity bukkit) {
        super(bukkit);
    }

    @Override
    protected Color getDefaultColor() {
        return Color.LIME;
    }

    @Override
    protected EntityDataAccessor<String> getKey() {
        return ColorableSlime.COLOR;
    }

    @Override
    public boolean canColor(Player player) {
        return super.canColor(player) || player.hasPermission("rainglow.entity.slime");
    }
}
