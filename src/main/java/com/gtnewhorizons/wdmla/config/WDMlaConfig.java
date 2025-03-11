package com.gtnewhorizons.wdmla.config;

import com.gtnewhorizons.wdmla.api.Identifiers;
import mcp.mobius.waila.api.impl.ConfigHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * the new configuration file added by WDMla
 */
//TODO: module config
public class WDMlaConfig extends Configuration {

    private static WDMlaConfig _instance;

    public final Property forceLegacyMode;

    public WDMlaConfig(File configFile) {
        super(configFile);

        getCategory(Identifiers.CONFIG_GENERAL).setComment("These are the WDMla exclusive settings");
        forceLegacyMode = get(Identifiers.CONFIG_GENERAL, Identifiers.CONFIG_KEY_FORCELEGACY, false, Identifiers.CONFIG_COMMENT_FORCELEGACY);

        save();
        _instance = this;
    }

    public static WDMlaConfig instance() {
        return _instance;
    }
}
