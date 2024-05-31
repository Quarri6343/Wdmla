package com.gtnewhorizons.wdmla.wailacompat.parser;

import com.gtnewhorizons.wdmla.api.ITTRenderParser;
import com.gtnewhorizons.wdmla.impl.ui.component.Component;
import com.gtnewhorizons.wdmla.impl.ui.component.VanillaIconComponent;
import com.gtnewhorizons.wdmla.overlay.VanillaIconUI;

public class IconArgsParser implements ITTRenderParser {

    @Override
    public Component parse(String[] args) {
        VanillaIconUI iconUI = switch (args[0]) {
            case "a" -> VanillaIconUI.HEART;
            case "b" -> VanillaIconUI.HHEART;
            case "c" -> VanillaIconUI.EHEART;
            default -> VanillaIconUI.BUBBLEEXP;
        };
        // intentional hardcode to bypass bad enum implementation
        return new VanillaIconComponent(iconUI);
    }
}
