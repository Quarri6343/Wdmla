package mcp.mobius.wdmla.impl;

import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.wdmla.api.AccessorClientHandler;
import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IServerDataProvider;
import mcp.mobius.wdmla.api.IWdmlaProvider;
import mcp.mobius.wdmla.api.ui.IComponent;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.function.Function;

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
    public void gatherComponents(BlockAccessor accessor, Function<IWdmlaProvider, IComponent> tooltipProvider) {
        for (var provider : WdmlaClientRegistration.instance().getProviders(accessor.getBlock())) {
            IComponent tooltip = tooltipProvider.apply(provider);
            provider.appendTooltip(tooltip, accessor);
        }
    }
}
