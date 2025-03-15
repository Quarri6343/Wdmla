package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSilverfish;

public class VanillaPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(new SilverFishBlockProvider(), BlockSilverfish.class);
        registration.registerBlockComponent(new RedstoneWireProvider(), BlockRedstoneWire.class);
    }
}
