package com.gtnewhorizons.wdmla.api.accessor;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;

import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.channel.ChannelHandlerContext;
import mcp.mobius.waila.network.Message0x01TERequest;

@ApiStatus.Internal
public class BlockAccessorImpl extends AccessorImpl implements BlockAccessor {

    private final Block block;
    private final @Nullable Supplier<TileEntity> tileEntity;
    private final int metadata;
    private final ItemStack itemForm;

    public BlockAccessorImpl(Builder builder) {
        super(builder.level, builder.player, builder.hit, builder.connected, builder.showDetails, builder.serverData);
        this.block = builder.block;
        this.tileEntity = builder.tileEntity;
        this.metadata = builder.metadata;
        this.itemForm = builder.itemForm;
    }

    public static void handleRequest(ChannelHandlerContext ctx, NBTTagCompound tag, Message0x01TERequest msg) {
        World world = DimensionManager.getWorld(msg.dim);
        TileEntity entity = world.getTileEntity(msg.posX, msg.posY, msg.posZ);
        Block block = world.getBlock(msg.posX, msg.posY, msg.posZ);
        int meta = world.getBlockMetadata(msg.posX, msg.posY, msg.posZ);
        EntityPlayerMP player = ((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER)
                .get()).playerEntity;

        for (IServerDataProvider<BlockAccessor> provider : WDMlaCommonRegistration.instance()
                .getBlockNBTProviders(block, entity)) {
            try {
                provider.appendServerData(
                        tag,
                        new BlockAccessorImpl.Builder().level(world).player(player).block(block).tileEntity(entity)
                                .meta(meta).build());
            } catch (AbstractMethodError | NoSuchMethodError ame) {
                // tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
            }
        }

    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public @Nullable TileEntity getTileEntity() {
        return tileEntity == null ? null : tileEntity.get();
    }

    @Nullable
    @Override
    public Object getTarget() {
        return getTileEntity();
    }

    @Override
    public int getMetadata() {
        return metadata;
    }

    @Override
    public ItemStack getItemForm() {
        return itemForm;
    }

    @Override
    public boolean verifyData(NBTTagCompound data) {
        if (!verify) {
            return true;
        }
        int x = data.getInteger("x");
        int y = data.getInteger("y");
        int z = data.getInteger("z");
        MovingObjectPosition hitPos = getHitResult();
        return x == hitPos.blockX && y == hitPos.blockY && z == hitPos.blockZ;
    }

    public static class Builder implements BlockAccessor.Builder {

        private World level;
        private EntityPlayer player;
        private NBTTagCompound serverData;
        private boolean connected;
        private boolean showDetails;
        private MovingObjectPosition hit;
        private Block block = Blocks.air;
        private Supplier<TileEntity> tileEntity;
        private int metadata;
        private ItemStack itemForm;
        private boolean verify;

        @Override
        public Builder level(World level) {
            this.level = level;
            return this;
        }

        @Override
        public Builder player(EntityPlayer player) {
            this.player = player;
            return this;
        }

        @Override
        public Builder serverData(NBTTagCompound serverData) {
            this.serverData = serverData;
            return this;
        }

        @Override
        public Builder serverConnected(boolean connected) {
            this.connected = connected;
            return this;
        }

        @Override
        public Builder showDetails(boolean showDetails) {
            this.showDetails = showDetails;
            return this;
        }

        @Override
        public Builder hit(MovingObjectPosition hit) {
            this.hit = hit;
            return this;
        }

        @Override
        public Builder block(Block block) {
            this.block = block;
            return this;
        }

        @Override
        public BlockAccessor.Builder itemForm(ItemStack itemStack) {
            this.itemForm = itemStack;
            return this;
        }

        @Override
        public Builder tileEntity(Supplier<TileEntity> tileEntity) {
            this.tileEntity = tileEntity;
            return this;
        }

        @Override
        public Builder meta(int metaData) {
            this.metadata = metaData;
            return this;
        }

        @Override
        public Builder requireVerification() {
            verify = true;
            return this;
        }

        @Override
        public BlockAccessor build() {
            BlockAccessorImpl accessor = new BlockAccessorImpl(this);
            if (verify) {
                accessor.requireVerification();
            }
            return accessor;
        }
    }
}
