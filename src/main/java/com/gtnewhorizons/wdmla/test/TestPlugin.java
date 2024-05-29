package com.gtnewhorizons.wdmla.test;

import net.minecraft.block.BlockFurnace;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IWdmlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWdmlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWdmlaPlugin;
import com.gtnewhorizons.wdmla.impl.BlockAccessorClientHandler;

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
