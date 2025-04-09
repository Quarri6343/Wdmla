package com.gtnewhorizons.wdmla.plugin.core;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public enum BlockFaceProvider implements IBlockComponentProvider {

    INSTANCE;

    private static final String[] SIDES = {"down", "up", "east", "west", "north", "south"};

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        int side = accessor.getHitResult().sideHit;
        if (side != -1 && tooltip.getChildWithTag(Identifiers.ITEM_NAME_ROW) instanceof ITooltip itemNameRow) {
            itemNameRow.text(String.format(StatCollector.translateToLocal("hud.msg.wdmla.side.decorator"),
                    StatCollector.translateToLocal("hud.msg.wdmla.side." + SIDES[side])));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.BLOCK_FACE;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.DEFAULT_INFO + 1;
    }

    @Override
    public boolean isPriorityFixed() {
        return true;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }
}
