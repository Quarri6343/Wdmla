package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.util.ResourceLocation;

public class VanillaIdentifiers {

    // provider Uid
    public static final ResourceLocation SILVERFISH_HEADER = MC("silverfish_header");
    public static final ResourceLocation REDSTONE_WIRE_HEADER = MC("redstone_wire_header");
    public static final ResourceLocation REDSTONE_WIRE = MC("redstone_wire");
    public static final ResourceLocation GROWABLE_HEADER = MC("growable_header");
    public static final ResourceLocation GROWTH_RATE = MC("growth_rate");
    public static final ResourceLocation REDSTONE_ORE_HEADER = MC("redstone_ore");
    public static final ResourceLocation DOUBLE_PLANT_HEADER = MC("double_plant_header");
    public static final ResourceLocation DROPPED_ITEM_HEADER = MC("dropped_item_header");
    public static final ResourceLocation CUSTOM_META_HEADER = MC("custom_meta_header");
    public static final ResourceLocation REDSTONE_STATE = MC("redstone_state");

    // config

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
