package com.gtnewhorizons.wdmla.addon;

import static mcp.mobius.waila.api.SpecialChars.*;

import com.gtnewhorizons.wdmla.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.wailacompat.RayTracingCompat;

import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;

public class DefaultInfoProvider implements IBlockComponentProvider {

    @Override
    public ResourceLocation getUid() {
        return Identifiers.DEFAULT;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.DEFAULT_INFO;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        // step 1: check whether waila has custom Wailastack or not
        ItemStack overrideStack = RayTracingCompat.INSTANCE.getWailaStack(accessor.getHitResult());

        // step 2: construct an actual icon
        ITooltip row = tooltip.horizontal();
        ItemStack itemStack = overrideStack != null ? overrideStack : accessor.getItemForm();
        row.child(new ItemComponent(itemStack).tag(Identifiers.ITEM_ICON));

        ITooltip row_vertical = row.vertical();
        row_vertical.child(
                new TextComponent(WHITE + DisplayUtil.itemDisplayNameShort(itemStack)).tag(Identifiers.ITEM_NAME));
        String modName = ModIdentification.nameFromStack(itemStack);
        if (modName != null && !modName.isEmpty()) {
            row_vertical.child(new TextComponent(BLUE + ITALIC + modName).tag(Identifiers.MOD_NAME));
        }
    }
}
