package com.gtnewhorizons.wdmla.addon.harvestability;

import net.minecraft.block.Block;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;

public class HarvestabilityPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(new HarvestToolProvider(), Block.class);
        registration.registerBlockComponent(new LegacyHarvestToolProvider(), Block.class);
        registration.registerConfigComponent(new HarvestabilityConfigProvider());
    }
}
