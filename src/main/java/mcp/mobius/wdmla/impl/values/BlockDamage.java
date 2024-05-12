package mcp.mobius.wdmla.impl.values;

import net.minecraft.client.Minecraft;

public class BlockDamage {

    private final float damage;

    public BlockDamage() {
        damage = Minecraft.getMinecraft().playerController.curBlockDamageMP;
    }

    public boolean isIntact() {
        return damage == 0;
    }

    public float get() {
        return damage;
    }

    /**
     * get corresponding alpha value of the current block damage for HUD
     */
    public Alpha getAlphaForProgressHUD() {
        return new Alpha(Math.min(damage, 0.6F) + 0.4F * damage);
    }
}
