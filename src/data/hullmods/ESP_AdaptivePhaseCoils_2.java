package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class ESP_AdaptivePhaseCoils_2 extends BaseHullMod {

    private static final String ESP_id ="ESP_PHASE_FLAT_THRESHOLD";

    public static float FLUX_THRESHOLD_INCREASE_FLAT = 100000f;

/*    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
      stats.getDynamic().getMod(
                Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD).modifyFlat(id, FLUX_THRESHOLD_INCREASE_FLAT);
    }*/

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "removing the threshold at which speed bottoms out";
        if (index == 1) return "officer";
        if (index == 2) return "has no effect";
        return null;
    }


    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {

        if (!ship.getCaptain().isDefault()) {
                ship.getMutableStats().getDynamic().getMod(
                        Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD).modifyFlat(ESP_id, FLUX_THRESHOLD_INCREASE_FLAT);


            } else {
                ship.getMutableStats().getDynamic().getMod(
                        Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD).unmodify(ESP_id);

            }
        }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        if (ship.getVariant().hasHullMod(HullMods.PHASE_ANCHOR)) return false;
        if (ship.getVariant().hasHullMod(HullMods.ADAPTIVE_COILS)) return false;
        return ship.getHullSpec().isPhase();
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        if (ship.getVariant().hasHullMod(HullMods.PHASE_ANCHOR)) {
            return "Incompatible with Phase Anchor";
        }
        if (ship.getVariant().hasHullMod(HullMods.ADAPTIVE_COILS)) {
            return "Incompatible with Adaptive Phase Coils";
        }
        if (!ship.getHullSpec().isPhase()) {
            return "Can only be installed on phase ships";
        }
        return super.getUnapplicableReason(ship);
    }

}

