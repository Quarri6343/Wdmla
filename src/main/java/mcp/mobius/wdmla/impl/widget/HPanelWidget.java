package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.IHUDWidget;
import mcp.mobius.wdmla.impl.Area;

public class HPanelWidget extends PanelWidget {

    public void tick(int x, int y) {
        int totHeight = this.getHeight();
        x += padding.getLeftPadding();
        for (int i = 0; i < children.size(); i++) {
            int h = children.get(i).getHeight();
            int cy = y;
            switch (style.getAlignment()) {
                case ALIGN_TOPLEFT:
                    cy = y + padding.getTopPadding();
                    break;
                case ALIGN_CENTER:
                    cy = y + (totHeight - h) / 2;
                    break;
                case ALIGN_BOTTOMRIGHT:
                    cy = y + totHeight - h - padding.getBottomPadding();
                default:
                    break;
            }

            children.get(i).tick(x, cy);

            if(i < children.size() - 1){
                x += children.get(i).getWidth() + style.getSpacing();
            }
        }

        foreGround.draw(new Area(x + padding.getLeftPadding(), y + padding.getTopPadding(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IHUDWidget child : children) {
            w += child.getWidth();
        }

        return padding.getLeftPadding() + w + style.getSpacing() * (children.size() - 1) + padding.getRightPadding();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IHUDWidget child : children) {
            int ww = child.getHeight();
            if(ww > h) {
                h = ww;
            }
        }

        return padding.getTopPadding() + h + padding.getBottomPadding();
    }
}
