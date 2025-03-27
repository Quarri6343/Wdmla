package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.addon.core.DefaultBlockInfoProvider;
import com.gtnewhorizons.wdmla.addon.core.DefaultEntityInfoProvider;
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
    public static final ResourceLocation BLAST_RESISTANCE = Core("blast_resistance");
    public static final ResourceLocation EQUIPMENT = Core("equipment");
    public static final ResourceLocation ARMOR = Core("armor");

    public static final ResourceLocation TEST_HEAD = Test("head");
    public static final ResourceLocation TEST_NBT_BLOCK = Test("nbt_block");
    public static final ResourceLocation TEST_ENTITY = Test("entity");
    public static final ResourceLocation TEST_THEME_BLOCK = Test("theme_block");

    // config
    public static final String CONFIG_PROVIDER = "wdmla_providers";
    public static final String CONFIG_PROVIDER_LANGKEY = "option.provider.category";
    public static final String CONFIG_PROVIDER_ENABLED = "option.provider.enabled";
    public static final String CONFIG_PROVIDER_PRIORITY = "option.provider.priority";

    public static final String CONFIG_CORE_CATEGORY = CONFIG_PROVIDER + ".core";
    public static final String CONFIG_CORE_LANGKEY = "option.core.category";

    public static final ConfigEntry<Boolean> CONFIG_SHOW_ICON = new ConfigEntry<>(
            DefaultBlockInfoProvider.INSTANCE,
            "option.core.show.icon",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_BLOCK_NAME = new ConfigEntry<>(
            DefaultBlockInfoProvider.INSTANCE,
            "option.core.show.blockname",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_MOD_NAME = new ConfigEntry<>(
            DefaultBlockInfoProvider.INSTANCE,
            "option.core.show.modname",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ENTITY_NAME = new ConfigEntry<>(
            DefaultEntityInfoProvider.INSTANCE,
            "option.core.show.entityname",
            true,
            "");
    public static final ConfigEntry<Integer> CONFIG_MAX_ENTITY_HEALTH_FOR_TEXT = new ConfigEntry<>(
            DefaultEntityInfoProvider.INSTANCE,
            "option.core.maxentityhealth",
            40,
            "If the maximum health of an entity is above this value, texts will be shown instead of heart icons");

    public static ResourceLocation Core(String path) {
        return new ResourceLocation("core", path);
    }

    public static ResourceLocation Test(String path) {
        return new ResourceLocation("test", path);
    }
}
