package mcp.mobius.wdmla.impl.widget;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.waila.utils.Constants;
import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.drawable.BreakProgressDrawable;
import mcp.mobius.wdmla.impl.setting.Area;
import mcp.mobius.wdmla.util.MiscUtil;
import mcp.mobius.wdmla.util.RenderUtil;

public final class RootWidget extends VPanelWidget {

    private final IDrawable breakProgress = new BreakProgressDrawable();
    private static final int MARGIN = 5;
    private Area area;

    public RootWidget() {
        super();
    }

    private RootWidget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground,
            IPanelStyle style) {
        super(children, padding, size, foreground, style);
    }

    public void preTick() {
        area = computeRenderArea();
        RenderUtil.drawBG(area, OverlayConfig.bgcolor, OverlayConfig.gradient1, OverlayConfig.gradient2); // TODO:
                                                                                                          // drawable変換
        breakProgress.draw(area);
    }

    public void tick() {
        int x = area.getX() + MARGIN;
        int y = area.getY() + MARGIN;
        for (IHUDWidget child : children) {
            child.tick(x + padding.getLeft(), y + padding.getTop());
        }
    }

    @Override
    public void tick(int x, int y) {}

    private Area computeRenderArea() {
        Point pos = new Point(
                ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_POSX, 0),
                ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_POSY, 0));

        int w = getWidth() + MARGIN * 2;
        int h = getHeight() + MARGIN * 2;

        Dimension size = MiscUtil.displaySize();
        int x = ((int) (size.width / OverlayConfig.scale) - w - 1) * pos.x / 10000;
        int y = ((int) (size.height / OverlayConfig.scale) - h - 1) * pos.y / 10000;

        return new Area(x, y, w, h);
    }

    @Override
    public RootWidget child(@NotNull IHUDWidget child) {
        List<IHUDWidget> newChildren = new ArrayList<>(children);
        newChildren.add(child);
        return new RootWidget(newChildren, padding, size, foreground, style);
    }
}
