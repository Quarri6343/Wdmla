package com.gtnewhorizons.wdmla.addon;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.util.ResourceLocation;

import static mcp.mobius.waila.api.SpecialChars.GREEN;
import static mcp.mobius.waila.api.SpecialChars.WHITE;

public class HarvestToolProvider implements IComponentProvider<BlockAccessor> {

    @Override
    public ResourceLocation getUid() {
        return Identifiers.HARVESTABILITY;
    }

    @Override
    public int getDefaultPriority() {
        return -8000;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        //TODO: move harvestability addon from Waila to here
        IComponent replacedName = new TextComponent(WHITE + DisplayUtil.itemDisplayNameShort(accessor.getItemForm()) + GREEN + "✔").tag(Identifiers.ITEM_NAME);
        if(!tooltip.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName)) {
            throw new RuntimeException("WDMla Harvestability Module couldn't find item name in the tooltip");
        }
    }
}
