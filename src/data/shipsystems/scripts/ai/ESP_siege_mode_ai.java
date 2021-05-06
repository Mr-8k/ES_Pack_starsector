package data.shipsystems.scripts.ai;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAIScript;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.ShipwideAIFlags;
import com.fs.starfarer.api.combat.WeaponAPI;
import java.util.List;

import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

public class ESP_siege_mode_ai implements ShipSystemAIScript {

    private ShipSystemAPI system;
    private ShipAPI ship;
    private float rangeWeapon = 0;

    @Override
    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, com.fs.starfarer.api.combat.CombatEngineAPI engine) {
        this.ship = ship;
        this.system = system;

        int count=0;

        List<WeaponAPI> weapons = ship.getAllWeapons();
        if (weapons != null) {
            for (WeaponAPI weapon : weapons) {
                if (weapon.getId().equals("ESP_focusingbeamlance")) {
                    rangeWeapon+=weapon.getRange();
                    count++;
                }
            }
        }

        rangeWeapon /= count;
        rangeWeapon = (rangeWeapon*1.5f) - 200f;

    }

    @Override
    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {
        ShipwideAIFlags flags = ship.getAIFlags();
        boolean usesystem = false;

        if (target != null) {
            float distance = MathUtils.getDistance(ship, target);
            if (distance < rangeWeapon) {
                usesystem = true;
            }
        }
        if (target == null || flags.hasFlag(ShipwideAIFlags.AIFlags.BACKING_OFF) || MathUtils.getDistance(ship, target) > rangeWeapon + 200f) {
            usesystem = false;
        }
        if (usesystem) {
            activateSystem();
        } else {
            deactivateSystem();
        }
    }

    private void deactivateSystem() {
        if (system.isOn()) {
            ship.useSystem();
        }
    }

    private void activateSystem() {
        if (!system.isOn()) {
            ship.useSystem();
        }
    }
}
