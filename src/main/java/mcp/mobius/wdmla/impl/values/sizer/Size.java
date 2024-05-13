package mcp.mobius.wdmla.impl.values.sizer;

import mcp.mobius.wdmla.api.ISize;

import java.awt.*;

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
