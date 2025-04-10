package mcp.mobius.waila.network;

import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessorImpl;
import com.gtnewhorizons.wdmla.wailacompat.TERequestCompat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mcp.mobius.waila.utils.WailaExceptionHandler;


/**
 * Requests tile entity data to server side.<br>
 * Sent data will be processed by both WDMla {@link BlockAccessorImpl}, and Waila {@link TERequestCompat},
 * and send back as {@link Message0x02TENBTData} <br>
 * Keys field does nothing right now<br>
 * Non tile entity block is not supported yet.<br>
 * TODO: split packet for WDMla and Waila
 */
public class Message0x01TERequest extends SimpleChannelInboundHandler<Message0x01TERequest> implements IWailaMessage {

    /**
     * block dimension id
     */
    public int dim;
    /**
     * block position x
     */
    public int posX;
    /**
     * block position y
     */
    public int posY;
    /**
     * block position z
     */
    public int posZ;
    /**
     * unused
     */
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
