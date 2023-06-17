package net.pl3x.rainglow.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class Dye {
    private final ItemStack item;
    private final Color color;

    private Dye(ItemStack item) {
        this.item = item;
        this.color = Color.get(item.getType());

        if (this.color == null) {
            throw new IllegalArgumentException("Item is not a valid dye color");
        }
    }

    public ItemStack getItem() {
        return this.item;
    }

    public Color getColor() {
        return this.color;
    }

    public static Dye get(Player player) {
        ItemStack item = null;
        PlayerInventory inv = player.getInventory();
        if (isDye(inv.getItemInMainHand())) {
            item = inv.getItemInMainHand();
        } else if (isDye(inv.getItemInOffHand())) {
            item = inv.getItemInOffHand();
        }
        if (item == null) {
            return null;
        }
        return new Dye(item);
    }

    public static boolean isDye(ItemStack item) {
        return item.getType().name().endsWith("_DYE");
    }
}
