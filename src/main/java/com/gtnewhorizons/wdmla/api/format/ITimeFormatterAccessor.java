package com.gtnewhorizons.wdmla.api.format;

import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;

public interface ITimeFormatterAccessor {

    default TimeFormattingPattern getDefaultTimeFormatter() {
        return TimeFormattingPattern.ALWAYS_TICK;
    }
}
