package com.gtnewhorizons.wdmla.impl.format;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.Ticks;
import com.gtnewhorizons.wdmla.util.FormatUtil;

/**
 * Tries to format time unit into specified time unit with the help of {@link ChronoUnit} and {@link FormatUtil}
 */
public enum TimeFormattingPattern {

    /**
     * 123,456t
     */
    ALWAYS_TICK(tick -> FormatUtil.STANDARD.format(tick) + StatCollector.translateToLocal("hud.msg.wdmla.ticks")),

    /**
     * 7,890s
     */
    ALWAYS_SECOND(tick -> FormatUtil.STANDARD.format(Duration.of(tick, Ticks.INSTANCE).get(ChronoUnit.SECONDS))
            + StatCollector.translateToLocal("hud.msg.wdmla.seconds")),

    /**
     * 1h02m03s
     */
    HOUR_MIN_SEC(tick -> {
        Duration duration = Duration.of(tick, Ticks.INSTANCE);
        if (duration.toMinutes() < 1) {
            return FormatUtil.STANDARD.format(duration.get(ChronoUnit.SECONDS))
                    + StatCollector.translateToLocal("hud.msg.wdmla.seconds");
        } else if (duration.toHours() < 1) {
            return FormatUtil.STANDARD.format(duration.toMinutes())
                    + StatCollector.translateToLocal("hud.msg.wdmla.minutes")
                    + FormatUtil.TIME_PART.format(duration.minusMinutes(duration.toMinutes()).get(ChronoUnit.SECONDS))
                    + StatCollector.translateToLocal("hud.msg.wdmla.seconds");
        } else {
            return FormatUtil.STANDARD.format(duration.toHours())
                    + StatCollector.translateToLocal("hud.msg.wdmla.hours")
                    + FormatUtil.TIME_PART.format(duration.minusHours(duration.toHours()).toMinutes())
                    + StatCollector.translateToLocal("hud.msg.wdmla.minutes")
                    + FormatUtil.TIME_PART.format(duration.minusMinutes(duration.toMinutes()).get(ChronoUnit.SECONDS))
                    + StatCollector.translateToLocal("hud.msg.wdmla.seconds");
        }
    });

    public final Function<Integer, String> tickFormatter;

    TimeFormattingPattern(Function<Integer, String> tickFormatter) {
        this.tickFormatter = tickFormatter;
    }
}
