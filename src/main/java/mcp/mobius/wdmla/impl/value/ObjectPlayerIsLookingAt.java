package mcp.mobius.wdmla.impl.value;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.config.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ObjectPlayerIsLookingAt {

    private final Optional<MovingObjectPosition> target;
    private final Minecraft mc = Minecraft.getMinecraft();

    public ObjectPlayerIsLookingAt() {
        if (isMcPointingEntity()) {
            target = Optional.of(mc.objectMouseOver);
            return;
        }

        target = Optional.ofNullable(mc.renderViewEntity)
                .flatMap(viewPoint ->
                    this.rayTrace(viewPoint, mc.playerController.getBlockReachDistance(), 0)
                );
    }

    private boolean isMcPointingEntity() {
        return mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
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
        return target.map(target -> target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                .orElse(false);
    }

    public Optional<UnIdentifiedBlockPos> getBlockPos() {
        if(isBlock()) {
            return target.map(target -> new UnIdentifiedBlockPos(target.blockX, target.blockY, target.blockZ));
        }

        return Optional.empty();
    }

    //TODO:entity

    public Optional<MovingObjectPosition> rayTrace(@NotNull EntityLivingBase entity, double par1, float par3) {
        Vec3 vec3 = entity.getPosition(par3);
        Vec3 vec31 = entity.getLook(par3);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * par1, vec31.yCoord * par1, vec31.zCoord * par1);

        if (ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_LIQUID, true))
            return Optional.of(entity.worldObj.rayTraceBlocks(vec3, vec32, true));
        else return Optional.of(entity.worldObj.rayTraceBlocks(vec3, vec32, false));
    }
}
