package com.gtnewhorizons.wdmla.impl;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import net.minecraft.entity.Entity;

import com.gtnewhorizons.wdmla.api.AccessorClientHandler;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.overlay.RayTracing;
import com.gtnewhorizons.wdmla.wailacompat.DataProviderCompat;
import com.gtnewhorizons.wdmla.wailacompat.RayTracingCompat;
import com.gtnewhorizons.wdmla.wailacompat.TooltipCompat;

import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.network.Message0x03EntRequest;
import mcp.mobius.waila.network.WailaPacketHandler;

public class EntityAccessorClientHandler implements AccessorClientHandler<EntityAccessor> {

    private final TooltipCompat tooltipCompat = new TooltipCompat();
    private final DataProviderCompat dataProviderCompat = new DataProviderCompat();

    @Override
    public boolean shouldDisplay(EntityAccessor accessor) {
        // TODO: config
        return true;
    }

    @Override
    public boolean shouldRequestData(EntityAccessor accessor) {
        for (IServerDataProvider<EntityAccessor> provider : WDMlaCommonRegistration.instance()
                .getEntityNBTProviders(accessor.getEntity())) {
            if (provider.shouldRequestData(accessor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void requestData(EntityAccessor accessor) {
        HashSet<String> keys = new HashSet<>();
        WailaPacketHandler.INSTANCE.sendToServer(new Message0x03EntRequest(accessor.getEntity(), keys));
    }

    @Override
    public void gatherComponents(EntityAccessor accessor, Function<IWDMlaProvider, ITooltip> tooltipProvider) {
        // step 1: gather WDMla tooltip components including icon, block name and mod name
        for (IComponentProvider<EntityAccessor> provider : WDMlaClientRegistration.instance()
                .getEntityProviders(accessor.getEntity(), iComponentProvider -> WDMlaConfig.instance().isProviderEnabled(iComponentProvider))) {
            ITooltip middleTooltip = tooltipProvider.apply(provider);
            provider.appendTooltip(middleTooltip, accessor, WDMlaConfig.instance());
        }

        // step 2: setup legacy DataAccessor with legacy WailaEntity
        DataAccessorCommon legacyAccessor = DataAccessorCommon.instance;
        legacyAccessor.set(accessor.getWorld(), accessor.getPlayer(), accessor.getHitResult());
        Entity entity = RayTracingCompat.INSTANCE.getWailaEntity(RayTracing.instance().getTarget());
        if (entity == null) {
            entity = RayTracing.instance().getTargetEntity();
        }

        // step 3: gather raw tooltip lines from the old Waila api (this may include Waila regex which represents
        // ItemStack or Progressbar)
        List<String> legacyTooltips = dataProviderCompat.getLegacyEntityTooltips(entity, legacyAccessor);

        // step 4: Convert legacy tooltip String to actual various WDMla component
        ITooltip convertedTooltips = tooltipCompat.computeRenderables(legacyTooltips);
        ITooltip lateTooltip = tooltipProvider.apply(null);
        lateTooltip.child(convertedTooltips);
    }
}
