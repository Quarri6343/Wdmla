package com.gtnewhorizons.wdmla.impl.ui.component;

import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Area;
import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import org.jetbrains.annotations.NotNull;

/**
 * A Horizontal layout panel. Size depends on children.
 */
public class HPanelComponent extends PanelComponent {

    public HPanelComponent() {
        super();
    }

    public void tick(int x, int y) {
        int totHeight = this.getHeight();
        int cx = x + padding.getLeft() + style.getBorderThickness();
        for (int i = 0; i < children.size(); i++) {
            int h = children.get(i).getHeight();
            int cy = y;
            switch (style.getAlignment()) {
                case TOPLEFT:
                    cy = y + padding.getTop() + style.getBorderThickness();
                    break;
                case CENTER:
                    cy = y + (totHeight - h) / 2;
                    break;
                case BOTTOMRIGHT:
                    cy = y + totHeight - h - padding.getBottom() - style.getBorderThickness();
                default:
                    break;
            }

            children.get(i).tick(cx, cy);

            if (i < children.size() - 1) {
                cx += children.get(i).getWidth() + style.getSpacing();
            }
        }

        foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), getWidth(), getHeight()));
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IComponent child : children) {
            w += child.getWidth();
        }

        return padding.getLeft() + w + style.getSpacing() * (children.size() - 1) + padding.getRight() + style.getBorderThickness() * 2;
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

        return padding.getTop() + h + padding.getBottom() + style.getBorderThickness() * 2;
    }

    @Override
    public PanelComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("Horizontal Panel is auto sized.");
    }
}
