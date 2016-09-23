package me.xorgon.simpletrails.objects;

import me.xorgon.simpletrails.STManager;
import me.xorgon.simpletrails.SimpleTrails;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TrailMenu {

    private Player player;
    private STManager manager;

    public TrailMenu(Player player) {
        this.player = player;
        manager = SimpleTrails.getInstance().getManager();
    }

    public void showMenu() {
        Inventory menu = Bukkit.createInventory(player, InventoryType.HOPPER, "Trails");
        for (Trail trail : SimpleTrails.getInstance().getManager().getAvailableTrails().values()) {
            ItemStack item = new ItemStack(trail.getMaterial());
            ItemMeta meta = item.getItemMeta();
            if (manager.getTrail(player) == trail) {
                meta.addEnchant(Enchantment.THORNS, 0, false);
            }
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "-----------------");
            if (player.hasPermission("trail." + trail.getName()) || player.hasPermission("trail.*")) {
                lore.add(ChatColor.GREEN + "Click to use!");
            } else {
                lore.add(ChatColor.RED + "You don't have permission.");
            }
            meta.setLore(lore);
            meta.setDisplayName(trail.getDisplayName());
            item.setItemMeta(meta);
            menu.addItem(item);
        }
        player.openInventory(menu);
    }

}
