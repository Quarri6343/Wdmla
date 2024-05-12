package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.IWidget;
import mcp.mobius.wdmla.impl.Area;

public class HPanelWidget extends PanelWidget {

    @Override
    public void tick(int x, int y) {
        int totHeight = this.getHeight();
        x += padding.getLeftPadding();
        for (IWidget child : children) {
            int h = child.getHeight();
            int cy = y;
            switch (style.getAlignment()) {
                case ALIGN_TOPLEFT:
                    cy = y + padding.getTopPadding();
                    break;
                case ALIGN_CENTER:
                    cy = y + (totHeight - h) / 2;
                    break;
                case ALIGN_BOTTOMRIGHT:
                    cy = y + totHeight - h - padding.getRightPadding();
                default:
                    break;
            }
            x += style.getSpacing();

            child.tick(x, cy);
        }

        foreGround.draw(new Area(x + padding.getLeftPadding(), y + padding.getTopPadding(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IWidget child : children) {
            w += child.getWidth();
        }

        return padding.getLeftPadding() + w + style.getSpacing() * (children.size() - 1) + padding.getRightPadding();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IWidget child : children) {
            int ww = child.getHeight();
            if(ww > h) {
                h = ww;
            }
        }

        return padding.getTopPadding() + h + padding.getBottomPadding();
    }
}
