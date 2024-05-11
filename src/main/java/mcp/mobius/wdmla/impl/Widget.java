package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.*;

public class Widget implements IWidget {

    // settings
    protected IPadding padding;
    protected ISize size;

    // render
    protected final IDrawable foreGround;

    public Widget(IDrawable foreGround) {
        this.foreGround = foreGround;
    }

    @Override
    public IWidget padding(IPadding padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public IWidget size(ISize size) {
        this.size = size;
        return this;
    }

    @Override
    public void tick(int x, int y) {
        foreGround.draw(x * padding.getLeftPadding(), y + padding.getRightPadding());
    }

    @Override
    public int getWidth() {
        if (padding != null) {
            return padding.getLeftPadding() + size.getW() + padding.getRightPadding();
        } else {
            return size.getW();
        }
    }

    @Override
    public int getHeight() {
        if (padding != null) {
            return padding.getTopPadding() + size.getH() + padding.getBottomPadding();
        } else {
            return size.getH();
        }
    }
}
