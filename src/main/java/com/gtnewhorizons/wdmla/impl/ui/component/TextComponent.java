package com.gtnewhorizons.wdmla.impl.ui.component;

import mcp.mobius.waila.utils.WailaExceptionHandler;
import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.api.ui.style.ITextStyle;
import com.gtnewhorizons.wdmla.impl.ui.drawable.TextDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Area;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.TextSize;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;

public class TextComponent extends Component {

    protected ITextStyle style;

    public TextComponent(String text) {
        super(new Padding(), new TextSize(text), new TextDrawable(text));
        this.style = new TextStyle();
    }

    public TextComponent style(ITextStyle style) {
        ((TextDrawable) this.foreground).style(style);
        this.style = style;
        return this;
    }

    public TextComponent scale(float scale) {
        ((TextSize)size).scale(scale);
        ((TextDrawable) this.foreground).scale(scale);
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
            default -> WailaExceptionHandler.handleErr(new IllegalArgumentException("invalid text alignment"), this.getClass().getName(), null);
        }
    }
}
