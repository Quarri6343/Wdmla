package com.gtnewhorizons.wdmla.plugin.core;

import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.util.StatCollector;

public enum EnchantmentPowerProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        float power = accessor.getBlock().getEnchantPowerBonus(accessor.getPlayer().worldObj, 0, 0, 0);
        if (power > 0) {
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.enchantment.power"),
                            String.format("%.2f", power)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.ENCHANTMENT_POWER;
    }
}
