package me.xorgon.simpletrails;

import de.slikey.effectlib.Effect;
import me.xorgon.simpletrails.effects.*;
import me.xorgon.simpletrails.objects.Trail;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class STManager {

    private final SimpleTrails plugin = SimpleTrails.getInstance();

    Map<String, Trail> availableTrails = new HashMap<>();

    private Map<Player, Effect> effects = new HashMap<>();

    public STManager() {
        availableTrails.put("blue", new Trail(plugin, SimpleTrailEffect.class, "blue", ChatColor.BLUE + "Blue Line", Color.BLUE, Material.PAPER));
        availableTrails.put("cloud", new Trail(plugin, CloudEffect.class, "cloud", ChatColor.WHITE + "Cloud", Color.RED, Material.PAPER));
        availableTrails.put("spinning", new Trail(plugin, BallEllipseEffect.class, "spinning", ChatColor.GREEN + "Spinning", Color.GREEN, Material.PAPER));
        availableTrails.put("rain", new Trail(plugin, RainEffect.class, "rain", ChatColor.GRAY + "Rain", Color.GRAY, Material.PAPER));
        availableTrails.put("red", new Trail(plugin, ColorableCloudEffect.class, "red", ChatColor.RED + "Red Cloud", Color.RED, Material.PAPER));
    }

    public boolean startTrail(Player player, Trail trail) {
        if (player.hasPermission("trail." + trail.getName()) || player.hasPermission("trail.*")) {
            if (hasTrail(player)) {
                stopTrail(player);
            }
            Effect trailEffect = trail.createEffect(plugin.getEffectManager(), player);
            trailEffect.start();
            effects.put(player, trailEffect);
            return true;
        } else {
            return false;
        }
    }

    public void stopTrail(Player player) {
        if (effects.containsKey(player)) {
            effects.get(player).cancel();
            effects.remove(player);
        }
    }

    public void stopAllTrails() {
        for (Player player : effects.keySet()) {
            stopTrail(player);
        }
    }

    public Trail getTrail(Player player) {
        if (hasTrail(player)) {
            for (Trail trail : availableTrails.values()) {
                if (hasTrail(player, trail)) {
                    return trail;
                }
            }
        }
        return null;
    }

    public boolean hasTrail(Player player) {
        return effects.containsKey(player);
    }

    public boolean hasTrail(Player player, Trail trail) {
        return effects.containsKey(player) && effects.get(player).getClass() == trail.getEffect();
    }

    public Map<String, Trail> getAvailableTrails() {
        return availableTrails;
    }


}