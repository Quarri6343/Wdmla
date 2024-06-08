package com.gtnewhorizons.wdmla.wailacompat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.handlers.HUDHandlerBlocks;
import mcp.mobius.waila.handlers.HUDHandlerEntities;
import mcp.mobius.waila.utils.WailaExceptionHandler;

public class DataProviderCompat {

    private final HUDHandlerBlocks legacyBlockHandler = new HUDHandlerBlocks();
    private final HUDHandlerEntities legacyEntityHandler = new HUDHandlerEntities();

    public List<String> getLegacyBlockTooltips(ItemStack itemForm, DataAccessorCommon legacyAccessor) {
        List<String> legacyTooltips = new ArrayList<>();
        try {
            // some WailaHead Handlers modify item name text, so we have to insert dummy item name to avoid crash
            legacyTooltips = legacyBlockHandler
                    .getWailaHead(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());
            LinkedHashMap<Integer, List<IWailaDataProvider>> legacyHeadProviders = new LinkedHashMap<>();
            legacyHeadProviders.putAll(ModuleRegistrar.instance().getHeadProviders(legacyAccessor.getBlock()));
            legacyHeadProviders.putAll(ModuleRegistrar.instance().getHeadProviders(legacyAccessor.getTileEntity()));
            for (List<IWailaDataProvider> providersList : legacyHeadProviders.values()) {
                for (IWailaDataProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider
                            .getWailaHead(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());
                }
            }
            if (!legacyTooltips.isEmpty()) {
                legacyTooltips.remove(0);
            }

            LinkedHashMap<Integer, List<IWailaDataProvider>> legacyBodyProviders = new LinkedHashMap<>();
            legacyBodyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(legacyAccessor.getBlock()));
            legacyBodyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(legacyAccessor.getTileEntity()));
            for (List<IWailaDataProvider> providersList : legacyBodyProviders.values()) {
                for (IWailaDataProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider
                            .getWailaBody(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());;
                }
            }

            // Hopefully no mod edits mod name in Waila...
            LinkedHashMap<Integer, List<IWailaDataProvider>> legacyTailProviders = new LinkedHashMap<>();
            legacyTailProviders.putAll(ModuleRegistrar.instance().getTailProviders(legacyAccessor.getBlock()));
            legacyTailProviders.putAll(ModuleRegistrar.instance().getTailProviders(legacyAccessor.getTileEntity()));
            for (List<IWailaDataProvider> providersList : legacyTailProviders.values()) {
                for (IWailaDataProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider
                            .getWailaTail(itemForm, legacyTooltips, legacyAccessor, ConfigHandler.instance());;
                }
            }
        } catch (Throwable e) {
            WailaExceptionHandler.handleErr(e, this.getClass().toString(), legacyTooltips);
        }

        return legacyTooltips;
    }

    public List<String> getLegacyEntityTooltips(Entity entity, DataAccessorCommon legacyAccessor) {
        List<String> legacyTooltips = new ArrayList<>();
        try {
            // some WailaHead Handlers modify item name text, so we have to insert dummy item name to avoid crash
            legacyTooltips = legacyEntityHandler
                    .getWailaHead(entity, legacyTooltips, legacyAccessor, ConfigHandler.instance());
            for (List<IWailaEntityProvider> providersList : ModuleRegistrar.instance()
                    .getHeadEntityProviders(legacyAccessor.getEntity()).values()) {
                for (IWailaEntityProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider
                            .getWailaHead(entity, legacyTooltips, legacyAccessor, ConfigHandler.instance());
                }
            }
            if (!legacyTooltips.isEmpty()) {
                legacyTooltips.remove(0);
            }

            for (List<IWailaEntityProvider> providersList : ModuleRegistrar.instance()
                    .getBodyEntityProviders(legacyAccessor.getEntity()).values()) {
                for (IWailaEntityProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider
                            .getWailaBody(entity, legacyTooltips, legacyAccessor, ConfigHandler.instance());
                }
            }

            // Hopefully no mod edits mod name in Waila...
            for (List<IWailaEntityProvider> providersList : ModuleRegistrar.instance()
                    .getTailEntityProviders(legacyAccessor.getEntity()).values()) {
                for (IWailaEntityProvider dataProvider : providersList) {
                    legacyTooltips = dataProvider
                            .getWailaTail(entity, legacyTooltips, legacyAccessor, ConfigHandler.instance());
                }
            }
        } catch (Throwable e) {
            WailaExceptionHandler.handleErr(e, this.getClass().toString(), legacyTooltips);
        }

        return legacyTooltips;
    }
}
