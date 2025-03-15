package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.util.ResourceLocation;

public class VanillaIdentifiers {

    // provider Uid
    public static final ResourceLocation SILVERFISH = MC("silverfish");

    // config

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
