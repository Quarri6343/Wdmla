package com.gtnewhorizons.wdmla.impl;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizons.wdmla.api.AccessorClientHandler;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.wailacompat.DataProviderCompat;
import com.gtnewhorizons.wdmla.wailacompat.RayTracingCompat;
import com.gtnewhorizons.wdmla.wailacompat.TooltipCompat;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.waila.utils.Constants;

public class BlockAccessorClientHandler implements AccessorClientHandler<BlockAccessor> {

    private final TooltipCompat tooltipCompat = new TooltipCompat();
    private final DataProviderCompat dataProviderCompat = new DataProviderCompat();

    @Override
    public boolean shouldDisplay(BlockAccessor accessor) {
        return ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_SHOW, true);
    }

    @Override
    public boolean shouldRequestData(BlockAccessor accessor) {
        // TODO: support non tile entity block data requesting
        if (accessor.getTileEntity() == null) {
            return false;
        }

        // Step 1: check WDMla has providers
        for (IServerDataProvider<BlockAccessor> provider : WDMlaCommonRegistration.instance()
                .getBlockNBTProviders(accessor.getBlock(), accessor.getTileEntity())) {
            if (provider.shouldRequestData(accessor)) {
                return true;
            }
        }

        // Step 2: check Waila has providers
        if (ModuleRegistrar.instance().hasNBTProviders(accessor.getBlock())
                || ModuleRegistrar.instance().hasNBTProviders(accessor.getTileEntity())) {
            return true;
        }

        return false;
    }

    @Override
    public void requestData(BlockAccessor accessor) {
        HashSet<String> keys = new HashSet<>();
        WailaPacketHandler.INSTANCE.sendToServer(new Message0x01TERequest(accessor.getTileEntity(), keys));
    }

    @Override
    public void gatherComponents(BlockAccessor accessor, Function<IWDMlaProvider, ITooltip> tooltipProvider) {
        // step 1: setup legacy DataAccessor with legacy WailaStack
        DataAccessorCommon legacyAccessor = DataAccessorCommon.instance;
        legacyAccessor.set(accessor.getWorld(), accessor.getPlayer(), accessor.getHitResult());
        ItemStack itemForm = RayTracingCompat.INSTANCE.getWailaStack(accessor.getHitResult());
        if (itemForm == null) {
            itemForm = accessor.getItemForm();
        }

        // step 2: gather WDMla tooltip components including icon, block name and mod name (with WailaStack override)
        for (IComponentProvider<BlockAccessor> provider : WDMlaClientRegistration.instance().getBlockProviders(
                accessor.getBlock(),
                iComponentProvider -> WDMlaConfig.instance().isProviderEnabled(iComponentProvider))) {
            ITooltip middleTooltip = tooltipProvider.apply(provider);
            provider.appendTooltip(middleTooltip, accessor, WDMlaConfig.instance());
        }

        // step 3: gather raw tooltip lines from the old Waila api (this may include Waila regex which represents
        // ItemStack or Progressbar)
        List<String> legacyTooltips = dataProviderCompat.getLegacyBlockTooltips(itemForm, legacyAccessor);

        // step 4: Convert legacy tooltip String to actual various WDMla component
        ITooltip convertedTooltips = tooltipCompat.computeRenderables(legacyTooltips);
        ITooltip lateTooltip = tooltipProvider.apply(null);
        lateTooltip.child(convertedTooltips);
    }
}
