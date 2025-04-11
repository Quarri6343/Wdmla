package com.gtnewhorizons.wdmla.api.ui;

/**
 * Represents something is being filled with two positive values
 */
public interface IFilledAmount {

    /**
     * @return The current amount of filling value which is always smaller than max value.
     */
    long getCurrent();

    /**
     * @return The maximum amount of filling value.
     */
    long getMax();
}
