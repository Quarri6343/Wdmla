package mcp.mobius.waila.network;

import com.gtnewhorizons.wdmla.impl.BlockAccessorImpl;
import com.gtnewhorizons.wdmla.wailacompat.TERequestCompat;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.HashSet;

//TODO: split packet for WDMla and Waila
public class Message0x01TERequest extends SimpleChannelInboundHandler<Message0x01TERequest> implements IWailaMessage {

    public int dim;
    public int posX;
    public int posY;
    public int posZ;
    public HashSet<String> keys = new HashSet<>();

    public Message0x01TERequest() {}

    public Message0x01TERequest(TileEntity ent, HashSet<String> keys) {
        this.dim = ent.getWorldObj().provider.dimensionId;
        this.posX = ent.xCoord;
        this.posY = ent.yCoord;
        this.posZ = ent.zCoord;
        this.keys = keys;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target) throws Exception {
        target.writeInt(dim);
        target.writeInt(posX);
        target.writeInt(posY);
        target.writeInt(posZ);
        target.writeInt(this.keys.size());

        for (String key : keys) WailaPacketHandler.INSTANCE.writeString(target, key);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage rawmsg) {
        try {
            Message0x01TERequest msg = (Message0x01TERequest) rawmsg;
            msg.dim = dat.readInt();
            msg.posX = dat.readInt();
            msg.posY = dat.readInt();
            msg.posZ = dat.readInt();

            int nkeys = dat.readInt();

            for (int i = 0; i < nkeys; i++) this.keys.add(WailaPacketHandler.INSTANCE.readString(dat));

        } catch (Exception e) {
            WailaExceptionHandler.handleErr(e, this.getClass().toString(), null);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message0x01TERequest msg) throws Exception {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("x", msg.posX);
        tag.setInteger("y", msg.posY);
        tag.setInteger("z", msg.posZ);

        BlockAccessorImpl.handleRequest(ctx, tag, msg);
        TERequestCompat.handleRequest(ctx, tag, msg);

        WailaPacketHandler.INSTANCE.sendTo(new Message0x02TENBTData(tag), WailaPacketHandler.getPlayer(ctx));
    }
}
