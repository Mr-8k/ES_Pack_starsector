package data.scripts.weapons;

import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.BeamAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.combat.BeamEffectPlugin;
import com.fs.starfarer.api.Global;
import org.lwjgl.util.vector.Vector2f;

public class ESP_beamlancedamage implements BeamEffectPlugin {

    private final IntervalUtil fireInterval;

    public ESP_beamlancedamage() {
        fireInterval = new IntervalUtil(0.5f, 0.5f);
    }
    public void advance(float amount, final CombatEngineAPI engine, final BeamAPI beam) {
        final CombatEntityAPI target = beam.getDamageTarget();
        final WeaponAPI weapon = beam.getWeapon();
        final ShipAPI ship = beam.getSource();
        final float base_damage = (weapon.getDamage().getDamage() * (0.5f));
        Vector2f point1 = new Vector2f(weapon.getLocation());
        final float initwidth = 25f;

        if (engine.isPaused()) {
            return;
        }
        if (!weapon.isFiring()) {
            fireInterval.setElapsed(0f);
            return;
        }
        if(weapon.getChargeLevel()<0.99f){
            fireInterval.setElapsed(0f);
            beam.setWidth(initwidth);
            return;
        }
        if (weapon.getChargeLevel() >= 0.99f && weapon.getCooldownRemaining() <= 0f) {
            Global.getSoundPlayer().playLoop("focusingbeamlance_loop", ship , 0.8f, 2f, point1, weapon.getShip().getVelocity());
        }

            fireInterval.advance(amount);
              if (fireInterval.intervalElapsed()) {
                  beam.setWidth(initwidth * 2);
                  if (Math.random()>0.9f) {

                      engine.applyDamage(
                              target,
                              beam.getTo(),
                              base_damage,
                              DamageType.ENERGY,
                              0.0f,
                              false,
                              false,
                              ship);
                  }
              }
        if(weapon.getChargeLevel()<0.99f){
            beam.setWidth(initwidth);
            return; //this return is needed
        }
        }
    }
