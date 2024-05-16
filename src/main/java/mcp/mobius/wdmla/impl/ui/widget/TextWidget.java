package mcp.mobius.wdmla.impl.ui.widget;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.IWidget;
import mcp.mobius.wdmla.impl.ui.drawable.TextDrawable;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.impl.ui.value.setting.TextStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;
import mcp.mobius.wdmla.impl.ui.value.sizer.Padding;
import mcp.mobius.wdmla.impl.ui.value.sizer.TextSize;

public class TextWidget extends Widget {

    protected final ITextStyle style;

    public TextWidget(String text) {
        super(new ArrayList<>(), new Padding(), new TextSize(text), new TextDrawable(text));
        this.style = new TextStyle();
    }

    private TextWidget(List<IWidget> children, IPadding padding, ISize textSize, IDrawable foreground,
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
