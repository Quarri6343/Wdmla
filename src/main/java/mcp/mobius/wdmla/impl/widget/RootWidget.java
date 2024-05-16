package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.api.sizer.IPadding;
import mcp.mobius.wdmla.api.sizer.ISize;
import mcp.mobius.wdmla.impl.drawable.BackgroundDrawable;
import mcp.mobius.wdmla.impl.drawable.BreakProgressDrawable;
import mcp.mobius.wdmla.impl.values.HUDRenderArea;
import mcp.mobius.wdmla.impl.values.sizer.Area;
import mcp.mobius.wdmla.impl.values.sizer.Size;
import mcp.mobius.wdmla.util.GLStateHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class RootWidget extends VPanelWidget {

    private final @NotNull IDrawable background = new BackgroundDrawable();
    private final @NotNull IDrawable breakProgress = new BreakProgressDrawable();

    public RootWidget() {
        super();
    }

    private RootWidget(List<IWidget> children, IPadding padding, ISize size, IDrawable foreground,
                       IPanelStyle style) {
        super(children, padding, size, foreground, style);
    }

    public void renderHUD() {

        GLStateHelper.prepareBGDraw();

        Area bgArea = new HUDRenderArea(new Size(getWidth(), getHeight())).computeBackground();
        background.draw(bgArea);
        breakProgress.draw(bgArea);

        GLStateHelper.prepareFGDraw();

        Area fgArea = new HUDRenderArea(new Size(getWidth(), getHeight())).computeForeground();
        tick(fgArea.getX(), fgArea.getY());

        GLStateHelper.endDraw();
    }

    @Override
    public RootWidget child(@NotNull IWidget child) {
        List<IWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new RootWidget(newChildren, padding, size, foreground, style);
    }
}
