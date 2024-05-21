package mcp.mobius.wdmla.impl.ui.component;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;

/**
 * A Vertical layout panel. Size depends on children.
 */
public class VPanelComponent extends PanelComponent {

    public VPanelComponent() {
        super();
    }

    protected VPanelComponent(List<IComponent> children, IPadding padding, ISize size, IDrawable foreground,
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
        for (IComponent child : children) {
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
        for (IComponent child : children) {
            h += child.getHeight();
        }

        int totalSpacing = children.isEmpty() ? 0 : style.getSpacing() * (children.size() - 1);
        return padding.getTop() + h + totalSpacing + padding.getBottom();
    }

    @Override
    public PanelComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("Vertical Panel is auto sized.");
    }
}
