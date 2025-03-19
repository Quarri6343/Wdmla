package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;

import mcp.mobius.waila.cbcore.LangUtil;

public enum RedstoneStateProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Block block = accessor.getBlock();

        if (block == Blocks.lever) {
            IComponent redstoneOn = (accessor.getMetadata() & 8) == 0
                    ? ThemeHelper.INSTANCE.failure(LangUtil.translateG("hud.msg.off"))
                    : ThemeHelper.INSTANCE.success(LangUtil.translateG("hud.msg.on"));
            tooltip.child(
                    new HPanelComponent().text(String.format("%s: ", LangUtil.translateG("hud.msg.state")))
                            .child(redstoneOn));
        } else if ((block == Blocks.unpowered_repeater) || (block == Blocks.powered_repeater)) {
            int tick = (accessor.getMetadata() >> 2) + 1;
            tooltip.child(
                    new HPanelComponent().text(String.format("%s: ", LangUtil.translateG("hud.msg.delay"))).child(
                            ThemeHelper.INSTANCE.info(
                                    String.format(
                                            "%s%s",
                                            tick,
                                            (tick == 1 ? LangUtil.translateG("hud.msg.tick")
                                                    : LangUtil.translateG("hud.msg.ticks"))))));
        } else if ((block == Blocks.unpowered_comparator) || (block == Blocks.powered_comparator)) {
            String mode = ((accessor.getMetadata() >> 2) & 1) == 0 ? LangUtil.translateG("hud.msg.comparator")
                    : LangUtil.translateG("hud.msg.substractor");
            tooltip.child(
                    new HPanelComponent().text(String.format("%s: ", LangUtil.translateG("hud.msg.mode")))
                            .child(ThemeHelper.INSTANCE.info(mode)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.REDSTONE_STATE;
    }
}
