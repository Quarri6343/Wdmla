package com.gtnewhorizons.wdmla.wailacompat;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.handlers.HUDHandlerBlocks;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataProviderCompat {

    private final HUDHandlerBlocks legacyBlockHandler = new HUDHandlerBlocks();

    public List<String> getLegacyTooltips(ItemStack itemForm, DataAccessorCommon legacyAccessor) {
        List<String> legacyTooltips = new ArrayList<>();
        try {
            //some WailaHead Handlers modify item name text, so we have to insert dummy item name to avoid crash
            legacyTooltips = legacyBlockHandler.getWailaHead(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());
            LinkedHashMap<Integer, List<IWailaDataProvider>> legacyHeadProviders = new LinkedHashMap<>();
            legacyHeadProviders.putAll(ModuleRegistrar.instance().getHeadProviders(legacyAccessor.getBlock()));
            legacyHeadProviders.putAll(ModuleRegistrar.instance().getHeadProviders(legacyAccessor.getTileEntity()));
            for (List<IWailaDataProvider> providersList : legacyHeadProviders.values()) {
                for (IWailaDataProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider.getWailaHead(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());
                }
            }
            if(!legacyTooltips.isEmpty()) {
                legacyTooltips.remove(0);
            }

            LinkedHashMap<Integer, List<IWailaDataProvider>> legacyBodyProviders = new LinkedHashMap<>();
            legacyBodyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(legacyAccessor.getBlock()));
            legacyBodyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(legacyAccessor.getTileEntity()));
            for (List<IWailaDataProvider> providersList : legacyBodyProviders.values()) {
                for (IWailaDataProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider.getWailaBody(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());;
                }
            }

            //Hopefully no mod edits mod name in Waila...
            LinkedHashMap<Integer, List<IWailaDataProvider>> legacyTailProviders = new LinkedHashMap<>();
            legacyTailProviders.putAll(ModuleRegistrar.instance().getTailProviders(legacyAccessor.getBlock()));
            legacyTailProviders.putAll(ModuleRegistrar.instance().getTailProviders(legacyAccessor.getTileEntity()));
            for (List<IWailaDataProvider> providersList : legacyTailProviders.values()) {
                for (IWailaDataProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider.getWailaTail(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());;
                }
            }
        }
        catch (Throwable e) {
            legacyTooltips = WailaExceptionHandler.handleErr(e, this.getClass().toString(), legacyTooltips);
        }

        return legacyTooltips;
    }
}
