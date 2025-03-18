package com.gtnewhorizons.wdmla.addon.core;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.util.ResourceLocation;

public class HardnessProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        float hardness = accessor.getBlock().getBlockHardness(
                accessor.getWorld(), accessor.getHitResult().blockX, accessor.getHitResult().blockY, accessor.getHitResult().blockZ);
        tooltip.child(
                ThemeHelper.INSTANCE.value(LangUtil.translateG("hud.msg.hardness"), String.format("%.0f", hardness))
        );
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.HARDNESS;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }
}
