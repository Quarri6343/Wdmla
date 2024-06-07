package com.gtnewhorizons.wdmla.wailacompat;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.netty.channel.ChannelHandlerContext;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.utils.AccessHelper;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class TERequestCompat {

    private static final Field classToNameMap;

    static {
        try {
            classToNameMap = ReflectionHelper.findField(TileEntity.class, "classToNameMap", "field_145853_j");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void handleRequest(ChannelHandlerContext ctx, NBTTagCompound tag, Message0x01TERequest msg) {
        World world = DimensionManager.getWorld(msg.dim);
        TileEntity entity = world.getTileEntity(msg.posX, msg.posY, msg.posZ);
        Block block = world.getBlock(msg.posX, msg.posY, msg.posZ);
        boolean hasLegacyNBTBlock = ModuleRegistrar.instance().hasNBTProviders(block);
        boolean hasLegacyNBTEnt = ModuleRegistrar.instance().hasNBTProviders(entity);

        try {
            if ((hasLegacyNBTBlock || hasLegacyNBTEnt)) {
                tag.setString("id", (String) ((HashMap) classToNameMap.get(null)).get(entity.getClass()));

                EntityPlayerMP player = ((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER)
                        .get()).playerEntity;

                for (List<IWailaDataProvider> providersList : ModuleRegistrar.instance().getNBTProviders(block)
                        .values()) {
                    for (IWailaDataProvider provider : providersList) {
                        try {
                            tag = provider.getNBTData(player, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        } catch (AbstractMethodError | NoSuchMethodError ame) {
                            tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                    }
                }

                for (List<IWailaDataProvider> providersList : ModuleRegistrar.instance().getNBTProviders(entity)
                        .values()) {
                    for (IWailaDataProvider provider : providersList) {
                        try {
                            tag = provider.getNBTData(player, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        } catch (AbstractMethodError | NoSuchMethodError ame) {
                            tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                    }
                }
            }

            tag.setInteger("WailaX", msg.posX);
            tag.setInteger("WailaY", msg.posY);
            tag.setInteger("WailaZ", msg.posZ);
            tag.setString("WailaID", (String) ((HashMap) classToNameMap.get(null)).get(entity.getClass()));
        }
        catch (Throwable e) {
            WailaExceptionHandler.handleErr(e, entity.getClass().toString(), null);
        }
    }
}
