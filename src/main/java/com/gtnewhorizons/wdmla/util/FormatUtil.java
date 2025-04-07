package com.gtnewhorizons.wdmla.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtil {

    public static final DecimalFormat STANDARD = getDecimalFormat();
    public static final DecimalFormat TIME_PART = getTimePartFormat();

    private static DecimalFormat getDecimalFormat() {
        DecimalFormat decimalFormat;
        NumberFormat format = NumberFormat.getNumberInstance(Locale.US); // we don't want it to depend on JVM system
                                                                         // language
        if (format instanceof DecimalFormat) {
            decimalFormat = (DecimalFormat) format;
        } else {
            decimalFormat = new DecimalFormat();
        }
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        return decimalFormat;
    }

    private static DecimalFormat getTimePartFormat() {
        DecimalFormat decimalFormat;
        NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
        if (format instanceof DecimalFormat) {
            decimalFormat = (DecimalFormat) format;
        } else {
            decimalFormat = new DecimalFormat();
        }
        decimalFormat.applyPattern("00");
        decimalFormat.setMaximumIntegerDigits(2);
        decimalFormat.setParseIntegerOnly(true);
        decimalFormat.setGroupingUsed(false);
        return decimalFormat;
    }
}
