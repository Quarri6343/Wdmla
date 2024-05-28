package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.api.ui.style.ITextStyle;

public class TextDrawable implements IDrawable {

    private final @NotNull String text;
    private @NotNull ITextStyle style;

    public TextDrawable(@NotNull String text) {
        this.text = text;
        this.style = new TextStyle();
    }

    public TextDrawable style(ITextStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.drawString(text, area.getX(), area.getY(), style.getColor(), style.getShadow());
    }
}
