package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class StemHeaderProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if(accessor.getBlock().equals(Blocks.pumpkin_stem)) {
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, LangUtil.translateG("hud.item.pumpkinstem"));
        }
        else if(accessor.getBlock().equals(Blocks.melon_stem)) {
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, LangUtil.translateG("hud.item.melonstem"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.STEM_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.CORE_OVERRIDE;
    }
}
