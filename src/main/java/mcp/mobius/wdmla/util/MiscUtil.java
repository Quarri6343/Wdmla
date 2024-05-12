package mcp.mobius.wdmla.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public enum MiscUtil {
    INSTANCE;

    public static Dimension displaySize() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        return new Dimension(res.getScaledWidth(), res.getScaledHeight());
    }

    public static int applyAlpha(int color, float alpha) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        int appliedAlpha = (int) (alpha * 255); // アルファ値を0〜255の範囲に変換
        return (appliedAlpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
