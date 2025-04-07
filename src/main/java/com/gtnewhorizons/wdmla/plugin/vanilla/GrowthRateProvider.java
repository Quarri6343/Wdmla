package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;

import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.util.StatCollector;

public enum GrowthRateProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        boolean iscrop = BlockCrops.class.isInstance(accessor.getBlock()); // Done to cover all inheriting mods
        if (iscrop || accessor.getBlock() == Blocks.melon_stem || accessor.getBlock() == Blocks.pumpkin_stem) {
            float growthValue = (accessor.getMetadata() / 7.0F) * 100.0F;
            appendGrowthRate(tooltip, growthValue);
        } else if (accessor.getBlock() == Blocks.cocoa) {
            float growthValue = ((accessor.getMetadata() >> 2) / 2.0F) * 100.0F;
            appendGrowthRate(tooltip, growthValue);
        } else if (accessor.getBlock() == Blocks.nether_wart) {
            float growthValue = (accessor.getMetadata() / 3.0F) * 100.0F;
            appendGrowthRate(tooltip, growthValue);
        }
    }

    private static void appendGrowthRate(ITooltip tooltip, float growthValue) {
        if (growthValue < 100.0) {
            tooltip.child(
                    ThemeHelper.INSTANCE
                            .value(StatCollector.translateToLocal("hud.msg.wdmla.growth"), String.format("%.0f %%", growthValue))); //TODO:percentage format
        } else {
            tooltip.child(
                    new HPanelComponent().text(String.format("%s: ", StatCollector.translateToLocal("hud.msg.wdmla.growth")))
                            .child(
                                    ThemeHelper.INSTANCE.success(
                                            String.format("%s", StatCollector.translateToLocal("hud.msg.wdmla.mature")))));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.GROWTH_RATE;
    }
}
