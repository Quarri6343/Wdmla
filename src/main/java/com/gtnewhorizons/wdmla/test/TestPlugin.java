package com.gtnewhorizons.wdmla.test;

import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.entity.passive.EntityPig;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import net.minecraft.tileentity.TileEntityCommandBlock;

public class TestPlugin implements IWDMlaPlugin {

    @Override
    public void register(IWDMlaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TestNBTBlockProvider(), BlockCommandBlock.class);
        registration.registerEntityDataProvider(new TestEntityProvider(), EntityPig.class);
    }

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(new TestHeaderProvider(), BlockCommandBlock.class);
        registration.registerBlockComponent(new TestNBTBlockProvider(), BlockCommandBlock.class);
        registration.registerBlockComponent(new TestThemeBlockProvider(), BlockDirt.class);
        registration.registerEntityComponent(new TestEntityProvider(), EntityPig.class);
    }
}
