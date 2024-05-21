package mcp.mobius.wdmla.impl.ui.value.sizer;

import java.awt.*;

import mcp.mobius.wdmla.api.ui.sizer.ISize;

public class Size implements ISize {

    private final Dimension dim;

    public Size(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size must not below zero");
        }
        dim = new Dimension(width, height);
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
