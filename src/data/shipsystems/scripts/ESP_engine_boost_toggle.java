package data.shipsystems.scripts;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShieldAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

import java.util.List;

public class ESP_engine_boost_toggle extends BaseShipSystemScript {

    public static final float SPEED_BONUS = 70f;
    public static final float MANEUVER_BONUS = 50f;
    private final float  orig = 360f;
    private final float locked_arc = 90f;
    private float arc = 1f;


    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        ShipAPI ship = (ShipAPI) stats.getEntity();

        stats.getMaxSpeed().modifyFlat(id, SPEED_BONUS);
        stats.getAcceleration().modifyPercent(id, MANEUVER_BONUS * 2f);
        stats.getDeceleration().modifyPercent(id, MANEUVER_BONUS);
        stats.getTurnAcceleration().modifyPercent(id, MANEUVER_BONUS * 2f);
        stats.getMaxTurnRate().modifyPercent(id, MANEUVER_BONUS);
        stats.getFluxDissipation().modifyMult(id, 1f- 0.33f * effectLevel);



        ShieldAPI shield = ship.getShield();
        arc = shield.getActiveArc();
        if (arc >= locked_arc) arc = locked_arc;
        shield.setArc(effectLevel * (locked_arc - orig) + orig);
        shield.setActiveArc(arc); //prevent shield from resetting

        List<WeaponAPI> weapons = ship.getAllWeapons();
        if (weapons != null) {
            for (WeaponAPI weapon : weapons) {
                if (weapon.getId().equals("ESP_Kilopulse")) {
                    weapon.setRemainingCooldownTo(5000f);
                }
            }
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        ShipAPI ship = (ShipAPI) stats.getEntity();

        List<WeaponAPI> weapons = ship.getAllWeapons();
        if (weapons != null) {
            for (WeaponAPI weapon : weapons) {
                if (weapon.getId().equals("ESP_Kilopulse")) {
                    weapon.setRemainingCooldownTo(0f);
                }
            }
        }



        ShieldAPI shield = ship.getShield();
        arc = shield.getActiveArc();
        shield.setArc(orig);
        shield.setActiveArc(arc);

        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getFluxDissipation().unmodify(id);
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        if (index == 0) {
            return new StatusData( "Max speed increased by " + (int) SPEED_BONUS, false);
        }
        if (index == 1) {
            return new StatusData("Maneuverability increased", false);
        }
        if (index == 2) {
            return new StatusData("Built-in weapons disabled", true);
        }
        if (index == 3) {
            return new StatusData("Shield arc locked to " + (int) locked_arc + " degrees", true);
        }
        if (index == 4) {
            return new StatusData("Flux dissipation reduced by 1/3", true);
        }
        return null;
    }

}
