package com.gtnewhorizons.wdmla.impl.ui.sizer;

import net.minecraft.client.Minecraft;

import org.jetbrains.annotations.NotNull;

public class TextSize extends Size {

    protected final @NotNull String text;
    protected float scale;

    public TextSize(@NotNull String text) {
        super(0, 0);
        this.text = text;
        this.scale = 1;
    }

    public TextSize scale(float scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public int getW() {
        return (int) Math.ceil(Minecraft.getMinecraft().fontRenderer.getStringWidth(text) * scale);
    }

    @Override
    public int getH() {
        return (int) Math.ceil(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * scale);
    }

    public double getScale() {
        return scale;
    }
}
