package com.gtnewhorizons.wdmla.api;

import net.minecraftforge.common.config.ConfigCategory;

public interface IPluginConfig {

    ConfigCategory getCategory(String category);

    boolean getBoolean(ConfigEntry<Boolean> entry);

    int getInteger(ConfigEntry<Integer> entry);

    String getString(ConfigEntry<String> entry);
}
