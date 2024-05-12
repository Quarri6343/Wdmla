package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.*;
import mcp.mobius.wdmla.impl.drawable.ProgressDrawable;
import mcp.mobius.wdmla.impl.drawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

public class ProgressWidget extends Widget {
    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    private final IProgressStyle style;

    public ProgressWidget(int current, int max) {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new ProgressDrawable(current, max));
        this.style = new ProgressStyle();
        ((ProgressDrawable) foreGround).style(style);
    }

    private ProgressWidget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground, IProgressStyle style) {
        super(children, padding, size, foreground);
        this.style = style;
        ((ProgressDrawable) foreGround).style(style);
    }

    public ProgressWidget style(IProgressStyle style) {
        return new ProgressWidget(children, padding, size, foreGround, style);
    }

    @Override
    public ProgressWidget padding(IPadding padding) {
        return new ProgressWidget(children, padding, size, foreGround, style);
    }

    @Override
    public ProgressWidget size(ISize size) {
        return new ProgressWidget(children, padding, size, foreGround, style);
    }
}
