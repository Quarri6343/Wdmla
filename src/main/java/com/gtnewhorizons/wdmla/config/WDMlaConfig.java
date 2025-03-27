package com.gtnewhorizons.wdmla.config;

import java.io.File;
import java.util.Arrays;

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
        getCategory(Identifiers.CONFIG_AUTOGEN).setLanguageKey("option.autogen.category");
        getCategory(Identifiers.CONFIG_AUTOGEN + Configuration.CATEGORY_SPLITTER + Identifiers.NAMESPACE_CORE).setLanguageKey("option.core.category");
        reloadProviderAutogenConfigs();
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

    public <T extends Enum<T>> T getEnum(ConfigEntry<T> entry) {
        Class<T> enumType = entry.defaultValue.getDeclaringClass();
        return T.valueOf(
                enumType,
                getString(
                        entry.key,
                        entry.category,
                        entry.defaultValue.toString(),
                        entry.comment,
                        Arrays.stream(enumType.getEnumConstants())
                                .map(Enum::toString)
                                .toArray(String[]::new)));

    }

    public void reloadProviderAutogenConfigs() {
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
                        provider.getConfigCategory(),
                        "option.autogen.enabled",
                        provider.enabledByDefault(),
                        ""));
    }

    public int getProviderPriority(IComponentProvider<?> provider) {
        if (provider.isPriorityFixed()) {
            return provider.getDefaultPriority();
        }

        return getInteger(
                new ConfigEntry<>(
                        provider.getConfigCategory(),
                        "option.autogen.priority",
                        provider.getDefaultPriority(),
                        ""));
    }
}
