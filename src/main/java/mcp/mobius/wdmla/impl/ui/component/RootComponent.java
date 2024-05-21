package mcp.mobius.wdmla.impl.ui.component;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.impl.ui.drawable.BackgroundDrawable;
import mcp.mobius.wdmla.impl.ui.drawable.BreakProgressDrawable;
import mcp.mobius.wdmla.impl.ui.value.HUDRenderArea;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;
import mcp.mobius.wdmla.util.GLStateHelper;

public final class RootComponent extends VPanelComponent {

    private final @NotNull IDrawable background = new BackgroundDrawable();
    private final @NotNull IDrawable breakProgress = new BreakProgressDrawable();

    public RootComponent() {
        super();
    }

    private RootComponent(List<IComponent> children, IPadding padding, ISize size, IDrawable foreground,
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
}
