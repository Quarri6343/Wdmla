package mcp.mobius.wdmla.impl.ui.drawable;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.IProgress;
import mcp.mobius.wdmla.api.ui.sizer.IArea;
import mcp.mobius.wdmla.api.ui.style.IProgressStyle;
import mcp.mobius.wdmla.impl.ui.style.ProgressStyle;
import mcp.mobius.wdmla.impl.ui.sizer.Area;

public class ProgressDrawable implements IDrawable {

    private final @NotNull IProgress progress;
    private @NotNull IProgressStyle style;

    public ProgressDrawable(@NotNull IProgress progress) {
        this.progress = progress;
        this.style = new ProgressStyle();
    }

    public ProgressDrawable style(IProgressStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public void draw(IArea area) {
        long current = progress.getCurrent();
        long max = progress.getMax();
        GuiDraw.drawThickBeveledBox(
                area,
                1,
                style.getBorderColor(),
                style.getBorderColor(),
                style.getBackgroundColor());
        if (current > 0L && max > 0L) {
            int dx = (int) Math.min(current * (long) (area.getW() - 2) / max, (long) (area.getW() - 2));
            if (style.getFilledColor() == style.getAlternateFilledColor()) {
                if (dx > 0) {
                    GuiDraw.drawThickBeveledBox(
                            new Area(area.getX() + 1, area.getY() + 1, dx + 1, area.getH() - 1),
                            1,
                            style.getFilledColor(),
                            style.getFilledColor(),
                            style.getFilledColor());
                }
            } else {
                for (int xx = area.getX() + 1; xx <= area.getX() + dx + 1; ++xx) {
                    int color = (xx & 1) == 0 ? style.getFilledColor() : style.getAlternateFilledColor();
                    GuiDraw.drawVerticalLine(xx, area.getY() + 1, area.getEY() - 1, color);
                }
            }
        }
    }
}
