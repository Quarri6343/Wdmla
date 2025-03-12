package com.gtnewhorizons.wdmla.api;

public class ConfigEntry<T> {

    public final String category;
    public final String key;
    public final T defaultValue;
    public final String comment;

    public ConfigEntry(String category, String key, T defaultValue, String comment) {
        this.category = category;
        this.key = key;
        this.defaultValue = defaultValue;
        this.comment = comment;
    }
}
