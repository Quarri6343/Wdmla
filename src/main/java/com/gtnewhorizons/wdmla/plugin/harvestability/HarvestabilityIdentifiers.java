package com.gtnewhorizons.wdmla.plugin.harvestability;

import net.minecraft.util.ResourceLocation;

public class HarvestabilityIdentifiers {

    // component tag
    public static final ResourceLocation HARVESTABILITY_ICON = Harvestability("harvestability_icon");
    public static final ResourceLocation HARVESTABILITY_TEXT = Harvestability("harvestability_text");

    // provider Uid
    public static final ResourceLocation HARVESTABILITY = Harvestability("modern");
    public static final ResourceLocation LEGACY_HARVESTABILITY = Harvestability("legacy");

    public static final String NAMESPACE_HARVESTABILITY = "harvestability";

    public static ResourceLocation Harvestability(String path) {
        return new ResourceLocation(NAMESPACE_HARVESTABILITY, path);
    }
}
