package mcp.mobius.wdmla.impl;

import net.minecraft.client.Minecraft;

public class TextSize extends Size {

    protected final String text;

    public TextSize(String text) {
        super(0,0);
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
