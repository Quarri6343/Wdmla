package com.gtnewhorizons.wdmla.api;

import net.minecraft.util.ResourceLocation;

public class Identifiers {

    public static final ResourceLocation ROOT = MC("root");

    public static final ResourceLocation ITEM_ICON = MC("item_icon");
    public static final ResourceLocation ITEM_NAME = MC("item_name");
    public static final ResourceLocation MOD_NAME = MC("mod_name");


    public static ResourceLocation WDMla(String path) {
        return new ResourceLocation("Wdmla", path);
    }

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
