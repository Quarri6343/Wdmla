package com.gtnewhorizons.wdmla.test;

import java.util.Arrays;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public enum TestThemeBlockProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public ResourceLocation getUid() {
        return Identifiers.TEST_THEME_BLOCK;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        tooltip.text("normal");
        tooltip.child(ThemeHelper.INSTANCE.info("This is info"));
        tooltip.child(ThemeHelper.INSTANCE.title("This is title"));
        tooltip.child(ThemeHelper.INSTANCE.success("This is success"));
        tooltip.child(ThemeHelper.INSTANCE.warning("This is warning"));
        tooltip.child(ThemeHelper.INSTANCE.danger("This is danger"));
        tooltip.child(ThemeHelper.INSTANCE.failure("This is failure"));
        tooltip.child(ThemeHelper.INSTANCE.amount(5, 10, new TextComponent("This is test amount")));
        tooltip.child(ThemeHelper.INSTANCE.value("The answer", "42"));
        tooltip.child(
                ThemeHelper.INSTANCE.itemProgress(
                        Arrays.asList(new ItemStack(Items.egg)),
                        Arrays.asList(new ItemStack(Items.chicken)),
                        1,
                        8,
                        new TextComponent("1 / 8"),
                        accessor.showDetails()));
    }
}
