package com.gtnewhorizons.wdmla.addon.harvestability.helpers;

import net.minecraft.util.EnumChatFormatting;

public class ColorHelper {

    private static final EnumChatFormatting booleanColorRange[] = { EnumChatFormatting.DARK_RED, EnumChatFormatting.RED,
            EnumChatFormatting.DARK_GREEN, EnumChatFormatting.GREEN };

    public static String getBooleanColor(boolean val) {
        return getBooleanColor(val, false);
    }

    public static String getBooleanColor(boolean val, boolean modified) {
        return booleanColorRange[(val ? 2 : 0) + (modified ? 1 : 0)].toString();
    }
}
