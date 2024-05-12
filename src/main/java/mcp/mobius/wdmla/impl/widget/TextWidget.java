package mcp.mobius.wdmla.impl.widget;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.drawable.TextDrawable;
import mcp.mobius.wdmla.impl.values.setting.TextStyle;
import mcp.mobius.wdmla.impl.values.sizer.Area;
import mcp.mobius.wdmla.impl.values.sizer.Padding;
import mcp.mobius.wdmla.impl.values.sizer.TextSize;

public class TextWidget extends Widget {

    protected final ITextStyle style;

    public TextWidget(String text) {
        super(new ArrayList<>(), new Padding(), new TextSize(text), new TextDrawable(text));
        this.style = new TextStyle();
    }

    private TextWidget(List<IHUDWidget> children, IPadding padding, ISize textSize, IDrawable foreground,
            ITextStyle style) {
        super(children, padding, textSize, foreground);
        this.style = style;
    }

    public TextWidget style(ITextStyle style) {
        return new TextWidget(children, padding, size, ((TextDrawable) this.foreground).style(style), style);
    }

    @Override
    public TextWidget padding(@NotNull IPadding padding) {
        return new TextWidget(children, padding, size, foreground, style);
    }

    @Override
    public TextWidget size(@NotNull ISize size) {
        throw new IllegalArgumentException("You can't set the size of TextWidget!");
    }

    @Override
    public void tick(int x, int y) {
        int width = size.getW();
        int height = size.getH();
        switch (style.getAlignment()) {
            case BOTTOMRIGHT -> foreground
                    .draw(new Area((x + width) + padding.getLeft(), y + padding.getTop(), width, height));
            case CENTER -> foreground
                    .draw(new Area((x + (width / 2)) + padding.getLeft(), y + padding.getTop(), width, height));
            case TOPLEFT -> foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), width, height));
        }
    }
}
