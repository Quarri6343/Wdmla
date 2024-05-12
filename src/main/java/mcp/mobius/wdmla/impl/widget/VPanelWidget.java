package mcp.mobius.wdmla.impl.widget;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.setting.Area;

/**
 * A Vertical layout panel. Size depends on children.
 */
public class VPanelWidget extends PanelWidget {

    public VPanelWidget() {
        super();
    }

    protected VPanelWidget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground,
            IPanelStyle style) {
        super(children, padding, size, foreground, style);
    }

    @Override
    public void tick(int x, int y) {
        int totWidth = this.getWidth();
        y += padding.getTop();
        for (int i = 0; i < children.size(); i++) {
            int w = children.get(i).getWidth();
            int cx = x;
            switch (style.getAlignment()) {
                case TOPLEFT:
                    cx = x + padding.getLeft();
                    break;
                case CENTER:
                    cx = x + (totWidth - w) / 2;
                    break;
                case BOTTOMRIGHT:
                    cx = x + totWidth - w - padding.getRight();
                default:
                    break;
            }

            children.get(i).tick(cx, y);

            if (i < children.size() - 1) {
                y += children.get(i).getHeight() + style.getSpacing();
            }
        }

        foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IHUDWidget child : children) {
            int ww = child.getWidth();
            if (ww > w) {
                w = ww;
            }
        }

        return padding.getLeft() + w + padding.getRight();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IHUDWidget child : children) {
            h += child.getHeight();
        }

        return padding.getTop() + h + style.getSpacing() * (children.size() - 1) + padding.getBottom();
    }

    @Override
    public VPanelWidget child(@NotNull IHUDWidget child) {
        List<IHUDWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new VPanelWidget(newChildren, padding, size, foreground, style);
    }

    @Override
    public PanelWidget size(@NotNull ISize size) {
        throw new IllegalArgumentException("Vertical Panel is auto sized.");
    }
}
