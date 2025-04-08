package com.gtnewhorizons.wdmla.plugin.debug;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.WDMlaPlugin;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.config.Configuration;

@SuppressWarnings("unused")
@WDMlaPlugin(uid = Identifiers.NAMESPACE_DEBUG)
public class DebugPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(HardnessProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(BlastResistanceProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(RegistryDataProvider.getBlock(), Block.class);
        registration.registerBlockComponent(CoordinatesProvider.getBlock(), Block.class);

        registration.registerEntityComponent(RegistryDataProvider.getEntity(), Entity.class);
        registration.registerEntityComponent(CoordinatesProvider.getEntity(), Entity.class);

        WDMlaConfig.instance()
                .getCategory(
                        Identifiers.CONFIG_AUTOGEN + Configuration.CATEGORY_SPLITTER
                                + Identifiers.NAMESPACE_DEBUG)
                .setLanguageKey("provider.wdmla.debug.category");
    }
}
