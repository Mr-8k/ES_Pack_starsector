package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.awt.*;

public class ESP_CCCDebug extends BaseHullMod {


    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSpacer(6.0F);
        tooltip.addSectionHeading("Effect", Alignment.MID, 0.0F);
        tooltip.addSpacer(6.0F);
        if (ship.getVariant().getHints().contains(ShipHullSpecAPI.ShipTypeHints.COMBAT)){
            tooltip.addPara("has %s", 0.0F, Color.GREEN, "COMBAT");
            tooltip.addSpacer(6.0F);
        }
        if (ship.getVariant().getHints().contains(ShipHullSpecAPI.ShipTypeHints.NO_AUTO_ESCORT)){
            tooltip.addPara("has %s", 0.0F, Color.GREEN, "NO_AUTO_ESCORT");
            tooltip.addSpacer(6.0F);
        }
    }

}
