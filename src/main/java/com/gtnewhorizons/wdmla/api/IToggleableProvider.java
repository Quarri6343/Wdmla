package com.gtnewhorizons.wdmla.api;

public interface IToggleableProvider extends IWDMlaProvider {

    /**
     * Whether player can toggle the provider with in game config.
     */
    default boolean canToggleInGui() {
        return true;
    }

    default boolean enabledByDefault() {
        return true;
    }
}
