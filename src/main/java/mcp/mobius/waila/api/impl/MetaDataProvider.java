package mcp.mobius.waila.api.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.cbcore.Layout;
import mcp.mobius.waila.network.Message0x03EntRequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.waila.utils.WailaExceptionHandler;

public class MetaDataProvider {

    private final Map<Integer, List<IWailaEntityProvider>> headEntityProviders = new TreeMap<>();
    private final Map<Integer, List<IWailaEntityProvider>> bodyEntityProviders = new TreeMap<>();
    private final Map<Integer, List<IWailaEntityProvider>> tailEntityProviders = new TreeMap<>();

    // TODO: remove once the migration is complete
    public List<String> handleEntityTextData(Entity entity, World world, EntityPlayer player, MovingObjectPosition mop,
            DataAccessorCommon accessor, List<String> currenttip, Layout layout) {

        if (accessor.getEntity() != null && Waila.instance.serverPresent && accessor.isTimeElapsed(250)) {
            accessor.resetTimer();
            HashSet<String> keys = new HashSet<>();

            if (ModuleRegistrar.instance().hasSyncedNBTKeys(accessor.getEntity()))
                keys.addAll(ModuleRegistrar.instance().getSyncedNBTKeys(accessor.getEntity()));

            if (!keys.isEmpty() || ModuleRegistrar.instance().hasNBTEntityProviders(accessor.getEntity()))
                WailaPacketHandler.INSTANCE.sendToServer(new Message0x03EntRequest(accessor.getEntity(), keys));

        } else if (accessor.getEntity() != null && !Waila.instance.serverPresent && accessor.isTimeElapsed(250)) {

            try {
                NBTTagCompound tag = new NBTTagCompound();
                accessor.getEntity().writeToNBT(tag);
                accessor.remoteNbt = tag;
            } catch (Exception e) {
                WailaExceptionHandler.handleErr(e, this.getClass().getName(), null);
            }
        }

        headEntityProviders.clear();
        bodyEntityProviders.clear();
        tailEntityProviders.clear();

        /* Lookup by class (for entities) */
        if (layout == Layout.HEADER && ModuleRegistrar.instance().hasHeadEntityProviders(entity))
            headEntityProviders.putAll(ModuleRegistrar.instance().getHeadEntityProviders(entity));

        else if (layout == Layout.BODY && ModuleRegistrar.instance().hasBodyEntityProviders(entity))
            bodyEntityProviders.putAll(ModuleRegistrar.instance().getBodyEntityProviders(entity));

        else if (layout == Layout.FOOTER && ModuleRegistrar.instance().hasTailEntityProviders(entity))
            tailEntityProviders.putAll(ModuleRegistrar.instance().getTailEntityProviders(entity));

        /* Apply all collected providers */
        if (layout == Layout.HEADER) for (List<IWailaEntityProvider> providersList : headEntityProviders.values()) {
            for (IWailaEntityProvider dataProvider : providersList) try {
                currenttip = dataProvider.getWailaHead(entity, currenttip, accessor, ConfigHandler.instance());
            } catch (Throwable e) {
                currenttip = WailaExceptionHandler.handleErr(e, dataProvider.getClass().toString(), currenttip);
            }
        }

        if (layout == Layout.BODY) for (List<IWailaEntityProvider> providersList : bodyEntityProviders.values()) {
            for (IWailaEntityProvider dataProvider : providersList) try {
                currenttip = dataProvider.getWailaBody(entity, currenttip, accessor, ConfigHandler.instance());
            } catch (Throwable e) {
                currenttip = WailaExceptionHandler.handleErr(e, dataProvider.getClass().toString(), currenttip);
            }
        }

        if (layout == Layout.FOOTER) for (List<IWailaEntityProvider> providersList : tailEntityProviders.values()) {
            for (IWailaEntityProvider dataProvider : providersList) try {
                currenttip = dataProvider.getWailaTail(entity, currenttip, accessor, ConfigHandler.instance());
            } catch (Throwable e) {
                currenttip = WailaExceptionHandler.handleErr(e, dataProvider.getClass().toString(), currenttip);
            }
        }

        return currenttip;
    }
}
