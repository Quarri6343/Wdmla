package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.impl.ui.DefaultThemes;
import net.minecraft.util.ResourceLocation;

public final class Identifiers {

    // component tag
    public static final ResourceLocation ROOT = Core("root");

    public static final ResourceLocation ITEM_ICON = Core("item_icon");
    public static final ResourceLocation ITEM_NAME_ROW = Core("item_name_row");
    public static final ResourceLocation ITEM_NAME = Core("item_name");
    public static final ResourceLocation ENTITY_NAME = Core("entity_name");
    public static final ResourceLocation MOD_NAME = Core("mod_name");

    // provider Uid
    public static final ResourceLocation DEFAULT_BLOCK = Core("default_block");
    public static final ResourceLocation DEFAULT_ENTITY = Core("default_entity");
    public static final ResourceLocation ENTITY_HEALTH = Core("entity_health");
    public static final ResourceLocation HARDNESS = Core("hardness");

    public static final ResourceLocation TEST_HEAD = Test("head");
    public static final ResourceLocation TEST_NBT_BLOCK = Test("nbt_block");
    public static final ResourceLocation TEST_ENTITY = Test("entity");
    public static final ResourceLocation TEST_THEME_BLOCK = Test("theme_block");

    // theme
    public static final ResourceLocation DEFAULT_THEME = Core("Waila");
    public static final ResourceLocation THEME_JADE = Core("Jade");

    // config
    public static final String CONFIG_GENERAL = "wdmla_general";
    public static final ConfigEntry<Boolean> CONFIG_FORCE_LEGACY = new ConfigEntry<>(
            CONFIG_GENERAL,
            "LegacyMode",
            false,
            "Disables all modern WDMla features. This will make the system ignore all settings in this category");
    public static final ConfigEntry<Boolean> CONFIG_GHOST_PRODUCT = new ConfigEntry<>(
            CONFIG_GENERAL,
            "GhostProduct",
            true,
            "Shows the ghost product on process");
    public static final ConfigEntry<DefaultThemes> CONFIG_CURRENT_THEME = new ConfigEntry<>(
            CONFIG_GENERAL,
            "Theme",
            DefaultThemes.WAILA,
            "Current Tooltips Theme");

    public static final String CONFIG_PROVIDER = "plugin_providers";
    public static final String CONFIG_PROVIDER_ENABLED = "Enabled";
    public static final String CONFIG_PROVIDER_PRIORITY = "Priority";

    public static final String CONFIG_PLUGINS = "plugin_settings";

    public static final String CONFIG_Plugins_CORE = CONFIG_PLUGINS + ".core";
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ICON = new ConfigEntry<>(
            CONFIG_Plugins_CORE,
            "ShowIcon",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_BLOCK_NAME = new ConfigEntry<>(
            CONFIG_Plugins_CORE,
            "ShowBlockName",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_MOD_NAME = new ConfigEntry<>(
            CONFIG_Plugins_CORE,
            "ShowModName",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ENTITY_NAME = new ConfigEntry<>(
            CONFIG_Plugins_CORE,
            "ShowEntityName",
            true,
            "");
    public static final ConfigEntry<Integer> CONFIG_MAX_ENTITY_HEALTH_FOR_TEXT = new ConfigEntry<>(
            CONFIG_Plugins_CORE,
            "MaxEntityHealthForText",
            40,
            "If the maximum health of an entity is above this value, texts will be shown instead of heart icons");

    public static ResourceLocation Core(String path) {
        return new ResourceLocation("core", path);
    }

    public static ResourceLocation Test(String path) {
        return new ResourceLocation("test", path);
    }
}
