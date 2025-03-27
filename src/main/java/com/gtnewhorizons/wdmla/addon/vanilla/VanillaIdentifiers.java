package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.ConfigEntry;
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
    public static final ResourceLocation MOB_SPAWNER_HEADER = MC("mob_spawner_header");
    public static final ResourceLocation FURNACE = MC("furnace");
    public static final ResourceLocation PLAYER_HEAD_HEADER = MC("player_head_header");
    public static final ResourceLocation BEACON = MC("beacon");
    public static final ResourceLocation BED = MC("bed");
    public static final ResourceLocation PET = MC("pet");
    public static final ResourceLocation ANIMAL = MC("animal");

    // config
    public static final String CONFIG_MINECRAFT = "minecraft";
    public static final String CONFIG_MINECRAFT_LANGKEY = "option.vanilla.category";
    public static final ConfigEntry<Boolean> CONFIG_SHOW_PET_SIT = new ConfigEntry<>(
            PetProvider.INSTANCE,
            "option.vanilla.show.petsitting",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_PET_OWNER = new ConfigEntry<>(
            PetProvider.INSTANCE,
            "option.vanilla.show.petowner",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ANIMAL_GROWTH = new ConfigEntry<>(
            AnimalProvider.INSTANCE,
            "option.vanilla.show.animalgrowth",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHOW_ANIMAL_BREED_COOLDOWN = new ConfigEntry<>(
            AnimalProvider.INSTANCE,
            "option.vanilla.show.breedcooldown",
            true,
            "");

    private static ResourceLocation MC(String path) {
        return new ResourceLocation(path);
    }
}
