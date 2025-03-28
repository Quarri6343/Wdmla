package com.gtnewhorizons.wdmla.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * the new configuration file added by WDMla
 */
public class WDMlaConfig extends Configuration {

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
        getCategory(Identifiers.CONFIG_AUTOGEN).setLanguageKey("option.wdmla.autogen.category");
        getCategory(Identifiers.CONFIG_AUTOGEN + Configuration.CATEGORY_SPLITTER + Identifiers.NAMESPACE_CORE).setLanguageKey("provider.wdmla.core.category");
        reloadProviderAutogenConfigs();
    }

    public void reloadProviderAutogenConfigs() {
        for (IComponentProvider<?> provider : WDMlaClientRegistration.instance().getAllProvidersWithoutInfo()) {
            getCategory(provider.getConfigCategory()).setLanguageKey(provider.getLangKey());
            isProviderEnabled(provider);
            WDMlaCommonRegistration.instance().priorities.put(provider, getProviderPriority(provider));
        }
    }

    // TODO:split provider config file
    public boolean isProviderEnabled(IComponentProvider<?> provider) {
        if (provider.isRequired()) {
            return true;
        }

        return get(
                provider.getConfigCategory(),
                "option.wdmla.autogen.enabled",
                provider.enabledByDefault(),
                "").getBoolean();
    }

    public int getProviderPriority(IComponentProvider<?> provider) {
        if (provider.isPriorityFixed()) {
            return provider.getDefaultPriority();
        }

        return get(
                        provider.getConfigCategory(),
                        "option.wdmla.autogen.priority",
                        provider.getDefaultPriority(),
                        "").getInt();
    }
}
