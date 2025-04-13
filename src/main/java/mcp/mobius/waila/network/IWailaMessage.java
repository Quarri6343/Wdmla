package mcp.mobius.waila.network;

import org.jetbrains.annotations.ApiStatus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * This is the standard Waila packet wrapping object. <br>
 * Packets extend this requires {@link io.netty.channel.SimpleChannelInboundHandler} to be sent
 */
@ApiStatus.Internal
public interface IWailaMessage {

    /**
     * Write data into packet.
     * 
     * @param ctx    context
     * @param msg    the packet instance, which means this packet object
     * @param target the raw packet buffer will be sent
     * @throws Exception encode failed
     */
    void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target) throws Exception;

    /**
     * read data from sent packet.
     * 
     * @param ctx context
     * @param dat the packet buffer storing received data
     * @param msg the packet instance, which means this packet object
     */
    void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage msg);
}
