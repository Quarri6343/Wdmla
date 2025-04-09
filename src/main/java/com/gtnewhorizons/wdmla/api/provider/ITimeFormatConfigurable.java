package com.gtnewhorizons.wdmla.api.provider;

import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;

public interface ITimeFormatConfigurable {

    default TimeFormattingPattern getDefaultTimeFormatter() {
        return TimeFormattingPattern.ALWAYS_TICK;
    }
}
