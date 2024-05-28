package com.gtnewhorizons.wdmla.impl.ui.value;

import com.gtnewhorizons.wdmla.api.ui.IProgress;

public class Progress implements IProgress {

    long current;
    long max;

    public Progress(long current, long max) {
        if (current < 0) {
            current = 0;
        }
        if (max < 0) {
            throw new IllegalArgumentException("Max progress cannot below zero.");
        }
        if (current > max) {
            throw new IllegalArgumentException("Current progress cannot exceed max progress.");
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
