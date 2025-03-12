package com.gtnewhorizons.wdmla.config;

import com.gtnewhorizons.wdmla.api.ConfigEntry;
import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * the new configuration file added by WDMla
 */
public class WDMlaConfig extends Configuration implements IPluginConfig {

    @SideOnly(Side.CLIENT)
    private static WDMlaConfig _instance;

    public WDMlaConfig(File configFile) {
        super(configFile);
        _instance = this;
    }

    public static WDMlaConfig instance() {
        return _instance;
    }

    public void reloadConfig() {
        for (IConfigProvider configProvider : WDMlaClientRegistration.instance().configProviders) {
            configProvider.loadConfig(this);
        }

        getCategory(Identifiers.CONFIG_GENERAL).setComment("These are the WDMla exclusive settings");
        getBoolean(Identifiers.CONFIG_FORCE_LEGACY);
    }

    public boolean getBoolean(ConfigEntry<Boolean> entry) {
        return get(entry.category, entry.key, entry.defaultValue, entry.comment).getBoolean();
    }

    public int getInteger(ConfigEntry<Integer> entry) {
        return get(entry.category, entry.key, entry.defaultValue, entry.comment).getInt();
    }

    public String getString(ConfigEntry<String> entry) {
        return get(entry.category, entry.key, entry.defaultValue, entry.comment).getString();
    }
}
