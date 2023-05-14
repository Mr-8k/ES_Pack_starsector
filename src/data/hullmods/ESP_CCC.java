package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.awt.*;
import java.util.EnumSet;

import static com.fs.starfarer.api.combat.ShipHullSpecAPI.ShipTypeHints;

public class ESP_CCC extends BaseHullMod {

    boolean isCarrier = false;
    boolean gotCOMBAT = false;
    boolean gotNO_AUTO_ESCORT = false;

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        ShipVariantAPI variant = stats.getVariant();
        EnumSet<ShipTypeHints> hints = variant.getHints();

        if (hints.contains(ShipTypeHints.CARRIER)) {
            isCarrier = true;

            if (!hints.contains(ShipTypeHints.COMBAT)) {
                hints.add(ShipTypeHints.COMBAT);
                gotCOMBAT = true;
                variant.addMod("ESP_CCCCleanup");
            }

            if (!hints.contains(ShipTypeHints.NO_AUTO_ESCORT)) {
                hints.add(ShipTypeHints.NO_AUTO_ESCORT);
                gotNO_AUTO_ESCORT = true;
                variant.addMod("ESP_CCCCleanup2");
            }

        }
    }

    /*    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        Global.getSector().getPlayerFleet();

        FleetMemberAPI member = ship.getFleetMember();
        ShipVariantAPI variant = member.getVariant();
        if (variant.getSource() != VariantSource.REFIT) {
            member.setVariant(member.getVariant().clone(), false, false);
            member.getVariant().setSource(VariantSource.REFIT);
        }
        ShipVariantAPI clone = ship.getVariant();
        EnumSet<ShipTypeHints> hints = clone.getHints();

        if (hints.contains(ShipTypeHints.CARRIER)) {
            isCarrier = true;

            if (!hints.contains(ShipTypeHints.COMBAT)) {
                hints.add(ShipTypeHints.COMBAT);
                gotCOMBAT = true;
            }

            if (!hints.contains(ShipTypeHints.NO_AUTO_ESCORT)) {
                hints.add(ShipTypeHints.NO_AUTO_ESCORT);
                gotNO_AUTO_ESCORT = true;
            }
        }

        ship.getFleetMember().setVariant(clone, false,true);
    }*/

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSpacer(6.0F);
        tooltip.addSectionHeading("Effect", Alignment.MID, 0.0F);
        tooltip.addSpacer(6.0F);
        if (ship == null)
            return;
        if (!isCarrier){
            tooltip.addPara("%s", 0.0F, Color.YELLOW, "No effect");
            return;
        }
        if (gotCOMBAT){
            tooltip.addPara("Gained %s hint", 0.0F, Color.GREEN, "COMBAT");
            tooltip.addSpacer(6.0F);
        }
        if (gotNO_AUTO_ESCORT) {
            tooltip.addPara("Gained %s hint", 0.0F, Color.GREEN, "NO_AUTO_ESCORT");
            tooltip.addSpacer(6.0F);
        }
        if (!gotCOMBAT && !gotNO_AUTO_ESCORT){
            tooltip.addPara("No effect", 0.0F);
            tooltip.addSpacer(6.0F);
        }
        if (gotNO_AUTO_ESCORT || gotCOMBAT) {
            tooltip.addPara("%s", 0.0F, Color.RED, "THIS APPLIES TO ALL HULLS OF THIS TYPE");
            //tooltip.addSpacer(6.0F);
        }
    }

}
