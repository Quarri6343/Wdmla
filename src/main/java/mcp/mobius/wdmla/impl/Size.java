package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.ISize;

public class Size implements ISize {

    private int width;
    private int height;

    public Size(int width, int height) {
        if(width < 0 || height < 0) {
            throw new IllegalArgumentException("Invalid size");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public int getW() {
        return width;
    }

    @Override
    public int getH() {
        return height;
    }
}
