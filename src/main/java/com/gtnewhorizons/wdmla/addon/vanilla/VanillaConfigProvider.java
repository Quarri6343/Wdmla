package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;

public class VanillaConfigProvider implements IConfigProvider {

    @Override
    public void loadConfig(IPluginConfig config) {
        config.getBoolean(VanillaIdentifiers.CONFIG_SHOW_PET_SIT);
        config.getBoolean(VanillaIdentifiers.CONFIG_SHOW_PET_OWNER);
    }
}
