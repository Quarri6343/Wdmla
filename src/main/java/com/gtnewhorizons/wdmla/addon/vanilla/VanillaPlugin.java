package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;

import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraftforge.common.config.Configuration;

public class VanillaPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(SilverFishBlockHeaderProvider.INSTANCE, BlockSilverfish.class);
        registration.registerBlockComponent(RedstoneWireHeaderProvider.INSTANCE, BlockRedstoneWire.class);
        registration.registerBlockComponent(RedstoneWireProvider.INSTANCE, BlockRedstoneWire.class);
        registration.registerBlockComponent(GrowableHeaderProvider.INSTANCE, BlockCrops.class);
        registration.registerBlockComponent(GrowableHeaderProvider.INSTANCE, BlockStem.class);
        registration.registerBlockComponent(GrowthRateProvider.INSTANCE, BlockCrops.class);
        registration.registerBlockComponent(GrowthRateProvider.INSTANCE, BlockStem.class);
        registration.registerBlockComponent(GrowthRateProvider.INSTANCE, BlockCocoa.class);
        registration.registerBlockComponent(GrowthRateProvider.INSTANCE, BlockNetherWart.class);
        registration.registerBlockComponent(RedstoneOreHeaderProvider.INSTANCE, BlockRedstoneOre.class);
        registration.registerBlockComponent(DoublePlantHeaderProvider.INSTANCE, BlockDoublePlant.class);
        registration.registerBlockComponent(DroppedItemHeaderProvider.INSTANCE, BlockAnvil.class);
        registration.registerBlockComponent(DroppedItemHeaderProvider.INSTANCE, BlockSapling.class);
        registration.registerBlockComponent(DroppedItemHeaderProvider.INSTANCE, BlockStoneSlab.class);
        registration.registerBlockComponent(DroppedItemHeaderProvider.INSTANCE, BlockWoodSlab.class);
        registration.registerBlockComponent(CustomMetaDataHeaderProvider.INSTANCE, BlockLeaves.class);
        registration.registerBlockComponent(CustomMetaDataHeaderProvider.INSTANCE, BlockLog.class);
        registration.registerBlockComponent(CustomMetaDataHeaderProvider.INSTANCE, BlockQuartz.class);
        registration.registerBlockComponent(RedstoneStateProvider.INSTANCE, BlockLever.class);
        registration.registerBlockComponent(RedstoneStateProvider.INSTANCE, BlockRedstoneRepeater.class);
        registration.registerBlockComponent(RedstoneStateProvider.INSTANCE, BlockRedstoneComparator.class);
        registration.registerBlockComponent(MobSpawnerHeaderProvider.INSTANCE, BlockMobSpawner.class);
        registration.registerBlockComponent(FurnaceProvider.INSTANCE, BlockFurnace.class);
        registration.registerBlockComponent(PlayerHeadHeaderProvider.INSTANCE, BlockSkull.class);
        registration.registerBlockComponent(BeaconProvider.INSTANCE, BlockBeacon.class);
        registration.registerBlockComponent(BedProvider.INSTANCE, BlockBed.class);
        registration.registerEntityComponent(PetProvider.INSTANCE, EntityTameable.class);
        registration.registerEntityComponent(AnimalProvider.INSTANCE, EntityAnimal.class);

        WDMlaConfig.instance().getCategory(Identifiers.CONFIG_AUTOGEN + Configuration.CATEGORY_SPLITTER + VanillaIdentifiers.NAMESPACE_MINECRAFT)
                .setLanguageKey("option.vanilla.category");
    }

    @Override
    public void register(IWDMlaCommonRegistration registration) {
        registration.registerBlockDataProvider(FurnaceProvider.INSTANCE, BlockFurnace.class);
        registration.registerBlockDataProvider(BeaconProvider.INSTANCE, BlockBeacon.class);
        registration.registerEntityDataProvider(PetProvider.INSTANCE, EntityTameable.class);
    }

    public enum RedstoneWireHeaderProvider implements IBlockComponentProvider {
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, new ItemStack(Items.redstone));
        }

        @Override
        public ResourceLocation getUid() {
            return VanillaIdentifiers.REDSTONE_WIRE_HEADER;
        }

        @Override
        public int getDefaultPriority() {
            return TooltipPosition.HEAD;
        }
    }

    public enum RedstoneWireProvider implements IBlockComponentProvider {
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            tooltip.child(
                    new HPanelComponent().text(String.format("%s: ", LangUtil.translateG("hud.msg.power")))
                            .child(ThemeHelper.INSTANCE.info(String.format("%s", accessor.getMetadata()))));
        }

        @Override
        public ResourceLocation getUid() {
            return VanillaIdentifiers.REDSTONE_WIRE;
        }
    }

    public enum RedstoneOreHeaderProvider implements IBlockComponentProvider {
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            // override lit redstone
            ItemStack redstoneOre = new ItemStack(Blocks.redstone_ore);
            ThemeHelper.INSTANCE.overrideTooltipHeader(tooltip, redstoneOre);
        }

        @Override
        public ResourceLocation getUid() {
            return VanillaIdentifiers.REDSTONE_ORE_HEADER;
        }

        @Override
        public int getDefaultPriority() {
            return TooltipPosition.HEAD;
        }
    }
}
