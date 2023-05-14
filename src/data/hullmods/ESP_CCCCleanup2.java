package data.hullmods;

import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import static com.fs.starfarer.api.combat.ShipHullSpecAPI.ShipTypeHints;

public class ESP_CCCCleanup2 extends BaseHullMod {

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        ShipVariantAPI variant = stats.getVariant();
        EnumSet<ShipTypeHints> hints = variant.getHints();
        Collection<ShipTypeHints> remove = new ArrayList<>();
        remove.add(ShipTypeHints.NO_AUTO_ESCORT);

        if (!variant.hasHullMod("ESP_CCC")) {
            //hints.remove(ShipTypeHints.NO_AUTO_ESCORT);
            hints.removeAll(remove);
            //variant.removeMod("ESP_CCCCleanup2");
        }
    }

/*    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        ShipVariantAPI variant = ship.getVariant();
        EnumSet<ShipTypeHints> hints = variant.getHints();
        Collection<ShipTypeHints> remove = new ArrayList<>();
        remove.add(ShipTypeHints.NO_AUTO_ESCORT);

        if (!variant.hasHullMod("ESP_CCC")) {
            //hints.remove(ShipTypeHints.NO_AUTO_ESCORT);
            hints.removeAll(remove);
           //variant.removeMod("ESP_CCCCleanup2");
        }
    }*/

    @Override
    public boolean canBeAddedOrRemovedNow(ShipAPI ship, MarketAPI marketOrNull, CampaignUIAPI.CoreUITradeMode mode) {
        return false;
    }
}
