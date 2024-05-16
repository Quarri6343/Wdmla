package mcp.mobius.wdmla.impl.widget;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.wdmla.api.sizer.IPadding;
import mcp.mobius.wdmla.api.sizer.ISize;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.drawable.ProgressDrawable;
import mcp.mobius.wdmla.impl.values.Progress;
import mcp.mobius.wdmla.impl.values.sizer.Padding;
import mcp.mobius.wdmla.impl.values.sizer.Size;

public class ProgressWidget extends Widget {

    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    public ProgressWidget(int current, int max) {
        super(
                new ArrayList<>(),
                new Padding(),
                new Size(DEFAULT_W, DEFAULT_H),
                new ProgressDrawable(new Progress(current, max)));
    }

    private ProgressWidget(List<IWidget> children, IPadding padding, ISize size, IDrawable foreground) {
        super(children, padding, size, foreground);
    }

    public ProgressWidget style(IProgressStyle style) {
        return new ProgressWidget(children, padding, size, ((ProgressDrawable) foreground).style(style));
    }

    @Override
    public ProgressWidget padding(@NotNull IPadding padding) {
        return new ProgressWidget(children, padding, size, foreground);
    }

    @Override
    public ProgressWidget size(@NotNull ISize size) {
        return new ProgressWidget(children, padding, size, foreground);
    }
}
