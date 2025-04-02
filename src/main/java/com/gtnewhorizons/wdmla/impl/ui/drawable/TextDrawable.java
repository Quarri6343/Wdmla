package com.gtnewhorizons.wdmla.impl.ui.drawable;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.api.ui.style.ITextStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;

public class TextDrawable implements IDrawable {

    private final @NotNull String text;
    private @NotNull ITextStyle style;
    private float scale;

    public TextDrawable(@NotNull String text) {
        this.text = text;
        this.style = new TextStyle();
        this.scale = 1;
    }

    public TextDrawable style(ITextStyle style) {
        this.style = style;
        return this;
    }

    public TextDrawable scale(float scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.drawString(text, area.getX(), area.getY(), style.getColor(), style.getShadow(), scale);
    }
}
