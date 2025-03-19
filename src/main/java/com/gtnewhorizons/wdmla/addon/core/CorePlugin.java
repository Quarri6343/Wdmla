package com.gtnewhorizons.wdmla.addon.core;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.impl.BlockAccessorClientHandler;
import com.gtnewhorizons.wdmla.impl.EntityAccessorClientHandler;

/**
 * The base plugin that registers everything essential
 */
public class CorePlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerAccessorHandler(BlockAccessor.class, new BlockAccessorClientHandler());
        registration.registerAccessorHandler(EntityAccessor.class, new EntityAccessorClientHandler());

        registration.registerBlockComponent(new DefaultBlockInfoProvider(), Block.class);
        registration.registerBlockComponent(new HardnessProvider(), Block.class);
        registration.registerBlockComponent(new BlastResistanceProvider(), Block.class);
        registration.registerEntityComponent(new DefaultEntityInfoProvider(), Entity.class);
        registration.registerEntityComponent(new EntityHealthProvider(), Entity.class);
    }
}
