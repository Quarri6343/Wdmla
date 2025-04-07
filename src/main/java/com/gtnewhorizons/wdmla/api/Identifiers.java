package com.gtnewhorizons.wdmla.api;

import net.minecraft.util.ResourceLocation;

public final class Identifiers {

    // component tag
    public static final ResourceLocation ROOT = Core("root");

    public static final ResourceLocation ITEM_ICON = Core("item_icon");
    public static final ResourceLocation ITEM_NAME_ROW = Core("item_name_row");
    public static final ResourceLocation ITEM_NAME = Core("item_name");
    public static final ResourceLocation ENTITY = Core("entity");
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
    public static final ResourceLocation ENCHANTMENT_POWER = Core("enchantment_power");
    public static final ResourceLocation STATUS_EFFECT = Core("status_effect");

    public static final ResourceLocation ITEM_STORAGE = Universal("item_storage");
    public static final ResourceLocation ITEM_STORAGE_DEFAULT = Universal("item_storage_default");

    public static final ResourceLocation TEST_HEAD = Test("head");
    public static final ResourceLocation TEST_NBT_BLOCK = Test("nbt_block");
    public static final ResourceLocation TEST_ENTITY = Test("entity");
    public static final ResourceLocation TEST_THEME_BLOCK = Test("theme_block");
    public static final ResourceLocation TEST_ITEM_STORAGE = Test("item_storage");

    public static final String NAMESPACE_CORE = "core";
    public static final String NAMESPACE_UNIVERSAL = "universal";
    public static final String NAMESPACE_TEST = "test";

    public static final String CONFIG_AUTOGEN = "plugins_autogen";

    public static ResourceLocation Core(String path) {
        return new ResourceLocation(NAMESPACE_CORE, path);
    }

    public static ResourceLocation Universal(String path) {
        return new ResourceLocation(NAMESPACE_UNIVERSAL, path);
    }

    public static ResourceLocation Test(String path) {
        return new ResourceLocation(NAMESPACE_TEST, path);
    }
}
