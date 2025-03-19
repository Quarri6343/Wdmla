package com.gtnewhorizons.wdmla.addon.vanilla;

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

public class VanillaPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(new SilverFishBlockHeaderProvider(), BlockSilverfish.class);
        registration.registerBlockComponent(new RedstoneWireHeaderProvider(), BlockRedstoneWire.class);
        registration.registerBlockComponent(new RedstoneWireProvider(), BlockRedstoneWire.class);
        registration.registerBlockComponent(new GrowableHeaderProvider(), BlockCrops.class);
        registration.registerBlockComponent(new GrowableHeaderProvider(), BlockStem.class);
        registration.registerBlockComponent(new GrowthRateProvider(), BlockCrops.class);
        registration.registerBlockComponent(new GrowthRateProvider(), BlockStem.class);
        registration.registerBlockComponent(new GrowthRateProvider(), BlockCocoa.class);
        registration.registerBlockComponent(new GrowthRateProvider(), BlockNetherWart.class);
        registration.registerBlockComponent(new RedstoneOreHeaderProvider(), BlockRedstoneOre.class);
        registration.registerBlockComponent(new DoublePlantHeaderProvider(), BlockDoublePlant.class);
        registration.registerBlockComponent(new DroppedItemHeaderProvider(), BlockAnvil.class);
        registration.registerBlockComponent(new DroppedItemHeaderProvider(), BlockSapling.class);
        registration.registerBlockComponent(new DroppedItemHeaderProvider(), BlockStoneSlab.class);
        registration.registerBlockComponent(new DroppedItemHeaderProvider(), BlockWoodSlab.class);
        registration.registerBlockComponent(new CustomMetaDataHeaderProvider(), BlockLeaves.class);
        registration.registerBlockComponent(new CustomMetaDataHeaderProvider(), BlockLog.class);
        registration.registerBlockComponent(new CustomMetaDataHeaderProvider(), BlockQuartz.class);
        registration.registerBlockComponent(new RedstoneStateProvider(), BlockLever.class);
        registration.registerBlockComponent(new RedstoneStateProvider(), BlockRedstoneRepeater.class);
        registration.registerBlockComponent(new RedstoneStateProvider(), BlockRedstoneComparator.class);
        registration.registerBlockComponent(new MobSpawnerHeaderProvider(), BlockMobSpawner.class);
        registration.registerBlockComponent(new FurnaceProvider(), BlockFurnace.class);
        registration.registerBlockComponent(new PlayerHeadHeaderProvider(), BlockSkull.class);
        registration.registerBlockComponent(new BeaconProvider(), BlockBeacon.class);
        registration.registerBlockComponent(new BedProvider(), BlockBed.class);
    }

    @Override
    public void register(IWDMlaCommonRegistration registration) {
        registration.registerBlockDataProvider(new FurnaceProvider(), BlockFurnace.class);
        registration.registerBlockDataProvider(new BeaconProvider(), BlockBeacon.class);
    }

    public static class RedstoneWireHeaderProvider implements IBlockComponentProvider {

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

    public static class RedstoneWireProvider implements IBlockComponentProvider {

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

    public static class RedstoneOreHeaderProvider implements IBlockComponentProvider {

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
