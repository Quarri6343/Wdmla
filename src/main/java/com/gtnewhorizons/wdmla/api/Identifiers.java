package com.gtnewhorizons.wdmla.api;

import net.minecraft.util.ResourceLocation;

public final class Identifiers {

    // component tag
    public static final ResourceLocation ROOT = MC("root");

    public static final ResourceLocation ITEM_ICON = MC("item_icon");
    public static final ResourceLocation ITEM_NAME = MC("item_name");
    public static final ResourceLocation ENTITY_NAME = MC("entity_name");
    public static final ResourceLocation MOD_NAME = MC("mod_name");
    public static final ResourceLocation HARVESTABILITY_ICON = WDMla("harvestability_icon");
    public static final ResourceLocation HARVESTABILITY_TEXT = WDMla("harvestability_text");

    // provider Uid
    public static final ResourceLocation DEFAULT = MC("default");

    public static final ResourceLocation TEST_HEAD = WDMla("test_head");
    public static final ResourceLocation TEST_BODY = WDMla("test_body");
    public static final ResourceLocation TEST_ENTITY = WDMla("test_entity");

    public static final ResourceLocation HARVESTABILITY = WDMla("harvestability");
    public static final ResourceLocation LEGACY_HARVESTABILITY = WDMla("legacy_harvestability");
    public static final ResourceLocation ENTITY_HEALTH = MC("entity_health");

    // config
    public static final String CONFIG_GENERAL = "wdmla_general";
    public static final ConfigEntry<Boolean> CONFIG_FORCE_LEGACY = new ConfigEntry<>(
            CONFIG_GENERAL,
            "LegacyMode",
            false,
            "Disables all modern WDMla features. This will make the system ignore all settings in this category");

    public static final String CONFIG_GENERAL_CORE = "wdmla_general.core_plugin";
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ICON = new ConfigEntry<>(
            CONFIG_GENERAL_CORE,
            "ShowIcon",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_BLOCK_NAME = new ConfigEntry<>(
            CONFIG_GENERAL_CORE,
            "ShowBlockName",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_MOD_NAME = new ConfigEntry<>(
            CONFIG_GENERAL_CORE,
            "ShowModName",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ENTITY_NAME = new ConfigEntry<>(
            CONFIG_GENERAL_CORE,
            "ShowEntityName",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ENTITY_HEALTH = new ConfigEntry<>(
            CONFIG_GENERAL_CORE,
            "ShowEntityHealth",
            true,
            "");
    public static final ConfigEntry<Integer> CONFIG_MAX_ENTITY_HEALTH_FOR_TEXT = new ConfigEntry<>(
            CONFIG_GENERAL_CORE,
            "MaxEntityHealthForText",
            40,
            "If the maximum health of an entity is above this value, texts will be shown instead of heart icons");

    public static ResourceLocation WDMla(String path) {
        return new ResourceLocation("wdmla", path);
    }

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
