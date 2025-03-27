package com.gtnewhorizons.wdmla.addon.core;

import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;

public class CoreConfigProvider implements IConfigProvider {

    @Override
    public void loadConfig(IPluginConfig config) {
        config.getCategory(Identifiers.CONFIG_CORE_CATEGORY).setLanguageKey(Identifiers.CONFIG_CORE_LANGKEY);
        config.getBoolean(Identifiers.CONFIG_SHOW_ICON);
        config.getBoolean(Identifiers.CONFIG_SHOW_BLOCK_NAME);
        config.getBoolean(Identifiers.CONFIG_SHOW_MOD_NAME);
        config.getBoolean(Identifiers.CONFIG_SHOW_ENTITY_NAME);
        config.getInteger(Identifiers.CONFIG_MAX_ENTITY_HEALTH_FOR_TEXT);
    }
}
