package com.gtnewhorizons.wdmla.impl.ui.sizer;

import java.awt.Dimension;

import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;

public class Size implements ISize {

    protected final Dimension dim;

    public Size(int width, int height) {
        dim = new Dimension(width, height);
        verify();
    }

    private void verify() {
        if (dim.width < 0 || dim.height < 0) {
            throw new IllegalArgumentException("Size must not below zero");
        }
    }

    @Override
    public int getW() {
        return dim.width;
    }

    @Override
    public int getH() {
        return dim.height;
    }
}
