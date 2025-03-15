package com.gtnewhorizons.wdmla.addon;

import static mcp.mobius.waila.api.SpecialChars.*;

import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.wailacompat.RayTracingCompat;

import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;

public class DefaultBlockInfoProvider implements IBlockComponentProvider {

    @Override
    public ResourceLocation getUid() {
        return Identifiers.DEFAULT_BLOCK;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.DEFAULT_INFO;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        // step 1: check whether waila has custom Wailastack or not
        ItemStack overrideStack = RayTracingCompat.INSTANCE.getWailaStack(accessor.getHitResult());

        // step 2: construct an actual icon
        ITooltip row = tooltip.horizontal();
        ItemStack itemStack = overrideStack != null ? overrideStack : accessor.getItemForm();
        if (config.getBoolean(Identifiers.CONFIG_SHOW_ICON)) {
            row.child(new ItemComponent(itemStack).tag(Identifiers.ITEM_ICON));
        }

        ITooltip row_vertical = row.vertical();
        if (config.getBoolean(Identifiers.CONFIG_SHOW_BLOCK_NAME)) {
            row_vertical.child(
                    ThemeHelper.INSTANCE.title(DisplayUtil.itemDisplayNameShort(itemStack)).tag(Identifiers.ITEM_NAME));
        }
        String modName = ModIdentification.nameFromStack(itemStack);
        if (modName != null && !modName.isEmpty() && config.getBoolean(Identifiers.CONFIG_SHOW_MOD_NAME)) {
            row_vertical.child(new TextComponent(BLUE + ITALIC + modName).tag(Identifiers.MOD_NAME));
        }
    }

    @Override
    public boolean isPriorityFixed() {
        return true;
    }
}
