package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;

import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.util.StatCollector;

public enum RedstoneStateProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        Block block = accessor.getBlock();

        if (block == Blocks.lever && PluginsConfig.vanilla.redstoneState.showLeverState) {
            IComponent redstoneOn = (accessor.getMetadata() & 8) == 0
                    ? ThemeHelper.INSTANCE.failure(StatCollector.translateToLocal("hud.msg.wdmla.off"))
                    : ThemeHelper.INSTANCE.success(StatCollector.translateToLocal("hud.msg.wdmla.on"));
            tooltip.child(
                    new HPanelComponent().text(String.format("%s: ", StatCollector.translateToLocal("hud.msg.wdmla.state")))
                            .child(redstoneOn));
        } else if (((block == Blocks.unpowered_repeater) || (block == Blocks.powered_repeater))
                && PluginsConfig.vanilla.redstoneState.showRepeaterDelay) {
                    int tick = (accessor.getMetadata() >> 2) + 1;
                    tooltip.child(
                            ThemeHelper.INSTANCE.value(
                                    StatCollector.translateToLocal("hud.msg.wdmla.delay"),
                                    String.format("%d %s", tick, StatCollector.translateToLocal("hud.msg.wdmla.ticks"))));
                } else
            if (((block == Blocks.unpowered_comparator) || (block == Blocks.powered_comparator))
                    && PluginsConfig.vanilla.redstoneState.showComparatorMode) {
                        String mode = ((accessor.getMetadata() >> 2) & 1) == 0
                                ? StatCollector.translateToLocal("hud.msg.wdmla.comparator")
                                : StatCollector.translateToLocal("hud.msg.wdmla.substractor");
                        tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.mode"), mode));
                    }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.REDSTONE_STATE;
    }
}
