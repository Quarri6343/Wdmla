package com.gtnewhorizons.wdmla.wailacompat.parser;

import com.gtnewhorizons.wdmla.api.ITTRenderParser;
import com.gtnewhorizons.wdmla.impl.ui.component.Component;
import com.gtnewhorizons.wdmla.impl.ui.component.TexturedProgressComponent;

public class ProgressArgsParser implements ITTRenderParser {

    @Override
    public Component parse(String[] args) {
        int current = Integer.parseInt(args[0]);
        int max = Integer.parseInt(args[1]);
        return new TexturedProgressComponent(current, max);
    }
}
