package com.gtnewhorizons.wdmla.api;

import net.minecraftforge.common.config.Configuration;

public interface IConfigProvider {

    String categoryName();

    void loadConfig(IPluginConfig config);
}
