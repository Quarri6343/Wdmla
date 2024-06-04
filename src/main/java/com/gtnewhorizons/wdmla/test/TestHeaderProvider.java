package com.gtnewhorizons.wdmla.test;

import static mcp.mobius.waila.api.SpecialChars.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public class TestHeaderProvider implements IComponentProvider<BlockAccessor> {

    @Override
    public ResourceLocation getUid() {
        return Identifiers.TEST_HEAD;
    }

    @Override
    public int getDefaultPriority() {
        return -9000;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        ItemStack itemStack = new ItemStack(Blocks.lit_furnace);
        tooltip.replaceChildWithTag(Identifiers.ITEM_ICON, new ItemComponent(itemStack).tag(Identifiers.ITEM_ICON));
        tooltip.replaceChildWithTag(
                Identifiers.ITEM_NAME,
                new TextComponent(WHITE + "Furnace").tag(Identifiers.ITEM_NAME));
        tooltip.replaceChildWithTag(
                Identifiers.MOD_NAME,
                new TextComponent(BLUE + ITALIC + "WDMla").tag(Identifiers.MOD_NAME));
    }
}
