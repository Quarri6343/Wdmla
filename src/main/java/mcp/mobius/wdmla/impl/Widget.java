package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.*;

import java.util.ArrayList;
import java.util.List;

public class Widget implements IWidget {

    protected List<IWidget> children = new ArrayList();

    // settings
    protected IPadding padding;
    protected ISize size;

    // render
    protected final IDrawable foreGround;

    public Widget(IDrawable foreGround, ISize size) {
        this.foreGround = foreGround;
        padding(new Padding());
        size(size);
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
        for (IWidget child : children) {
            child.tick(x + padding.getLeftPadding(), y + padding.getTopPadding());
        }
        foreGround.draw(new Area(x + padding.getLeftPadding(), y + padding.getTopPadding(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        return padding.getLeftPadding() + size.getW() + padding.getRightPadding();
    }

    @Override
    public int getHeight() {
        return padding.getTopPadding() + size.getH() + padding.getBottomPadding();
    }

    public IWidget child(IWidget child) {
        this.children.add(child);
        return this;
    }
}
