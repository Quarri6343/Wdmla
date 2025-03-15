package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.util.ResourceLocation;

public class VanillaIdentifiers {

    // provider Uid
    public static final ResourceLocation SILVERFISH = MC("silverfish");
    public static final ResourceLocation REDSTONE_WIRE_HEADER = MC("redstone_wire_header");
    public static final ResourceLocation REDSTONE_WIRE = MC("redstone_wire");
    public static final ResourceLocation CROP_HEADER = MC("crop_header");
    public static final ResourceLocation STEM_HEADER = MC("stem_header");
    public static final ResourceLocation GROWTH_RATE_HEADER = MC("growth_rate");

    // config

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
