package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.*;

import java.util.ArrayList;
import java.util.List;

public class Widget implements IHUDWidget {

    protected List<IHUDWidget> children = new ArrayList();

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
    public IHUDWidget padding(IPadding padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public IHUDWidget size(ISize size) {
        this.size = size;
        return this;
    }

    @Override
    public void tick(int x, int y) {
        for (IHUDWidget child : children) {
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

    @Override
    public IHUDWidget child(IHUDWidget child) {
        this.children.add(child);
        return this;
    }
}
