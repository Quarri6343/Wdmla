package com.gtnewhorizons.wdmla.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizons.wdmla.api.ConfigEntry;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
        reloadComponentProviderConfigs();

        getCategory(Identifiers.CONFIG_GENERAL).setComment("These are the WDMla exclusive settings");
        getBoolean(Identifiers.CONFIG_FORCE_LEGACY);
        getBoolean(Identifiers.CONFIG_GHOST_PRODUCT);

        getBoolean(Identifiers.CONFIG_SHOW_ICON);
        getBoolean(Identifiers.CONFIG_SHOW_BLOCK_NAME);
        getBoolean(Identifiers.CONFIG_SHOW_MOD_NAME);
        getBoolean(Identifiers.CONFIG_SHOW_ENTITY_NAME);
        getInteger(Identifiers.CONFIG_MAX_ENTITY_HEALTH_FOR_TEXT);
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

    public void reloadComponentProviderConfigs() {
        for (IComponentProvider<?> provider : WDMlaClientRegistration.instance().getAllProvidersWithoutInfo()) {
            isProviderEnabled(provider);
            WDMlaCommonRegistration.instance().priorities.put(provider, getProviderPriority(provider));
        }
    }

    // TODO:split provider config file
    public boolean isProviderEnabled(IComponentProvider<?> provider) {
        if (provider.isRequired()) {
            return true;
        }

        return getBoolean(
                new ConfigEntry<>(
                        Identifiers.CONFIG_PROVIDER + "."
                                + provider.getUid().getResourceDomain()
                                + "."
                                + provider.getUid().getResourcePath(),
                        Identifiers.CONFIG_PROVIDER_ENABLED,
                        provider.enabledByDefault(),
                        ""));
    }

    public int getProviderPriority(IComponentProvider<?> provider) {
        if (provider.isPriorityFixed()) {
            return provider.getDefaultPriority();
        }

        return getInteger(
                new ConfigEntry<>(
                        Identifiers.CONFIG_PROVIDER + "."
                                + provider.getUid().getResourceDomain()
                                + "."
                                + provider.getUid().getResourcePath(),
                        Identifiers.CONFIG_PROVIDER_PRIORITY,
                        provider.getDefaultPriority(),
                        ""));
    }
}
