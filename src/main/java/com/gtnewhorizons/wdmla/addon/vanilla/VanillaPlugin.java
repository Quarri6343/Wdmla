package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.ColorCodes;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class VanillaPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(new SilverFishBlockProvider(), BlockSilverfish.class);
        registration.registerBlockComponent(new RedstoneWireHeaderProvider(), BlockRedstoneWire.class);
        registration.registerBlockComponent(new RedstoneWireProvider(), BlockRedstoneWire.class);
        registration.registerBlockComponent(new CropHeaderProvider(), BlockCrops.class);
        registration.registerBlockComponent(new StemHeaderProvider(), BlockStem.class);
        registration.registerBlockComponent(new GeneralGrowthRateProvider(), BlockCrops.class);
        registration.registerBlockComponent(new GeneralGrowthRateProvider(), BlockStem.class);
    }

    public static class RedstoneWireHeaderProvider implements IBlockComponentProvider {

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            ItemStack redstone = new ItemStack(Items.redstone);
            tooltip.replaceChildWithTag(Identifiers.ITEM_ICON, new ItemComponent(redstone).tag(Identifiers.ITEM_ICON));
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
                            .text(String.format("%s", accessor.getMetadata()), new TextStyle().color(ColorCodes.INFO), new Padding())
            );
        }

        @Override
        public ResourceLocation getUid() {
            return VanillaIdentifiers.REDSTONE_WIRE;
        }
    }
}
