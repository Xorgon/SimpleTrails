package me.xorgon.simpletrails.effects;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.entity.Player;

public class SimpleTrailEffect extends Effect {

    private Color color;

    public SimpleTrailEffect(EffectManager effectManager, Player player, Color color) {
        super(effectManager);
        type = EffectType.REPEATING;
        setEntity(player);
        period = 1;
        infinite();
        this.color = color;
    }

    @Override
    public void onRun() {
        display(ParticleEffect.REDSTONE, ((Player) getEntity()).getLocation().add(0, 0.5, 0), color);
    }
}
