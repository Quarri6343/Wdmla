package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.*;
import mcp.mobius.wdmla.impl.drawable.TextDrawable;

public class TextWidget extends Widget {

    // settings
    protected ITextStyle style;

    public TextWidget(String text) {
        super(new TextDrawable(text), new TextSize(text));
        style(new TextStyle());
    }

    public TextWidget style(ITextStyle style) {
        this.style = style;
        ((TextDrawable) foreGround).style(style);
        return this;
    }

    @Override
    public IHUDWidget size(ISize size) {
        if(size instanceof TextSize) {
            this.size = size;
        }

        return this;
    }

    @Override
    public void tick(int x, int y) {
        int width = size.getW();
        int height = size.getH();
        switch (style.getAlignment()) {
            case ALIGN_BOTTOMRIGHT -> foreGround
                    .draw(new Area((x + width) + padding.getLeftPadding(), y + padding.getTopPadding(), width, height));
            case ALIGN_CENTER -> foreGround
                    .draw(new Area((x + (width / 2)) + padding.getLeftPadding(), y + padding.getTopPadding(), width, height));
            case ALIGN_TOPLEFT -> foreGround.draw(new Area(x + padding.getLeftPadding(), y + padding.getTopPadding(), width, height));
        }
    }
}
