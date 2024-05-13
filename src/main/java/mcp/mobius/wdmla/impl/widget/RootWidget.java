package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.drawable.BreakProgressDrawable;
import mcp.mobius.wdmla.impl.values.ScreenRenderArea;
import mcp.mobius.wdmla.impl.values.sizer.Area;
import mcp.mobius.wdmla.impl.values.sizer.Size;
import mcp.mobius.wdmla.util.GLStateHelper;
import mcp.mobius.wdmla.util.RenderUtil;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class RootWidget extends VPanelWidget {

    private final @NotNull IDrawable breakProgress = new BreakProgressDrawable();

    public RootWidget() {
        super();
    }

    private RootWidget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground,
            IPanelStyle style) {
        super(children, padding, size, foreground, style);
    }

    public void renderHUD() {
        if (!canShowOverlay()) {
            return;
        }

        GLStateHelper.prepareBGDraw();

        Area bgArea = new ScreenRenderArea(new Size(getWidth(), getHeight())).computeBackground();
        RenderUtil.drawBG(bgArea, OverlayConfig.bgcolor, OverlayConfig.gradient1, OverlayConfig.gradient2);
        breakProgress.draw(bgArea);

        GLStateHelper.prepareFGDraw();

        Area fgArea = new ScreenRenderArea(new Size(getWidth(), getHeight())).computeForeground();
        tick(fgArea.getX(), fgArea.getY());

        GLStateHelper.endDraw();
    }

    private static boolean canShowOverlay() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.currentScreen == null && mc.theWorld != null
                && Minecraft.isGuiEnabled()
                && !mc.gameSettings.keyBindPlayerList.getIsKeyPressed()
                && ConfigHandler.instance().showTooltip();
    }

    @Override
    public RootWidget child(@NotNull IHUDWidget child) {
        List<IHUDWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new RootWidget(newChildren, padding, size, foreground, style);
    }
}
