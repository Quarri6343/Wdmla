package com.gtnewhorizons.wdmla.api.provider;

import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;

/**
 * Provides provider-specific time formatter when implemented on {@link IComponentProvider}.<br>
 * The config loader will mark the provider requires time formatter settings, then adds config entry to {@link com.gtnewhorizons.wdmla.gui.ModsMenuScreenConfig}.<br>
 * The formatter can be retrieved by {@code WDMlaConfig.instance().getTimeFormatter(this)}.<br>
 */
public interface ITimeFormatConfigurable extends IWDMlaProvider {

    /**
     * gets the default formatter this provider will use
     * @return the default formatter
     */
    default TimeFormattingPattern getDefaultTimeFormatter() {
        return TimeFormattingPattern.ALWAYS_TICK;
    }
}
