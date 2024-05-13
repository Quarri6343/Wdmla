package mcp.mobius.wdmla.impl.drawable;

import mcp.mobius.wdmla.impl.values.sizer.Area;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.IArea;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.impl.values.Alpha;
import mcp.mobius.wdmla.impl.values.BlockDamage;
import mcp.mobius.wdmla.util.RenderUtil;

public class BreakProgressDrawable implements IDrawable {

    private static final int DEFAULT_COLOR = 0xFFA0A0A0;
    private static final int FAILURE_COLOR = 0xFFAA0000;

    private static @NotNull Alpha progressAlpha = new Alpha(0);
    private static @NotNull BlockDamage savedDamage = new BlockDamage();

    @Override
    public void draw(IArea area) {
        BlockDamage damage = new BlockDamage();
        if (damage.isIntact() && progressAlpha.isTransparent()) {
            return;
        }

        if (!damage.isIntact()) {
            progressAlpha = damage.getAlphaForProgressHUD();
            savedDamage = damage;
        } else {
            progressAlpha = progressAlpha.fade();
        }

        int drawX = area.getX() + 1;
        int drawY = area.getY(); // TODO: configurable top / bottom drawing

        int color = progressAlpha.apply(DEFAULT_COLOR); // TODO: change color with harvestability
        int width = (int) ((area.getW() - 1) * savedDamage.get());
        RenderUtil.drawGradientRect(new Area(drawX, drawY, width - 1, 2), color, color);
    }
}
