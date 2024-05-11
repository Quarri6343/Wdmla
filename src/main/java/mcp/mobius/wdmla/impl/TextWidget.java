package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.*;

public class TextWidget extends Widget {

    // settings
    protected ITextStyle style;

    public TextWidget(String text) {
        super(new TextDrawable(text));
        super.size(new TextSize(text));
        style(new TextStyle());
    }

    public TextWidget style(ITextStyle style) {
        this.style = style;
        ((TextDrawable) foreGround).style(style);
        return this;
    }

    @Override
    public IWidget size(ISize size) {
        return this;
    }

    @Override
    public void tick(int x, int y) {
        int width = size.getW();
        switch (style.getAlignment()) {
            case ALIGN_BOTTOMRIGHT -> foreGround
                    .draw((x + width) + padding.getLeftPadding(), y + padding.getTopPadding());
            case ALIGN_CENTER -> foreGround
                    .draw((x + (width / 2)) + padding.getLeftPadding(), y + padding.getTopPadding());
            case ALIGN_TOPLEFT -> foreGround.draw(x + padding.getLeftPadding(), y + padding.getTopPadding());
        }
    }
}
