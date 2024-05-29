package com.gtnewhorizons.wdmla.impl.ui.sizer;

import net.minecraft.client.Minecraft;

import org.jetbrains.annotations.NotNull;

public class TextSize extends Size {

    protected final @NotNull String text;

    public TextSize(@NotNull String text) {
        super(0, 0);
        this.text = text;
    }

    @Override
    public int getW() {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    @Override
    public int getH() {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }
}
