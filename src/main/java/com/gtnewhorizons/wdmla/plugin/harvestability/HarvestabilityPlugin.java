package com.gtnewhorizons.wdmla.plugin.harvestability;

import com.gtnewhorizons.wdmla.api.WDMlaPlugin;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;

@SuppressWarnings("unused")
@WDMlaPlugin(uid = HarvestabilityIdentifiers.NAMESPACE_HARVESTABILITY)
public class HarvestabilityPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(HarvestToolProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(LegacyHarvestToolProvider.INSTANCE, Block.class);

        WDMlaConfig.instance()
                .getCategory(
                        Identifiers.CONFIG_AUTOGEN + Configuration.CATEGORY_SPLITTER
                                + HarvestabilityIdentifiers.NAMESPACE_HARVESTABILITY)
                .setLanguageKey("provider.wdmla.harvestability.category");
    }
}
