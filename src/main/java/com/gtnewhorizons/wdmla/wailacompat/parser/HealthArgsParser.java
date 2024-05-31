package com.gtnewhorizons.wdmla.wailacompat.parser;

import com.gtnewhorizons.wdmla.api.ITTRenderParser;
import com.gtnewhorizons.wdmla.impl.ui.component.Component;
import com.gtnewhorizons.wdmla.impl.ui.component.HealthComponent;

public class HealthArgsParser implements ITTRenderParser {
    @Override
    public Component parse(String[] args) {
        float maxHeartsPerLine = Float.parseFloat(args[0]); //for some reason, old api accepts float
        float health = Float.parseFloat(args[1]);
        float maxhealth = Float.parseFloat(args[2]);

        return new HealthComponent((int) maxHeartsPerLine, health, maxhealth);
    }
}
