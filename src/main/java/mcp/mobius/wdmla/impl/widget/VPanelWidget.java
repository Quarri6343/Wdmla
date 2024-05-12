package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.Area;
import mcp.mobius.wdmla.impl.Widget;
import mcp.mobius.wdmla.impl.drawable.BorderDrawable;

import java.util.ArrayList;
import java.util.List;

public class VPanelWidget extends PanelWidget {

    public VPanelWidget(){
        super();
    }

    protected VPanelWidget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground, IPanelStyle style) {
        super(children, padding, size, foreground, style);
    }

    @Override
    public void tick(int x, int y) {
        int totWidth = this.getWidth();
        y += padding.getTopPadding();
        for (int i = 0; i < children.size(); i++) {
            int w = children.get(i).getWidth();
            int cx = x;
            switch (style.getAlignment()) {
                case ALIGN_TOPLEFT:
                    cx = x + padding.getLeftPadding();
                    break;
                case ALIGN_CENTER:
                    cx = x + (totWidth - w) / 2;
                    break;
                case ALIGN_BOTTOMRIGHT:
                    cx = x + totWidth - w - padding.getRightPadding();
                default:
                    break;
            }

            children.get(i).tick(cx, y);

            if(i < children.size() - 1){
                y += children.get(i).getHeight() + style.getSpacing();
            }
        }

        foreGround.draw(new Area(x + padding.getLeftPadding(), y + padding.getTopPadding(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IHUDWidget child : children) {
            int ww = child.getWidth();
            if(ww > w) {
                w = ww;
            }
        }

        return padding.getLeftPadding() + w + padding.getRightPadding();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IHUDWidget child : children) {
            h += child.getHeight();
        }

        return padding.getTopPadding() + h + style.getSpacing() * (children.size() - 1) + padding.getBottomPadding();
    }

    @Override
    public VPanelWidget child(IHUDWidget child) {
        List<IHUDWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new VPanelWidget(newChildren, padding, size, foreGround, style);
    }
}