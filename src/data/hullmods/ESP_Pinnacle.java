package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class ESP_Pinnacle extends BaseHullMod {

	public static final float VENT_RATE_BONUS = 15f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		stats.getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(id, 1000f);
		stats.getBreakProb().modifyMult(id, 0f);

		if (stats.getVariant() != null) {
			if (stats.getVariant().getHullMods().contains("unstable_injector")) {
				float RANGE_MULT = 1.15f;
				stats.getBallisticWeaponRangeBonus().modifyMult(id, RANGE_MULT);
				stats.getEnergyWeaponRangeBonus().modifyMult(id, RANGE_MULT);
			}
		}
		if (stats.getVariant() != null) {
			if (stats.getVariant().getHullMods().contains("fluxbreakers")) {
				stats.getVentRateMult().modifyPercent(id, VENT_RATE_BONUS);
			}
		}
	}

	@Override
	public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
		super.applyEffectsAfterShipCreation(ship, id);

		if (ship.getVariant().getHullMods().contains("safetyoverrides")) {
			ship.getVariant().removeMod("safetyoverrides");
		}
	}

	@Override
	public boolean isApplicableToShip(ShipAPI ship) {
		return !ship.getVariant().getHullMods().contains("safetyoverrides");
	}

	public String getUnapplicableReason(ShipAPI ship) {
		if (ship.getVariant().getHullMods().contains("safetyoverrides")) {
			return "Incompatible with Safety Overrides";
		}
		return null;
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) VENT_RATE_BONUS + "% when Resistant Flux Conduits are installed";
		if (index == 1) return "no range penalty when installing Unstable Injector";
		if (index == 2) return "Safety Overrides";
		return null;
	}
}