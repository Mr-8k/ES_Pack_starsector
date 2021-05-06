package data.shipsystems.scripts;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;


public class ESP_siege_mode extends BaseShipSystemScript {

    public static final float RANGE_BONUS = 50f;
    public static final float FLUX_DISCOUNT = -33f;
    public static final float SHIELD_BONUS = 50f;


    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        stats.getMaxSpeed().modifyMult(id, 1f - 0.5f * effectLevel);
        stats.getAcceleration().modifyMult(id, 1f - 0.5f * effectLevel);
        stats.getDeceleration().modifyMult(id, 1f - 0.5f * effectLevel);
        stats.getTurnAcceleration().modifyMult(id, 1f - 0.5f * effectLevel);
        stats.getMaxTurnRate().modifyMult(id, 1f - 0.5f * effectLevel);
        stats.getEnergyWeaponRangeBonus().modifyPercent(id, RANGE_BONUS * effectLevel);
        stats.getBallisticWeaponRangeBonus().modifyPercent(id, RANGE_BONUS * effectLevel);
        stats.getBallisticWeaponFluxCostMod().modifyPercent(id, FLUX_DISCOUNT * effectLevel);
        stats.getEnergyWeaponFluxCostMod().modifyPercent(id, FLUX_DISCOUNT * effectLevel);
        stats.getShieldTurnRateMult().modifyPercent(id, SHIELD_BONUS);
        stats.getShieldUnfoldRateMult().modifyPercent(id, SHIELD_BONUS);


    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {

        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getEnergyWeaponRangeBonus().unmodify(id);
        stats.getBallisticWeaponRangeBonus().unmodify(id);
        stats.getBallisticWeaponFluxCostMod().unmodify(id);
        stats.getEnergyWeaponFluxCostMod().unmodify(id);
        stats.getShieldTurnRateMult().unmodify(id);
        stats.getShieldUnfoldRateMult().unmodify(id);
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        if (index == 0) {
            return new StatusData( "Max speed and maneuverability decreased by 50%", true);
        }
        if (index == 1) {
            return new StatusData("Weapon range increased by " + RANGE_BONUS + "%", false);
        }
        if (index == 2) {
            return new StatusData("Weapon flux generation reduced by " + FLUX_DISCOUNT + "%", false);
        }
        if (index == 3) {
            return new StatusData("Shield unfold and turn rate increased by " + SHIELD_BONUS + "%", false);
        }
        return null;
    }

}
