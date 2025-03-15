package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.util.ResourceLocation;

public class VanillaIdentifiers {

    // provider Uid
    public static final ResourceLocation SILVERFISH = MC("silverfish");
    public static final ResourceLocation REDSTONE_WIRE = MC("redstone_wire");

    // config

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
