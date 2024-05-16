package mcp.mobius.wdmla.impl.values;

import mcp.mobius.wdmla.api.sizer.IArea;
import mcp.mobius.wdmla.impl.values.sizer.Area;
import net.minecraft.client.Minecraft;

public class BlockDamage {

    private final float damage;

    public BlockDamage() {
        damage = Minecraft.getMinecraft().playerController.curBlockDamageMP;
    }

    public boolean isIntact() {
        return damage == 0;
    }

    /**
     * get corresponding alpha value of the current block damage for HUD
     */
    public Alpha getAlphaForProgressHUD() {
        float alpha = Math.min(damage, 0.6F) + 0.4F * damage;
        return new Alpha(Math.min(alpha, 1));
    }

    public IArea computeDrawAreaOnHUD(IArea area) {
        int width = (int) ((area.getW() - 1) * damage);
        return new Area(area.getX() + 1, area.getY(), width - 1, 2); //TODO: configure Area via config
    }
}
