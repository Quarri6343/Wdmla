package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.ColorCodes;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class StemHeaderProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if(accessor.getBlock().equals(Blocks.pumpkin_stem)) {
            IComponent replacedName = new HPanelComponent()
                    .text(LangUtil.translateG("hud.item.pumpkinstem"), new TextStyle().color(ColorCodes.INFO), new Padding())
                    .tag(Identifiers.ITEM_NAME);
            tooltip.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName);
        }
        else if(accessor.getBlock().equals(Blocks.melon_stem)) {
            IComponent replacedName = new HPanelComponent()
                    .text(LangUtil.translateG("hud.item.melonstem"), new TextStyle().color(ColorCodes.INFO), new Padding())
                    .tag(Identifiers.ITEM_NAME);
            tooltip.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName);
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
