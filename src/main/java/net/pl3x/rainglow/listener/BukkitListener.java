package net.pl3x.rainglow.listener;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pl3x.rainglow.Rainglow;
import net.pl3x.rainglow.configuration.Config;
import net.pl3x.rainglow.entity.ColorableEntity;
import net.pl3x.rainglow.util.Dye;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public final class BukkitListener implements Listener {
    private final Rainglow plugin;

    public BukkitListener(Rainglow plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return; // only listen to this event once
        }

        ColorableEntity entity = ColorableEntity.get(event.getRightClicked());
        if (entity == null) {
            return; // not a valid rainglow colorable entity
        }

        Player player = event.getPlayer();
        if (!entity.canColor(player)) {
            entity.resetColor();
            return; // player not allowed to color this entity
        }

        Dye dye = Dye.get(player);
        if (dye == null) {
            entity.resetColor();
            return; // player not holding valid dye item
        }

        if (!dye.getColor().canUse(player)) {
            entity.resetColor();
            if (Config.COLOR_NOT_ALLOWED != null && !Config.COLOR_NOT_ALLOWED.isBlank()) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(Config.COLOR_NOT_ALLOWED));
            }
            return; // player not allowed to use this color
        }

        // finally set the entity's rainglow color
        entity.setColor(dye.getColor());

        if (player.getGameMode() != GameMode.CREATIVE) {
            // only "use" one item when not in creative mode
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                // we must decrement _after_ coloring because of client prediction
                dye.getItem().subtract();
            }, 0);
        }
    }

    @EventHandler
    public void onEntitySpawn(EntityAddToWorldEvent event) {
        ColorableEntity entity = ColorableEntity.get(event.getEntity());
        if (entity != null) {
            entity.register();
        }
    }
}
