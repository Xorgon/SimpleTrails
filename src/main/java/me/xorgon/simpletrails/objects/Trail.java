package me.xorgon.simpletrails.objects;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import me.xorgon.simpletrails.SimpleTrails;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Elijah on 22/09/2016.
 */
public class Trail {

    private SimpleTrails plugin;
    private Class effect;
    private String name;
    private String displayName;
    private Color color;
    private Material material;

    public Trail(SimpleTrails plugin, Class effect, String name, String displayName, Color color, Material material) {
        this.plugin = plugin;
        this.effect = effect;
        this.name = name;
        this.displayName = displayName;
        this.color = color;
        this.material = material;
    }

    public Class getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public Effect createEffect(EffectManager effectManager, Player player){
        Effect activeEffect = null;
        try {
            activeEffect = (Effect) effect.getConstructor(EffectManager.class, Player.class, Color.class)
                                            .newInstance(plugin.getEffectManager(), player, this.color);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return activeEffect;
    }
}
