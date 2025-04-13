package com.gtnewhorizons.wdmla.test;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.provider.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.FluidView;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;

public enum TestFluidStorageProvider implements IServerExtensionProvider<FluidView.Data>, IClientExtensionProvider<FluidView.Data, FluidView> {
    INSTANCE;

    @Override
    public List<ClientViewGroup<FluidView>> getClientGroups(Accessor accessor, List<ViewGroup<FluidView.Data>> groups) {
        return ClientViewGroup.map(groups, FluidView::readDefault, (group, clientGroup) -> {
            if (group.id != null) {
                clientGroup.title = group.id;
            }
            clientGroup.messageType = MessageType.SUCCESS;
        });
    }

    @Override
    public List<ViewGroup<FluidView.Data>> getGroups(Accessor accessor) {
        var tank1 = new ViewGroup<>(Arrays.asList(new FluidView.Data(new FluidStack(FluidRegistry.WATER, 1000), 2000)));
        tank1.id = "1";
        var tank2 = new ViewGroup<>(Arrays.asList(
                new FluidView.Data(new FluidStack(FluidRegistry.LAVA, 500), 2000),
                new FluidView.Data(null, 2000)));
        return Arrays.asList(tank1, tank2, tank2, tank2, tank2);
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.TEST_FLUID_STORAGE;
    }
}
