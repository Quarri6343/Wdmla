package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.ISize;

public class Size implements ISize {

    private int width;
    private int height;

    public Size(int width, int height) {
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

    @Override
    public ISize w(int width) {
        this.width = width;
        return this;
    }

    @Override
    public ISize h(int height) {
        this.height = height;
        return this;
    }
}
