package me.xorgon.simpletrails;

import de.slikey.effectlib.Effect;
import me.xorgon.simpletrails.objects.Trail;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class STListener implements Listener {

    SimpleTrails plugin = SimpleTrails.getInstance();
    STManager manager = plugin.getManager();

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getName().equals("Trails")) {
                Inventory inv = event.getClickedInventory();
                int slot = event.getSlot();
                String displayName = inv.getItem(slot).getItemMeta().getDisplayName();
                Player player = (Player) event.getWhoClicked();
                for (Trail trail : manager.getAvailableTrails().values()) {
                    if (displayName.equals(trail.getDisplayName())) {
                        if (manager.hasTrail(player, trail)) {
                            manager.stopTrail(player);
                            ItemStack item = inv.getItem(slot);
                            ItemMeta meta = item.getItemMeta();
                            meta.removeEnchant(Enchantment.THORNS);
                            item.setItemMeta(meta);
                            event.setCancelled(true);
                        } else {
                            boolean started = manager.startTrail(player, trail);
                            if (started) {
                                player.closeInventory();
                            }
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        manager.stopTrail(event.getPlayer());
    }
}
