package com.gtnewhorizons.wdmla.addon.harvestability;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import net.minecraft.block.Block;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import net.minecraftforge.common.config.Configuration;

public class HarvestabilityPlugin implements IWDMlaPlugin {

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(HarvestToolProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(LegacyHarvestToolProvider.INSTANCE, Block.class);

        WDMlaConfig.instance().getCategory(Identifiers.CONFIG_AUTOGEN + Configuration.CATEGORY_SPLITTER + HarvestabilityIdentifiers.NAMESPACE_HARVESTABILITY)
                .setLanguageKey("option.harvestability.category");
    }
}
