package mcp.mobius.wdmla.impl;

import net.minecraft.client.Minecraft;

import mcp.mobius.wdmla.api.ISize;

public class TextSize extends Size {

    protected final String text;

    public TextSize(String text) {
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

    @Override
    public ISize w(int width) {
        return this;
    }

    @Override
    public ISize h(int height) {
        return this;
    }
}
