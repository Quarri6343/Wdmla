package mcp.mobius.wdmla.impl.value;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.config.Configuration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;

public class ObjectPlayerIsLookingAt {

    private final @Nullable MovingObjectPosition target;
    private final Minecraft mc = Minecraft.getMinecraft();

    public ObjectPlayerIsLookingAt() {
        if (isMcPointingEntity()) {
            target = mc.objectMouseOver;
            return;
        }

        if (mc.renderViewEntity != null) {
            target = this.rayTrace(mc.renderViewEntity, mc.playerController.getBlockReachDistance(), 0).orElse(null);
            return;
        }

        target = null;
    }

    private boolean isMcPointingEntity() {
        return mc.objectMouseOver != null
                && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
                && !shouldHidePlayer(mc.objectMouseOver.entityHit);
    }

    private static boolean shouldHidePlayer(@Nullable Entity targetEnt) {
        // Check if entity is player with invisibility effect
        if (targetEnt instanceof EntityPlayer thePlayer) {
            boolean shouldHidePlayerSetting = !ConfigHandler.instance().getConfig("vanilla.show_invisible_players");
            return shouldHidePlayerSetting && thePlayer.isInvisible();
        }
        return false;
    }

    public boolean isBlock() {
        return target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK;
    }

    public Optional<UnIdentifiedBlockPos> getBlockPos() {
        if (isBlock()) {
            return Optional.of(new UnIdentifiedBlockPos(target.blockX, target.blockY, target.blockZ));
        }

        return Optional.empty();
    }

    // TODO:entity

    public Optional<MovingObjectPosition> rayTrace(@NotNull EntityLivingBase entity, double par1, float par3) {
        Vec3 vec3 = entity.getPosition(par3);
        Vec3 vec31 = entity.getLook(par3);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * par1, vec31.yCoord * par1, vec31.zCoord * par1);

        if (ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_LIQUID, true))
            return Optional.ofNullable(entity.worldObj.rayTraceBlocks(vec3, vec32, true));
        else return Optional.ofNullable(entity.worldObj.rayTraceBlocks(vec3, vec32, false));
    }
}
