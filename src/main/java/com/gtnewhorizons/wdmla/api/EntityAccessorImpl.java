package com.gtnewhorizons.wdmla.api;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;

import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.channel.ChannelHandlerContext;
import mcp.mobius.waila.network.Message0x03EntRequest;

public class EntityAccessorImpl extends AccessorImpl implements EntityAccessor {

    private final Supplier<Entity> entity;

    public EntityAccessorImpl(Builder builder) {
        super(builder.level, builder.player, builder.hit, builder.connected, builder.showDetails, builder.serverData);
        entity = builder.entity;
    }

    public static void handleRequest(ChannelHandlerContext ctx, NBTTagCompound tag, Message0x03EntRequest msg) {
        World world = DimensionManager.getWorld(msg.dim);
        if (world == null) return;
        Entity entity = world.getEntityByID(msg.id);
        if (entity == null) return;
        EntityPlayerMP player = ((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER)
                .get()).playerEntity;

        for (IServerDataProvider<EntityAccessor> provider : WDMlaCommonRegistration.instance()
                .getEntityNBTProviders(entity)) {
            try {
                provider.appendServerData(
                        tag,
                        new EntityAccessorImpl.Builder().level(world).player(player).entity(entity).build());
            } catch (AbstractMethodError | NoSuchMethodError ame) {
                // tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
            }
        }

        tag.setInteger("EntityId", entity.getEntityId());
    }

    @Override
    public Entity getEntity() {
        return entity.get();
    }

    @NotNull
    @Override
    public Object getTarget() {
        return getEntity();
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
