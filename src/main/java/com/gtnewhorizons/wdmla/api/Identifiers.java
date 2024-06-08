package com.gtnewhorizons.wdmla.api;

import net.minecraft.util.ResourceLocation;

public final class Identifiers {

    //component tag
    public static final ResourceLocation ROOT = MC("root");

    public static final ResourceLocation ITEM_ICON = MC("item_icon");
    public static final ResourceLocation ITEM_NAME = MC("item_name");
    public static final ResourceLocation ENTITY_NAME = MC("entity_name");
    public static final ResourceLocation MOD_NAME = MC("mod_name");
    public static final ResourceLocation HARVESTABILITY_ICON = WDMla("harvestability_icon");
    public static final ResourceLocation HARVESTABILITY_TEXT = WDMla("harvestability_text");


    //provider Uid
    public static final ResourceLocation DEFAULT = MC("default");

    public static final ResourceLocation TEST_HEAD = WDMla("test_head");
    public static final ResourceLocation TEST_BODY = WDMla("test_body");
    public static final ResourceLocation TEST_ENTITY = WDMla("test_entity");

    public static final ResourceLocation HARVESTABILITY = WDMla("harvestability");
    public static final ResourceLocation LEGACY_HARVESTABILITY = WDMla("legacy_harvestability");
    public static final ResourceLocation ENTITY_HEALTH = MC("entity_health");

    public static ResourceLocation WDMla(String path) {
        return new ResourceLocation("Wdmla", path);
    }

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
