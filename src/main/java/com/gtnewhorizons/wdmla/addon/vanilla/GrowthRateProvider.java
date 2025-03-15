package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.ColorCodes;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class GrowthRateProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        boolean iscrop = BlockCrops.class.isInstance(accessor.getBlock()); // Done to cover all inheriting mods
        if (iscrop || accessor.getBlock() == Blocks.melon_stem || accessor.getBlock() == Blocks.pumpkin_stem) {
            float growthValue = (accessor.getMetadata() / 7.0F) * 100.0F;
            appendGrowthRate(tooltip, growthValue);
        }
        else if(accessor.getBlock() == Blocks.cocoa) {
            float growthValue = ((accessor.getMetadata() >> 2) / 2.0F) * 100.0F;
            appendGrowthRate(tooltip, growthValue);
        }
        else if(accessor.getBlock() == Blocks.nether_wart) {
            float growthValue = (accessor.getMetadata() / 3.0F) * 100.0F;
            appendGrowthRate(tooltip, growthValue);
        }
    }

    private static void appendGrowthRate(ITooltip tooltip, float growthValue) {
        if (growthValue < 100.0) {
            tooltip.child(
                    new HPanelComponent()
                            .text(String.format("%s: ", LangUtil.translateG("hud.msg.growth")))
                            .text(String.format("%.0f %%", growthValue), new TextStyle().color(ColorCodes.INFO), new Padding())
            );
        }
        else {
            tooltip.child(
                    new HPanelComponent()
                            .text(String.format("%s: ", LangUtil.translateG("hud.msg.growth")))
                            .text(String.format("%s", LangUtil.translateG("hud.msg.mature")), new TextStyle().color(ColorCodes.SUCCESS), new Padding())
            );
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.GROWTH_RATE_HEADER;
    }
}
