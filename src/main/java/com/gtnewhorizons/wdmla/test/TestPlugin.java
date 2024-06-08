package com.gtnewhorizons.wdmla.test;

import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.passive.EntityPig;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;

public class TestPlugin implements IWDMlaPlugin {

    @Override
    public void register(IWDMlaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TestBodyProvider(), BlockFurnace.class);
        registration.registerEntityDataProvider(new TestEntityProvider(), EntityPig.class);
    }

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(new TestHeaderProvider(), BlockFurnace.class);
        registration.registerBlockComponent(new TestBodyProvider(), BlockFurnace.class);
        registration.registerEntityComponent(new TestEntityProvider(), EntityPig.class);
    }
}
