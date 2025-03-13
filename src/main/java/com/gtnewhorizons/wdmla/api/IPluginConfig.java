package com.gtnewhorizons.wdmla.api;

import net.minecraftforge.common.config.ConfigCategory;

public interface IPluginConfig {

    ConfigCategory getCategory(String category);

    /**
     * get or register Boolean value to the config file
     * 
     * @return registered value
     */
    boolean getBoolean(ConfigEntry<Boolean> entry);

    /**
     * get or register Integer value to the config file
     * 
     * @return registered value
     */
    int getInteger(ConfigEntry<Integer> entry);

    /**
     * get or register String value to the config file
     * 
     * @return registered value
     */
    String getString(ConfigEntry<String> entry);
}
