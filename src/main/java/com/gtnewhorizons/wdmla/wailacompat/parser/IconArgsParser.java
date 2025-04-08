package com.gtnewhorizons.wdmla.wailacompat.parser;

import com.gtnewhorizons.wdmla.api.ITTRenderParser;
import com.gtnewhorizons.wdmla.impl.ui.component.Component;
import com.gtnewhorizons.wdmla.impl.ui.component.IconComponent;
import com.gtnewhorizons.wdmla.overlay.VanillaUIIcons;
import net.minecraft.client.gui.Gui;

public class IconArgsParser implements ITTRenderParser {

    @Override
    public Component parse(String[] args) {
        VanillaUIIcons iconUI = switch (args[0]) {
            case "a" -> VanillaUIIcons.HEART;
            case "b" -> VanillaUIIcons.HHEART;
            case "c" -> VanillaUIIcons.EHEART;
            default -> VanillaUIIcons.BUBBLEEXP;
        };
        // intentional hardcode to bypass bad enum implementation
        return new IconComponent(iconUI, Gui.icons);
    }
}
