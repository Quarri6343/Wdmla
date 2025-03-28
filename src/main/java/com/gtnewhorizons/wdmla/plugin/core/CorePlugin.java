package com.gtnewhorizons.wdmla.plugin.core;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.impl.BlockAccessorClientHandler;
import com.gtnewhorizons.wdmla.impl.EntityAccessorClientHandler;
import net.minecraft.entity.EntityLiving;

/**
 * The base plugin that registers everything essential
 */
public class CorePlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerAccessorHandler(BlockAccessor.class, new BlockAccessorClientHandler());
        registration.registerAccessorHandler(EntityAccessor.class, new EntityAccessorClientHandler());

        registration.registerBlockComponent(DefaultBlockInfoProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(HardnessProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(BlastResistanceProvider.INSTANCE, Block.class);
        registration.registerEntityComponent(DefaultEntityInfoProvider.INSTANCE, Entity.class);
        registration.registerEntityComponent(EntityHealthProvider.INSTANCE, Entity.class);
        registration.registerEntityComponent(EntityEquipmentProvider.INSTANCE, EntityLiving.class);
        registration.registerEntityComponent(EntityArmorProvider.INSTANCE, EntityLiving.class);
    }
}
