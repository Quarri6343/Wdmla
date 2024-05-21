package mcp.mobius.wdmla.impl.ui.component;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;
import mcp.mobius.wdmla.impl.ui.drawable.TextDrawable;
import mcp.mobius.wdmla.impl.ui.value.setting.TextStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;
import mcp.mobius.wdmla.impl.ui.value.sizer.Padding;
import mcp.mobius.wdmla.impl.ui.value.sizer.TextSize;

public class TextComponent extends Component {

    protected ITextStyle style;

    public TextComponent(String text) {
        super(new ArrayList<>(), new Padding(), new TextSize(text), new TextDrawable(text));
        this.style = new TextStyle();
    }

    public TextComponent style(ITextStyle style) {
        ((TextDrawable) this.foreground).style(style);
        this.style = style;
        return this;
    }

    @Override
    public TextComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("You can't set the size of TextComponent!");
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
