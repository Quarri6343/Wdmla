package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.WDMla;
import net.minecraft.util.ResourceLocation;

public final class Identifiers {

    public static final ResourceLocation ROOT = MC("root");

    public static final ResourceLocation ITEM_ICON = MC("item_icon");
    public static final ResourceLocation ITEM_NAME = MC("item_name");
    public static final ResourceLocation MOD_NAME = MC("mod_name");

    public static final ResourceLocation TEST_HEAD = WDMla("test_head");
    public static final ResourceLocation TEST_BODY = WDMla("test_body");

    public static final ResourceLocation HARVESTABILITY = WDMla("harvestability");


    public static ResourceLocation WDMla(String path) {
        return new ResourceLocation("Wdmla", path);
    }

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
