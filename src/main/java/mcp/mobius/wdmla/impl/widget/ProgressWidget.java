package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.drawable.ProgressDrawable;
import mcp.mobius.wdmla.impl.ProgressStyle;
import mcp.mobius.wdmla.impl.Size;
import mcp.mobius.wdmla.impl.Widget;

public class ProgressWidget extends Widget {
    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    public ProgressWidget(int current, int max) {
        super(new ProgressDrawable(current, max), new Size(DEFAULT_W, DEFAULT_H));
        style(new ProgressStyle());
    }

    public ProgressWidget style(IProgressStyle style) {
        ((ProgressDrawable) foreGround).style(style);
        return this;
    }
}
