package mcp.mobius.wdmla.impl.ui.component;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;

public class Component implements IComponent {

    protected List<IComponent> children;

    // settings
    protected IPadding padding;
    protected ISize size;

    // render
    protected IDrawable foreground;

    protected Component(List<IComponent> children, IPadding padding, ISize size, IDrawable foreground) {
        this.children = children;
        this.padding = padding;
        this.size = size;
        this.foreground = foreground;
    }

    public Component padding(@NotNull IPadding padding) {
        this.padding = padding;
        return this;
    }

    public Component size(@NotNull ISize size) {
        this.size = size;
        return this;
    }

    @Override
    public void tick(int x, int y) {
        for (IComponent child : children) {
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

    public Component child(@NotNull IComponent child) {
        this.children.add(child);
        return this;
    }
}
