package mcp.mobius.wdmla.impl.ui.value;

import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.Configuration;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.waila.utils.Constants;
import mcp.mobius.wdmla.impl.ui.sizer.Area;
import mcp.mobius.wdmla.impl.ui.sizer.Size;

public class HUDRenderArea {

    private static final int MARGIN = 5;

    private final @NotNull Size hudSize;

    public HUDRenderArea(@NotNull Size hudSize) {
        this.hudSize = hudSize;
    }

    public Area computeBackground() {
        Point pos = new Point(
                ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_POSX, 0),
                ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_POSY, 0));

        int w = hudSize.getW() + MARGIN * 2;
        int h = hudSize.getH() + MARGIN * 2;

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();
        int x = ((int) (width / OverlayConfig.scale) - w - 1) * pos.x / 10000;
        int y = ((int) (height / OverlayConfig.scale) - h - 1) * pos.y / 10000;

        return new Area(x, y, w, h);
    }

    public Area computeForeground() {
        Area bg = computeBackground();
        return new Area(bg.getX() + MARGIN, bg.getY() + MARGIN, bg.getW(), bg.getH());
    }
}
