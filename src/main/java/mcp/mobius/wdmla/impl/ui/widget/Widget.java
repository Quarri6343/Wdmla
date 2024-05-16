package mcp.mobius.wdmla.impl.ui.widget;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.IWidget;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.impl.ui.value.sizer.Area;

public class Widget implements IWidget {

    protected final List<IWidget> children;

    // settings
    protected final IPadding padding;
    protected final ISize size;

    // render
    protected final IDrawable foreground;

    protected Widget(List<IWidget> children, IPadding padding, ISize size, IDrawable foreground) {
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
        for (IWidget child : children) {
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

    public Widget child(@NotNull IWidget child) {
        List<IWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new Widget(newChildren, padding, size, foreground);
    }
}
