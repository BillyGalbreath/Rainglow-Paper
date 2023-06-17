package net.pl3x.rainglow.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.animal.allay.Allay;
import net.pl3x.rainglow.configuration.Config;
import net.pl3x.rainglow.util.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class ColorableAllay extends ColorableEntity {
    private static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(Allay.class, EntityDataSerializers.STRING);

    public ColorableAllay(Entity bukkit) {
        super(bukkit);
    }

    @Override
    public Color getDefaultColor() {
        return Color.BLUE;
    }

    @Override
    protected EntityDataAccessor<String> getKey() {
        return ColorableAllay.COLOR;
    }

    @Override
    public Color.Mode getColorMode(boolean dye) {
        return dye ? Config.ALLAY_DYE_COLOR_MODE : Config.ALLAY_SPAWN_COLOR_MODE;
    }

    @Override
    public boolean canColor(Player player) {
        return super.canColor(player) || player.hasPermission("rainglow.entity.allay");
    }
}
