package com.gtnewhorizons.wdmla.api.accessor;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.jetbrains.annotations.ApiStatus;

public interface EntityAccessor extends Accessor {

    Entity getEntity();

    @Override
    default Class<? extends Accessor> getAccessorType() {
        return EntityAccessor.class;
    }

    @ApiStatus.NonExtendable
    interface Builder {

        Builder level(World level);

        Builder player(EntityPlayer player);

        Builder serverData(NBTTagCompound serverData);

        Builder serverConnected(boolean connected);

        Builder showDetails(boolean showDetails);

        Builder hit(MovingObjectPosition hit);

        default Builder entity(Entity entity) {
            return entity(() -> entity);
        }

        Builder entity(Supplier<Entity> entity);

        Builder requireVerification();

        EntityAccessor build();
    }
}
