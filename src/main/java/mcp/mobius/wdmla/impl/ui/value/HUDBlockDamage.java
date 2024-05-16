package mcp.mobius.wdmla.impl.ui.value;

import mcp.mobius.wdmla.api.ui.sizer.IArea;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;
import net.minecraft.client.Minecraft;

public class HUDBlockDamage {

    private final float damage;

    public HUDBlockDamage() {
        damage = Minecraft.getMinecraft().playerController.curBlockDamageMP;
    }

    public boolean isIntact() {
        return damage == 0;
    }

    /**
     * get corresponding alpha value of the current block damage for HUD
     */
    public Alpha getAlphaForProgress() {
        float alpha = Math.min(damage, 0.6F) + 0.4F * damage;
        return new Alpha(Math.min(alpha, 1));
    }

    public IArea computeDrawArea(IArea area) {
        int width = (int) ((area.getW() - 1) * damage);
        return new Area(area.getX() + 1, area.getY(), width - 1, 2); //TODO: configure Area via config
    }
}
