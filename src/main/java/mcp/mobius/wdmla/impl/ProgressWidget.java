package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.*;

public class ProgressWidget extends Widget {

    public ProgressWidget(int current, int max) {
        super(new ProgressDrawable(current, max));
        size(new Size().w(100).h(12));
        style(new ProgressStyle());
    }

    public ProgressWidget style(IProgressStyle style) {
        ((ProgressDrawable) foreGround).style(style);
        return this;
    }

    @Override
    public ProgressWidget size(ISize size) {
        super.size(size);
        ((ProgressDrawable) foreGround).size(size);

        return this;
    }
}
