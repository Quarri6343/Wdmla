package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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
            return TooltipPosition.CORE_OVERRIDE;
        }
    }

    public static class RedstoneWireProvider implements IBlockComponentProvider {

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            tooltip.child(
                    new HPanelComponent()
                            .text(String.format("%s: ", LangUtil.translateG("hud.msg.power")))
                            .child(ThemeHelper.INSTANCE.info(String.format("%s", accessor.getMetadata())))
            );
        }

        @Override
        public ResourceLocation getUid() {
            return VanillaIdentifiers.REDSTONE_WIRE;
        }
    }

    public static class RedstoneOreHeaderProvider implements IBlockComponentProvider {

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            //override lit redstone
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, new ItemStack(Blocks.redstone_ore));
        }

        @Override
        public ResourceLocation getUid() {
            return VanillaIdentifiers.REDSTONE_ORE_HEADER;
        }

        @Override
        public int getDefaultPriority() {
            return TooltipPosition.CORE_OVERRIDE;
        }
    }

    public static class DroppedItemHeaderProvider implements IBlockComponentProvider {

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            ItemStack newStack = new ItemStack(accessor.getBlock(), 1, accessor.getBlock().damageDropped(accessor.getMetadata()));
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, newStack);
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, newStack);
        }

        @Override
        public ResourceLocation getUid() {
            return VanillaIdentifiers.DROPPED_ITEM_HEADER;
        }

        @Override
        public int getDefaultPriority() {
            return TooltipPosition.CORE_OVERRIDE;
        }
    }
}
