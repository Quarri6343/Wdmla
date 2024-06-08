package com.gtnewhorizons.wdmla.api;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityAccessorImpl extends AccessorImpl implements EntityAccessor {

    private final Supplier<Entity> entity;

    public EntityAccessorImpl(Builder builder) {
        super(builder.level, builder.player, builder.hit, builder.connected, builder.showDetails, builder.serverData);
        entity = builder.entity;
    }

    @Override
    public Entity getEntity() {
        return entity.get();
    }

    @Override
    public boolean verifyData(NBTTagCompound data) {
        if (!verify) {
            return true;
        }
        if (!data.hasKey("EntityId")) {
            return false;
        }
        return data.getInteger("EntityId") == getEntity().getEntityId();
    }

    public static class Builder implements EntityAccessor.Builder {

        private World level;
        private EntityPlayer player;
        private NBTTagCompound serverData;
        private boolean connected;
        private boolean showDetails;
        private MovingObjectPosition hit;
        private Supplier<Entity> entity;
        private boolean verify;

        @Override
        public EntityAccessor.Builder level(World level) {
            this.level = level;
            return this;
        }

        @Override
        public EntityAccessor.Builder player(EntityPlayer player) {
            this.player = player;
            return this;
        }

        @Override
        public EntityAccessor.Builder serverData(NBTTagCompound serverData) {
            this.serverData = serverData;
            return this;
        }

        @Override
        public EntityAccessor.Builder serverConnected(boolean connected) {
            this.connected = connected;
            return this;
        }

        @Override
        public EntityAccessor.Builder showDetails(boolean showDetails) {
            this.showDetails = showDetails;
            return this;
        }

        @Override
        public EntityAccessor.Builder hit(MovingObjectPosition hit) {
            this.hit = hit;
            return this;
        }

        @Override
        public EntityAccessor.Builder entity(Supplier<Entity> entity) {
            this.entity = entity;
            return this;
        }

        @Override
        public EntityAccessor.Builder requireVerification() {
            this.verify = true;
            return this;
        }

        @Override
        public EntityAccessor build() {
            EntityAccessorImpl accessor = new EntityAccessorImpl(this);
            if (verify) {
                accessor.requireVerification();
            }
            return accessor;
        }
    }
}
