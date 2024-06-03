package com.gtnewhorizons.wdmla.addon;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.impl.BlockAccessorClientHandler;
import net.minecraft.block.Block;

/**
 * The base plugin that registers everything essential
 */
public class CorePlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerAccessorHandler(BlockAccessor.class, new BlockAccessorClientHandler());

        registration.registerBlockComponent(new DefaultInfoProvider(), Block.class);
        registration.registerBlockComponent(new HarvestToolProvider(), Block.class);
    }
}
