package com.gtnewhorizons.wdmla.plugin.universal;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

public class UniversalPlugin implements IWDMlaPlugin {

    @Override
    public void register(IWDMlaCommonRegistration registration) {
        registration.registerBlockDataProvider(ItemStorageProvider.getBlock(), Block.class);
        registration.registerEntityDataProvider(ItemStorageProvider.getEntity(), Entity.class);
    }

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(ItemStorageProvider.getBlock(), Block.class);
        registration.registerEntityComponent(ItemStorageProvider.getEntity(), Entity.class);
        registration.registerItemStorageClient(ItemStorageProvider.Extension.INSTANCE);
    }
}
