package mcp.mobius.wdmla.impl.drawable;

import net.minecraft.client.Minecraft;

import mcp.mobius.wdmla.api.IArea;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.util.MiscUtil;
import mcp.mobius.wdmla.util.RenderUtil;

public class BreakProgressDrawable implements IDrawable {

    private static final int DEFAULT_COLOR = 0xFFA0A0A0;
    private static final int FAILURE_COLOR = 0xFFAA0000;

    private static float progressAlpha = 0;
    private static long lastMilliSecond;
    private static float savedProgress = 0;

    @Override
    public void draw(IArea area) {
        float elapsedSecond = (float) (System.currentTimeMillis() - lastMilliSecond) / 1000;
        float damage = Minecraft.getMinecraft().playerController.curBlockDamageMP;
        if (damage == 0 && progressAlpha <= 0) {
            lastMilliSecond = System.currentTimeMillis();
            return;
        }

        int drawX = area.getX() + 1;
        int drawY = area.getY(); // TODO: configurable top / bottom drawing

        if (damage > 0) {
            progressAlpha = Math.min(damage, 0.6F);
            progressAlpha += 0.4F * damage;
            savedProgress = damage;
        } else {
            progressAlpha -= elapsedSecond;
        }

        int color = MiscUtil.applyAlpha(DEFAULT_COLOR, progressAlpha); // TODO: change color with harvestability
        int width = (int) ((area.getW() - 1) * savedProgress);
        RenderUtil.drawGradientRect(drawX, drawY, width - 1, 2, color, color);
        lastMilliSecond = System.currentTimeMillis();
    }
}
