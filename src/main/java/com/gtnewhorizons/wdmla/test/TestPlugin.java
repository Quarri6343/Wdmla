package com.gtnewhorizons.wdmla.test;

import com.gtnewhorizons.wdmla.api.IWdmlaPlugin;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IWdmlaCommonRegistration;
import com.gtnewhorizons.wdmla.impl.BlockAccessorClientHandler;

import com.gtnewhorizons.wdmla.api.IWdmlaClientRegistration;
import net.minecraft.block.BlockFurnace;

public class TestPlugin implements IWdmlaPlugin {

    @Override
    public void register(IWdmlaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TestComponentProvider(), BlockFurnace.class);
    }

    @Override
    public void registerClient(IWdmlaClientRegistration registration) {
        registration.registerAccessorHandler(BlockAccessor.class, new BlockAccessorClientHandler());

        registration.registerBlockIcon(new TestComponentProvider(), BlockFurnace.class);
        registration.registerBlockComponent(new TestComponentProvider(), BlockFurnace.class);
    }
}
