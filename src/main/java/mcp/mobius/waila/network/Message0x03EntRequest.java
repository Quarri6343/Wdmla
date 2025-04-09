package mcp.mobius.waila.network;

import java.util.HashSet;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

import com.gtnewhorizons.wdmla.api.accessor.EntityAccessorImpl;
import com.gtnewhorizons.wdmla.wailacompat.EntRequestCompat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mcp.mobius.waila.utils.WailaExceptionHandler;

/**
 * Requests entity data to server side.<br>
 * Keys field does nothing right now
 */
public class Message0x03EntRequest extends SimpleChannelInboundHandler<Message0x03EntRequest> implements IWailaMessage {

    /**
     * dimension id entity belongs to
     */
    public int dim;
    /**
     * entity id
     */
    public int id;
    /**
     * unused
     */
    public HashSet<String> keys = new HashSet<>();

    public Message0x03EntRequest() {}

    public Message0x03EntRequest(Entity ent, HashSet<String> keys) {
        this.dim = ent.worldObj.provider.dimensionId;
        this.id = ent.getEntityId();
        this.keys = keys;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target) throws Exception {
        target.writeInt(dim);
        target.writeInt(id);
        target.writeInt(this.keys.size());

        for (String key : keys) WailaPacketHandler.INSTANCE.writeString(target, key);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage rawmsg) {
        try {
            Message0x03EntRequest msg = (Message0x03EntRequest) rawmsg;
            msg.dim = dat.readInt();
            msg.id = dat.readInt();

            int nkeys = dat.readInt();

            for (int i = 0; i < nkeys; i++) this.keys.add(WailaPacketHandler.INSTANCE.readString(dat));

        } catch (Exception e) {
            WailaExceptionHandler.handleErr(e, this.getClass().toString(), null);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message0x03EntRequest msg) {
        NBTTagCompound tag = new NBTTagCompound();

        EntityAccessorImpl.handleRequest(ctx, tag, msg);
        EntRequestCompat.handleRequest(ctx, tag, msg);

        WailaPacketHandler.INSTANCE.sendTo(new Message0x04EntNBTData(tag), WailaPacketHandler.getPlayer(ctx));
    }

}
