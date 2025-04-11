package com.gtnewhorizons.wdmla.api.provider;

/**
 * Toggleable provider template.
 */
public interface IToggleableProvider extends IWDMlaProvider {

    /**
     * Blocks cheat type providers easy access.
     * 
     * @return Whether player can toggle the provider with in game config or not.
     */
    default boolean canToggleInGui() {
        return true;
    }

    /**
     * Configures not important information (like registry name) to be displayed by default. Use along with
     * {@link IToggleableProvider#canToggleInGui()} if you want to handle cheat config.
     * 
     * @return Whether the provider is enabled by default in game config or not.
     */
    default boolean enabledByDefault() {
        return true;
    }
}
