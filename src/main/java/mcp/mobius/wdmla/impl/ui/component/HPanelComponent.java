package mcp.mobius.wdmla.impl.ui.component;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.IComponent;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.impl.ui.value.sizer.Area;

/**
 * A Horizontal layout panel. Size depends on children.
 */
public class HPanelComponent extends PanelComponent {

    public HPanelComponent() {
        super();
    }

    protected HPanelComponent(List<IComponent> children, IPadding padding, ISize size, IDrawable foreground,
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
        for (IComponent child : children) {
            w += child.getWidth();
        }

        return padding.getLeft() + w + style.getSpacing() * (children.size() - 1) + padding.getRight();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IComponent child : children) {
            int ww = child.getHeight();
            if (ww > h) {
                h = ww;
            }
        }

        return padding.getTop() + h + padding.getBottom();
    }

    @Override
    public PanelComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("Horizontal Panel is auto sized.");
    }
}
