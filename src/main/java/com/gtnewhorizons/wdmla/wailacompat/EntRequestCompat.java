package com.gtnewhorizons.wdmla.wailacompat;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.channel.ChannelHandlerContext;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.network.Message0x03EntRequest;
import mcp.mobius.waila.utils.AccessHelper;
import mcp.mobius.waila.utils.NBTUtil;
import mcp.mobius.waila.utils.WailaExceptionHandler;

/**
 * Handles entity data request from client, calls all necessary providers
 */
public class EntRequestCompat {

    public static void handleRequest(ChannelHandlerContext ctx, NBTTagCompound tag, Message0x03EntRequest msg) {
        World world = DimensionManager.getWorld(msg.dim);
        if (world == null) return;
        Entity entity = world.getEntityByID(msg.id);
        if (entity == null) return;
        try {
            EntityPlayerMP player = ((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER)
                    .get()).playerEntity;

            if (ModuleRegistrar.instance().hasNBTEntityProviders(entity)) {
                for (List<IWailaEntityProvider> providersList : ModuleRegistrar.instance().getNBTEntityProviders(entity)
                        .values()) {
                    for (IWailaEntityProvider provider : providersList) {
                        try {
                            tag = provider.getNBTData(player, entity, tag, world);
                        } catch (AbstractMethodError ame) {
                            tag = AccessHelper.getNBTData(provider, entity, tag);
                        }
                    }
                }

            } else {
                entity.writeToNBT(tag);
                tag = NBTUtil.createTag(tag, msg.keys);
            }

            tag.setInteger("WailaEntityID", entity.getEntityId());
        } catch (Throwable e) {
            WailaExceptionHandler.handleErr(e, entity.getClass().toString(), null);
        }
    }
}
