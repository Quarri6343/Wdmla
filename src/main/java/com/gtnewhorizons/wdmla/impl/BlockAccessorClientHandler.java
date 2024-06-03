package com.gtnewhorizons.wdmla.impl;

import com.gtnewhorizons.wdmla.api.*;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
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
import java.util.function.Function;

import static mcp.mobius.waila.api.SpecialChars.*;

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

    //TODO: move to CorePlugin
    public ITooltip getDefaultIcon(BlockAccessor accessor) {
        // step 1: check whether waila has custom Wailastack or not
        ItemStack overrideStack = RayTracingCompat.INSTANCE.getWailaStack(accessor.getHitResult());

        // step 2: construct an actual icon
        ITooltip icon = new VPanelComponent();
        ITooltip row = icon.horizontal();
        ItemStack itemStack = overrideStack != null ? overrideStack : accessor.getItemForm();
        row.child(new ItemComponent(itemStack).tag(Identifiers.ITEM_ICON));

        ITooltip row_vertical = row.vertical();
        row_vertical.child(new TextComponent(WHITE + DisplayUtil.itemDisplayNameShort(itemStack)).tag(Identifiers.ITEM_NAME));
        String modName = ModIdentification.nameFromStack(itemStack);
        if (modName != null && !modName.isEmpty()) {
            row_vertical.child(new TextComponent(BLUE + ITALIC + modName).tag(Identifiers.MOD_NAME));
        }

        return icon;
    }

    @Override
    public void gatherComponents(BlockAccessor accessor, Function<IWDMlaProvider, ITooltip> tooltipProvider) {
        // step 0: append icon, block name and mod name to tooltip
        ITooltip earlyTooltip = tooltipProvider.apply(null);
        earlyTooltip.child(getDefaultIcon(accessor));

        // step 1: gather WDMla tooltip components
        //TODO: config filter
        for (IComponentProvider<BlockAccessor> provider : WDMlaClientRegistration.instance()
                .getBlockProviders(accessor.getBlock(), iComponentProvider -> true)) {
            ITooltip middleTooltip = tooltipProvider.apply(provider);
            provider.appendTooltip(middleTooltip, accessor);
        }

        // step 2: setup legacy DataAccessor with legacy Wailastack
        DataAccessorCommon legacyAccessor = DataAccessorCommon.instance;
        legacyAccessor.set(accessor.getWorld(), accessor.getPlayer(), accessor.getHitResult());
        ItemStack itemForm = RayTracingCompat.INSTANCE.getWailaStack(accessor.getHitResult());
        if (itemForm == null) {
            itemForm = accessor.getItemForm();
        }

        // step 3: gather raw tooltip lines from the old Waila api (this may include Waila regex which represents ItemStack or Progressbar)
        List<String> legacyTooltips = dataProviderCompat.getLegacyTooltips(itemForm, legacyAccessor);

        // step 4: Convert legacy tooltip String to actual various WDMla component
        ITooltip convertedTooltips = tooltipCompat.computeRenderables(legacyTooltips);
        ITooltip lateTooltip = tooltipProvider.apply(null);
        lateTooltip.child(convertedTooltips);
    }
}
