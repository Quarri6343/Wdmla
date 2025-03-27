package com.gtnewhorizons.wdmla.api;

public class ConfigEntry<T> {

    public final String category;
    //config entry key & language key
    public final String key;
    public final T defaultValue;
    public final String comment;

    public ConfigEntry(String category, String key, T defaultValue, String comment) {
        this.category = category;
        this.key = key;
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    public ConfigEntry(IWDMlaProvider provider, String key, T defaultValue, String comment) {
        this.category = provider.getConfigCategory();
        this.key = key;
        this.defaultValue = defaultValue;
        this.comment = comment;
    }
}
