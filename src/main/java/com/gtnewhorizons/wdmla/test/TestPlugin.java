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
        registration.registerBlockDataProvider(TestNBTBlockProvider.INSTANCE, BlockCommandBlock.class);
        registration.registerEntityDataProvider(TestEntityProvider.INSTANCE, EntityPig.class);
    }

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(TestHeaderProvider.INSTANCE, BlockCommandBlock.class);
        registration.registerBlockComponent(TestNBTBlockProvider.INSTANCE, BlockCommandBlock.class);
        registration.registerBlockComponent(TestThemeBlockProvider.INSTANCE, BlockDirt.class);
        registration.registerEntityComponent(TestEntityProvider.INSTANCE, EntityPig.class);
    }
}
