package com.gtnewhorizons.wdmla.plugin.universal;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.plugin.vanilla.VanillaIdentifiers;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.config.Configuration;

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

        WDMlaConfig.instance().getCategory(Identifiers.CONFIG_AUTOGEN + Configuration.CATEGORY_SPLITTER + Identifiers.NAMESPACE_UNIVERSAL)
                .setLanguageKey("provider.wdmla.universal.category");
    }
}
