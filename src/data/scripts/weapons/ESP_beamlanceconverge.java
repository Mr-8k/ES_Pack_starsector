package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;
import org.lazywizard.lazylib.FastTrig;

public class ESP_beamlanceconverge implements EveryFrameWeaponEffectPlugin {
    //Note that the angles are in *either* direction, so a value of 20 creates a 40 degree cone in front of the weapon
    private static final float ANGLE_MAX   = 180f;
    private static final float ANGLE_MIN   = 0f;
    private static final float ANGLE_WAVE   = 0f;

    private static final float ROTATION_SPEED   = 0.0001f;

    private float counter = 0f;

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        //Don't run if we are paused, or our weapon is null
        if (engine.isPaused() || weapon == null) {
            return;
        }
        counter += amount * ROTATION_SPEED;

        //Sets some variables
        float angleMax = ANGLE_MAX;
        float angleMin = ANGLE_MIN;

        int startMod = 0;


        //We want a sine-shaped wave to affect our minimum angle, which loops every 0.95175 / 2 seconds
        float loopProgress = (counter / (0.95175f * ROTATION_SPEED)) * (float)Math.PI;
        angleMax += Math.abs(FastTrig.sin(loopProgress) * ANGLE_WAVE);
        angleMin += Math.abs(FastTrig.sin(loopProgress) * ANGLE_WAVE);

        //Handles hardpoint offsets
        for (int i = 0; i < weapon.getSpec().getHardpointAngleOffsets().size() - startMod; i++) {
            float angleToSet = (float)Math.sin(counter + (i * 2 * Math.PI / (weapon.getSpec().getHardpointAngleOffsets().size() - startMod)));
            angleToSet *= angleMin * weapon.getChargeLevel() + angleMax * (1 - weapon.getChargeLevel());
            weapon.getSpec().getHardpointAngleOffsets().set(i, angleToSet);
        }

        //Handles hidden offsets
        for (int i = 0; i < weapon.getSpec().getHiddenAngleOffsets().size() - startMod; i++) {
            float angleToSet = (float)Math.sin(counter + (i * 2 * Math.PI / (weapon.getSpec().getHiddenAngleOffsets().size() - startMod)));
            angleToSet *= angleMin * weapon.getChargeLevel() + angleMax * (1 - weapon.getChargeLevel());
            weapon.getSpec().getHiddenAngleOffsets().set(i, angleToSet);
        }

        //Handles turret offsets
        for (int i = 0; i < weapon.getSpec().getTurretAngleOffsets().size() - startMod; i++) {
            float angleToSet = (float)Math.sin(counter + (i * 2 * Math.PI / (weapon.getSpec().getTurretAngleOffsets().size() - startMod)));
            angleToSet *= angleMin * weapon.getChargeLevel() + angleMax * (1 - weapon.getChargeLevel());
            weapon.getSpec().getTurretAngleOffsets().set(i, angleToSet);
        }

    }

}
