package com.gtnewhorizons.wdmla.api.ui.style;

import com.gtnewhorizons.wdmla.api.ui.ComponentAlignment;

/**
 * collection of text component settings
 */
public interface ITextStyle {

    ComponentAlignment DEFAULT_ALIGN = ComponentAlignment.TOPLEFT;
    boolean DEFAULT_SHADOW = true;

    ComponentAlignment getAlignment();

    /**
     * @return the color of the text which can be overridden by {@link net.minecraft.util.EnumChatFormatting}
     */
    int getColor();

    /**
     * @return should the black parts of the text at right bottom be rendered or not
     */
    boolean getShadow();
}
