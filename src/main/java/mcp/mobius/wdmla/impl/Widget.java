package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.*;

import java.util.ArrayList;
import java.util.List;

public class Widget implements IHUDWidget {

    protected final List<IHUDWidget> children;

    // settings
    protected final IPadding padding;
    protected final ISize size;

    // render
    protected final IDrawable foreGround;

    protected Widget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreGround) {
        this.children = children;
        this.padding = padding;
        this.size = size;
        this.foreGround = foreGround;
    }

    @Override
    public IHUDWidget padding(IPadding padding) {
        return new Widget(this.children, padding, size, foreGround);
    }

    @Override
    public IHUDWidget size(ISize size) {
        return new Widget(this.children, padding, size, foreGround);
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
        List<IHUDWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new Widget(newChildren, padding, size, foreGround);
    }
}
