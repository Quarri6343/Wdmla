package mcp.mobius.wdmla.impl;

import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.IProgressStyle;
import mcp.mobius.wdmla.api.ISize;

public class ProgressDrawable implements IDrawable {

    private final long current;
    private final long max;
    private ISize size;
    private IProgressStyle style;

    public ProgressDrawable(long current, long max) {
        this.current = current;
        this.max = max;
    }

    public ProgressDrawable size(ISize size) {
        this.size = size;
        return this;
    }

    public ProgressDrawable style(IProgressStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public void draw(int x, int y) {
        DisplayUtil.drawThickBeveledBox(
                x,
                y,
                x + size.getW(),
                y + size.getH(),
                1,
                style.getBorderColor(),
                style.getBorderColor(),
                style.getBackgroundColor());
        if (current > 0L && max > 0L) {
            int dx = (int) Math.min(current * (long) (size.getW() - 2) / max, (long) (size.getW() - 2));
            if (style.getFilledColor() == style.getAlternateFilledColor()) {
                if (dx > 0) {
                    DisplayUtil.drawThickBeveledBox(
                            x + 1,
                            y + 1,
                            x + dx + 1,
                            y + size.getH() - 1,
                            1,
                            style.getFilledColor(),
                            style.getFilledColor(),
                            style.getFilledColor());
                }
            } else {
                for (int xx = x + 1; xx <= x + dx + 1; ++xx) {
                    int color = (xx & 1) == 0 ? style.getFilledColor() : style.getAlternateFilledColor();
                    DisplayUtil.drawVerticalLine(xx, y + 1, y + size.getH() - 1, color);
                }
            }
        }
    }
}
