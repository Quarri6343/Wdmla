package mcp.mobius.wdmla.impl.widget;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.setting.Area;

public class Widget implements IHUDWidget {

    protected final List<IHUDWidget> children;

    // settings
    protected final IPadding padding;
    protected final ISize size;

    // render
    protected final IDrawable foreground;

    protected Widget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground) {
        this.children = children;
        this.padding = padding;
        this.size = size;
        this.foreground = foreground;
    }

    public Widget padding(@NotNull IPadding padding) {
        return new Widget(this.children, padding, size, foreground);
    }

    public Widget size(@NotNull ISize size) {
        return new Widget(this.children, padding, size, foreground);
    }

    @Override
    public void tick(int x, int y) {
        for (IHUDWidget child : children) {
            child.tick(x + padding.getLeft(), y + padding.getTop());
        }
        foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        return padding.getLeft() + size.getW() + padding.getRight();
    }

    @Override
    public int getHeight() {
        return padding.getTop() + size.getH() + padding.getBottom();
    }

    public Widget child(@NotNull IHUDWidget child) {
        List<IHUDWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new Widget(newChildren, padding, size, foreground);
    }
}
