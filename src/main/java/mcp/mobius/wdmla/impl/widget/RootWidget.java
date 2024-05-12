package mcp.mobius.wdmla.impl.widget;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.drawable.BreakProgressDrawable;
import mcp.mobius.wdmla.impl.values.ScreenRenderArea;
import mcp.mobius.wdmla.impl.values.sizer.Area;
import mcp.mobius.wdmla.impl.values.sizer.Size;
import mcp.mobius.wdmla.util.HUDGLSaver;
import mcp.mobius.wdmla.util.RenderUtil;

public final class RootWidget extends VPanelWidget {

    private final @NotNull HUDGLSaver glSaver = new HUDGLSaver();
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

        GL11.glPushMatrix();
        glSaver.save();

        GL11.glScalef(OverlayConfig.scale, OverlayConfig.scale, 1.0f);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Area bgArea = new ScreenRenderArea(new Size(getWidth(), getHeight())).computeBackground();
        RenderUtil.drawBG(bgArea, OverlayConfig.bgcolor, OverlayConfig.gradient1, OverlayConfig.gradient2);
        breakProgress.draw(bgArea);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Area fgArea = new ScreenRenderArea(new Size(getWidth(), getHeight())).computeForeground();
        for (IHUDWidget child : children) {
            child.tick(fgArea.getX() + padding.getLeft(), fgArea.getY() + padding.getTop());
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        glSaver.load();
        GL11.glPopMatrix();
    }

    private static boolean canShowOverlay() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.currentScreen == null && mc.theWorld != null
                && Minecraft.isGuiEnabled()
                && !mc.gameSettings.keyBindPlayerList.getIsKeyPressed()
                && ConfigHandler.instance().showTooltip();
    }

    @Override
    public void tick(int x, int y) {}

    @Override
    public RootWidget child(@NotNull IHUDWidget child) {
        List<IHUDWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new RootWidget(newChildren, padding, size, foreground, style);
    }
}
