package mcp.mobius.wdmla.impl.setting;

import mcp.mobius.wdmla.api.ISize;

public class Size implements ISize {

    private final int width;
    private final int height;

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
