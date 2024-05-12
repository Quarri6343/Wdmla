package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.drawable.ProgressDrawable;
import mcp.mobius.wdmla.impl.setting.Padding;
import mcp.mobius.wdmla.impl.setting.Progress;
import mcp.mobius.wdmla.impl.setting.ProgressStyle;
import mcp.mobius.wdmla.impl.setting.Size;

import java.util.ArrayList;
import java.util.List;

public class ProgressWidget extends Widget {
    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    public ProgressWidget(int current, int max) {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new ProgressDrawable(new Progress(current, max)));
    }

    private ProgressWidget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground) {
        super(children, padding, size, foreground);
    }

    public ProgressWidget style(IProgressStyle style) {
        return new ProgressWidget(children, padding, size, ((ProgressDrawable) foreground).style(style));
    }

    @Override
    public ProgressWidget padding(IPadding padding) {
        return new ProgressWidget(children, padding, size, foreground);
    }

    @Override
    public ProgressWidget size(ISize size) {
        return new ProgressWidget(children, padding, size, foreground);
    }
}
