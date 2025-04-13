package com.gtnewhorizons.wdmla.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * For unifying formatting.
 * 
 * @see com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern
 */
public class FormatUtil {

    /**
     * Example: 123456.789 -> 123,456.79
     */
    public static final DecimalFormat STANDARD = getDecimalFormat();
    /**
     * Example: 1 -> 01 (for two-digit time unit)
     */
    public static final DecimalFormat TIME_PART = getTimePartFormat();
    /**
     * Example: 123456.789 -> 123456.79
     */
    public static final DecimalFormat STANDARD_NO_GROUP = getNoGroupFormat();
    /**
     * Example: 0.12345 -> 12%
     */
    public static final NumberFormat PERCENTAGE_STANDARD = getPercentageStandardFormat();

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

    private static DecimalFormat getNoGroupFormat() {
        DecimalFormat decimalFormat;
        NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
        if (format instanceof DecimalFormat) {
            decimalFormat = (DecimalFormat) format;
        } else {
            decimalFormat = new DecimalFormat();
        }
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat;
    }

    private static NumberFormat getPercentageStandardFormat() {
        NumberFormat percentFormat = NumberFormat.getPercentInstance(Locale.US); // we don't want it to depend on JVM
                                                                                 // system
        percentFormat.setGroupingUsed(false);
        percentFormat.setMaximumFractionDigits(0);
        percentFormat.setRoundingMode(RoundingMode.HALF_UP);
        return percentFormat;
    }
}
