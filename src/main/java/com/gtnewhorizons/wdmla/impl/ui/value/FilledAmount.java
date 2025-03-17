package com.gtnewhorizons.wdmla.impl.ui.value;

import com.gtnewhorizons.wdmla.api.ui.IFilledAmount;

public class FilledAmount implements IFilledAmount {

    long current;
    long max;

    public FilledAmount(long current, long max) {
        if (current < 0) {
            current = 0;
        }
        if (max < 0) {
            throw new IllegalArgumentException("Max amount cannot below zero.");
        }
        if (current > max) {
            throw new IllegalArgumentException("Current amount cannot exceed max amount.");
        }

        this.current = current;
        this.max = max;
    }

    @Override
    public long getCurrent() {
        return current;
    }

    @Override
    public long getMax() {
        return max;
    }
}
