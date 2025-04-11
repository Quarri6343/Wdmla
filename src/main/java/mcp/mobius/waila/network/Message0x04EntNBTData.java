package mcp.mobius.waila.network;

import net.minecraft.nbt.NBTTagCompound;

import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.impl.ObjectDataCenter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.utils.WailaExceptionHandler;

/**
 * The server response to {@link Message0x03EntRequest}.
 */
public class Message0x04EntNBTData extends SimpleChannelInboundHandler<Message0x04EntNBTData> implements IWailaMessage {

    /**
     * all nbt tags mainly written by {@link IServerDataProvider}
     */
    NBTTagCompound tag;

    public Message0x04EntNBTData() {}

    public Message0x04EntNBTData(NBTTagCompound tag) {
        this.tag = tag;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target) throws Exception {
        WailaPacketHandler.INSTANCE.writeNBT(target, tag);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage msg) {
        try {
            this.tag = WailaPacketHandler.INSTANCE.readNBT(dat);
        } catch (Exception e) {
            WailaExceptionHandler.handleErr(e, this.getClass().toString(), null);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message0x04EntNBTData msg) {
        DataAccessorCommon.instance.setNBTData(msg.tag);
        ObjectDataCenter.setServerData(msg.tag);
    }

}
