package com.gtnewhorizons.wdmla.test;

import net.minecraft.block.BlockFurnace;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.impl.BlockAccessorClientHandler;

public class TestPlugin implements IWDMlaPlugin {

    @Override
    public void register(IWDMlaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TestComponentProvider(), BlockFurnace.class);
    }

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockIcon(new TestComponentProvider(), BlockFurnace.class);
        registration.registerBlockComponent(new TestComponentProvider(), BlockFurnace.class);
    }
}
