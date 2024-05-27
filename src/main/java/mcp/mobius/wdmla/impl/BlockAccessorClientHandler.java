package mcp.mobius.wdmla.impl;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.wdmla.api.AccessorClientHandler;
import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IServerDataProvider;
import mcp.mobius.wdmla.api.ui.ITooltip;
import mcp.mobius.wdmla.impl.ui.component.TextComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class BlockAccessorClientHandler implements AccessorClientHandler<BlockAccessor> {
    @Override
    public boolean shouldDisplay(BlockAccessor accessor) {
        return true;
        //TODO: config
    }

    @Override
    public boolean shouldRequestData(BlockAccessor accessor) {
        for (IServerDataProvider<BlockAccessor> provider : WdmlaCommonRegistration.instance().getBlockNBTProviders(
                accessor.getBlock(),
                accessor.getTileEntity())) {
            if (provider.shouldRequestData(accessor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void requestData(BlockAccessor accessor) {
        HashSet<String> keys = new HashSet<>();
        WailaPacketHandler.INSTANCE
                .sendToServer(new Message0x01TERequest(accessor.getTileEntity(), keys, true));
    }

    @Override
    public void gatherComponents(BlockAccessor accessor, ITooltip tooltip) {
        for (IComponentProvider<BlockAccessor> provider : WdmlaClientRegistration.instance().getProviders(accessor.getBlock())) {
            provider.appendTooltip(tooltip, accessor);
        }

        for (IComponentProvider<BlockAccessor> provider : WdmlaClientRegistration.instance().getProviders(accessor.getTileEntity())) {
            provider.appendTooltip(tooltip, accessor);
        }

        //Legacy Tooltip support
        //TODO: WailaStack support
        //TODO: WailaHead, WailaTail support
        DataAccessorCommon legacyAccessor = DataAccessorCommon.instance;
        legacyAccessor.set(accessor.getWorld(), accessor.getPlayer(), accessor.getHitResult());
        LinkedHashMap<Integer, List<IWailaDataProvider>> legacyProviders = new LinkedHashMap<>();
        if(ModuleRegistrar.instance().hasBodyProviders(accessor.getBlock())) {
            legacyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(accessor.getBlock()));
        }
        if(ModuleRegistrar.instance().hasBodyProviders(accessor.getTileEntity())) {
            legacyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(accessor.getTileEntity()));
        }
        for (List<IWailaDataProvider> providersList : legacyProviders.values()) {
            for (IWailaDataProvider dataProvider : providersList) {
                List<String> tooltips = new ArrayList<>();
                tooltips = dataProvider.getWailaBody(accessor.getItemForm(), tooltips, legacyAccessor, ConfigHandler.instance());
                for (String tooltipStr : tooltips) {
                    tooltip.child(new TextComponent(tooltipStr));
                }
            }
        }
    }
}
