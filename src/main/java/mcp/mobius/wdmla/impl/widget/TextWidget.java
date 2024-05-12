package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.*;
import mcp.mobius.wdmla.impl.drawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

public class TextWidget extends Widget {

    protected final ITextStyle style;

    public TextWidget(String text) {
        super(new ArrayList<>(), new Padding(), new TextSize(text), new TextDrawable(text));
        this.style = new TextStyle();
        ((TextDrawable) foreGround).style(style);
    }

    private TextWidget(List<IHUDWidget> children, IPadding padding, ISize textSize, IDrawable foreground, ITextStyle style) {
        super(children, padding, textSize, foreground);
        this.style = style;
        ((TextDrawable) foreGround).style(style);
    }

    public TextWidget style(ITextStyle style) {
        return new TextWidget(children, padding, size, foreGround, style);
    }

    @Override
    public TextWidget padding(IPadding padding) {
        return new TextWidget(children, padding, size, foreGround, style);
    }

    @Override
    public TextWidget size(ISize size) {
        throw new IllegalArgumentException("You can't set the size of TextWidget!");
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
