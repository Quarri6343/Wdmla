package com.gtnewhorizons.wdmla.plugin.vanilla;

import java.util.Arrays;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.FluidView;
import com.gtnewhorizons.wdmla.plugin.universal.FluidStorageProvider;

public enum CauldronProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        FluidView fluidView = FluidView.readDefault(
                new FluidView.Data(new FluidStack(FluidRegistry.WATER, 1000 * accessor.getMetadata() / 3), 1000));
        if (fluidView == null) {
            return;
        }
        fluidView.overrideText = accessor.getMetadata() == 0 ? StatCollector.translateToLocal("hud.msg.wdmla.empty")
                : String.format("%d / 3", accessor.getMetadata());
        List<ClientViewGroup<FluidView>> viewList = Arrays.asList(new ClientViewGroup<>(Arrays.asList(fluidView)));
        FluidStorageProvider.getBlock().append(tooltip, accessor, viewList);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.CAULDRON;
    }
}
