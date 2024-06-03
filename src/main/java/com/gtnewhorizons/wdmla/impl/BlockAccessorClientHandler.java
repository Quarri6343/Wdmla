package com.gtnewhorizons.wdmla.impl;

import com.gtnewhorizons.wdmla.api.AccessorClientHandler;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.VPanelComponent;
import com.gtnewhorizons.wdmla.wailacompat.DataProviderCompat;
import com.gtnewhorizons.wdmla.wailacompat.RayTracingCompat;
import com.gtnewhorizons.wdmla.wailacompat.TooltipCompat;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.List;

import static mcp.mobius.waila.api.SpecialChars.BLUE;
import static mcp.mobius.waila.api.SpecialChars.ITALIC;

public class BlockAccessorClientHandler implements AccessorClientHandler<BlockAccessor> {

    private final TooltipCompat tooltipCompat = new TooltipCompat();
    private final DataProviderCompat dataProviderCompat = new DataProviderCompat();

    @Override
    public boolean shouldDisplay(BlockAccessor accessor) {
        return true;
        // TODO: config
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
        WailaPacketHandler.INSTANCE.sendToServer(new Message0x01TERequest(accessor.getTileEntity(), keys, true));
    }

    @Override
    public ITooltip getIcon(BlockAccessor accessor) {
        // Step 1: check whether Wdmla has custom icon provider or not
        ITooltip overrideIcon = new VPanelComponent();

        boolean hasIconOverride = false;
        for (IComponentProvider<BlockAccessor> provider : WDMlaClientRegistration.instance()
                .getBlockIconProviders(accessor.getBlock(), iComponentProvider -> true)) {
            ITooltip providerIcon = provider.getIcon(accessor, overrideIcon);
            if (providerIcon != null) {
                overrideIcon = providerIcon;
                hasIconOverride = true;
            }
        }

        if (hasIconOverride) {
            return overrideIcon;
        }

        // step 2: check whether waila has custom Wailastack or not
        ItemStack overrideStack = RayTracingCompat.INSTANCE.getWailaStack(accessor.getHitResult());

        // step 3: construct an actual icon
        ITooltip icon = new VPanelComponent();
        ITooltip row = icon.horizontal();
        ItemStack itemStack = overrideStack != null ? overrideStack : accessor.getItemForm();
        row.item(itemStack);

        ITooltip row_vertical = row.vertical();
        row_vertical.text(DisplayUtil.itemDisplayNameShort(itemStack));
        String modName = ModIdentification.nameFromStack(itemStack);
        if (modName != null && !modName.isEmpty()) {
            row_vertical.text(BLUE + ITALIC + modName);
        }

        return icon;
    }

    @Override
    public void gatherComponents(BlockAccessor accessor, ITooltip tooltip) {
        // step 0: append icon, block name and mod name to tooltip
        tooltip.child(getIcon(accessor));

        // step 1: gather wdmla tooltip components
        //TODO: config filter
        for (IComponentProvider<BlockAccessor> provider : WDMlaClientRegistration.instance()
                .getBlockProviders(accessor.getBlock(), iComponentProvider -> true)) {
            provider.appendTooltip(tooltip, accessor);
        }

        // step 2: setup legacy DataAccessor with legacy wailastack
        DataAccessorCommon legacyAccessor = DataAccessorCommon.instance;
        legacyAccessor.set(accessor.getWorld(), accessor.getPlayer(), accessor.getHitResult());
        ItemStack itemForm = RayTracingCompat.INSTANCE.getWailaStack(accessor.getHitResult());
        if (itemForm == null) {
            itemForm = accessor.getItemForm();
        }

        // step 3: gather legacy raw tooltip lines (this may include Waila regex representing ItemStack or Progressbar)
        List<String> legacyTooltips = dataProviderCompat.getLegacyTooltips(itemForm, legacyAccessor);

        // step 4: Convert legacy tooltip String to actual various WDMla component
        ITooltip convertedTooltips = tooltipCompat.computeRenderables(legacyTooltips);
        tooltip.child(convertedTooltips);
    }
}
