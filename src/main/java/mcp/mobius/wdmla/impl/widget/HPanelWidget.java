package mcp.mobius.wdmla.impl.widget;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.wdmla.api.sizer.IPadding;
import mcp.mobius.wdmla.api.sizer.ISize;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.values.sizer.Area;

/**
 * A Horizontal layout panel. Size depends on children.
 */
public class HPanelWidget extends PanelWidget {

    public HPanelWidget() {
        super();
    }

    protected HPanelWidget(List<IWidget> children, IPadding padding, ISize size, IDrawable foreground,
                           IPanelStyle style) {
        super(children, padding, size, foreground, style);
    }

    public void tick(int x, int y) {
        int totHeight = this.getHeight();
        x += padding.getLeft();
        for (int i = 0; i < children.size(); i++) {
            int h = children.get(i).getHeight();
            int cy = y;
            switch (style.getAlignment()) {
                case TOPLEFT:
                    cy = y + padding.getTop();
                    break;
                case CENTER:
                    cy = y + (totHeight - h) / 2;
                    break;
                case BOTTOMRIGHT:
                    cy = y + totHeight - h - padding.getBottom();
                default:
                    break;
            }

            children.get(i).tick(x, cy);

            if (i < children.size() - 1) {
                x += children.get(i).getWidth() + style.getSpacing();
            }
        }

        foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IWidget child : children) {
            w += child.getWidth();
        }

        return padding.getLeft() + w + style.getSpacing() * (children.size() - 1) + padding.getRight();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IWidget child : children) {
            int ww = child.getHeight();
            if (ww > h) {
                h = ww;
            }
        }

        return padding.getTop() + h + padding.getBottom();
    }

    @Override
    public HPanelWidget child(@NotNull IWidget child) {
        List<IWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new HPanelWidget(newChildren, padding, size, foreground, style);
    }

    @Override
    public PanelWidget size(@NotNull ISize size) {
        throw new IllegalArgumentException("Horizontal Panel is auto sized.");
    }
}
