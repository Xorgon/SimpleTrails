package me.xorgon.simpletrails.effects;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.util.RandomUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Elijah on 22/09/2016.
 */
public class RainEffect extends Effect {

    private int particles = 27;
    private double cloudRadius = 0.4;

    public RainEffect(EffectManager effectManager, Player player, Color color) {
        super(effectManager);
        type = EffectType.REPEATING;
        setEntity(player);
        period = 1;
        infinite();
        this.color = color;
    }

    @Override
    public void onRun() {
        Location center = getEntity().getLocation().add(0, 2.5, 0);

        for (int i = 0; i < particles; i++) {
            Vector vect = RandomUtils.getRandomVector().multiply(cloudRadius);
            vect.setY(vect.getY() * 0.5);
            Location newLoc = center.clone().add(vect);
            display(ParticleEffect.REDSTONE, newLoc, color);
            if (i % 4 == 0) {
                Location rainLoc = center.clone().add(vect.multiply(0.5));
                rainLoc.setY(center.getY() - 0.3);
                display(ParticleEffect.WATER_DROP, rainLoc);
            }
        }
    }

}
