package mcp.mobius.wdmla.impl;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import mcp.mobius.wdmla.api.IBlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.impl.ui.component.RootComponent;
import mcp.mobius.wdmla.impl.ui.component.TextComponent;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.*;

//TODO: decompose, implement AccessorClientHandler
public enum BlockAccessorClientHandler {
    INSTANCE;

    public @NotNull RootComponent handle(IBlockAccessor accessor) {
        Block block = accessor.getBlock();
        RootComponent root = new RootComponent();

        if (!WdmlaClientRegistration.instance().hasProviders(block)
                && !WdmlaClientRegistration.instance().hasProviders(accessor.getTileEntity())) {
            return root;
        }

        if (accessor.getTileEntity() != null && Waila.instance.serverPresent
                && ObjectDataCenter.isTimeElapsed(ObjectDataCenter.rateLimiter)) {
            ObjectDataCenter.resetTimer();
            HashSet<String> keys = new HashSet<>();

            if (WdmlaCommonRegistration.instance().hasProviders(block)
                    || WdmlaCommonRegistration.instance().hasProviders(accessor.getTileEntity()))
                WailaPacketHandler.INSTANCE
                        .sendToServer(new Message0x01TERequest(accessor.getTileEntity(), keys, true));
            else if (ModuleRegistrar.instance().hasNBTProviders(block) //OLD API Support
                    || ModuleRegistrar.instance().hasNBTProviders(accessor.getTileEntity()))
                WailaPacketHandler.INSTANCE
                        .sendToServer(new Message0x01TERequest(accessor.getTileEntity(), keys, false));
        } else if (accessor.getTileEntity() != null && !Waila.instance.serverPresent
                && ObjectDataCenter.isTimeElapsed(ObjectDataCenter.rateLimiter)) {
            ObjectDataCenter.resetTimer();

            try {
                NBTTagCompound tag = accessor.getServerData();
                accessor.getTileEntity().writeToNBT(tag);
                //TODO: handleRequest inside BlockAccessorImpl
            } catch (Exception e) {
                WailaExceptionHandler.handleErr(e, this.getClass().getName(), null);
            }
        }

        /* Lookup by class (for blocks) */
        if (WdmlaClientRegistration.instance().hasProviders(block)) {
            List<IComponentProvider<IBlockAccessor>> providers = WdmlaClientRegistration.instance().getProviders(block);
            for (IComponentProvider<IBlockAccessor> provider : providers) {
                provider.appendTooltip(root, accessor);
            }
        }

        /* Lookup by class (for tileentities) */
        if ((WdmlaClientRegistration.instance().hasProviders(accessor.getTileEntity()))) {
            List<IComponentProvider<IBlockAccessor>> providers = WdmlaClientRegistration.instance()
                    .getProviders(accessor.getTileEntity());
            for (IComponentProvider<IBlockAccessor> provider : providers) {
                provider.appendTooltip(root, accessor);
            }
        }

        //Legacy Tooltip support
        DataAccessorCommon legacyAccessor = DataAccessorCommon.instance;
        legacyAccessor.set(accessor.getWorld(), accessor.getPlayer(), accessor.getHitResult()); //TODO:pass MovingObjectPosition
        Map<Integer, List<IWailaDataProvider>> legacyProviders = new HashMap<>();
        if(ModuleRegistrar.instance().hasBodyProviders(block)) {
            legacyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(block));
        }
        if(ModuleRegistrar.instance().hasBodyProviders(accessor.getTileEntity())) {
            legacyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(accessor.getTileEntity()));
        }
        for (List<IWailaDataProvider> providersList : legacyProviders.values()) {
            for (IWailaDataProvider dataProvider : providersList) {
                List<String> tooltips = new ArrayList<>();
                tooltips = dataProvider.getWailaBody(accessor.getItemForm(), tooltips, legacyAccessor, ConfigHandler.instance());
                for (String tooltip : tooltips) {
                    root.child(new TextComponent(tooltip));
                }
            }
        }

        return root;
    }
}
