package com.gtnewhorizons.wdmla;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.plugin.PluginScanner;
import com.gtnewhorizons.wdmla.test.TestPlugin;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import com.google.common.cache.Cache;
import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.view.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.impl.lookup.WrappedHierarchyLookup;
import com.gtnewhorizons.wdmla.plugin.harvestability.MissingHarvestInfo;
import com.gtnewhorizons.wdmla.plugin.universal.ItemCollector;
import com.gtnewhorizons.wdmla.plugin.universal.ItemIterator;
import com.gtnewhorizons.wdmla.test.TestMode;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.waila.utils.WailaExceptionHandler;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        PluginScanner.INSTANCE.scan(event);
    }

    public void init(FMLInitializationEvent event) {
        MissingHarvestInfo.init();
        FMLCommonHandler.instance().bus().register(this);
    }

    public void postInit(FMLPostInitializationEvent event) {
        WDMlaCommonRegistration common = WDMlaCommonRegistration.instance();
        common.startSession();
        registerServerPlugins(common);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            WDMlaClientRegistration client = WDMlaClientRegistration.instance();
            client.startSession();
            registerClientPlugins(client);
            client.endSession();

            WDMlaConfig.instance().reloadConfig();
            WDMlaConfig.instance().save();
            OverlayConfig.updateColors();
        }
        common.endSession();
        loadComplete();
    }

    public static void loadComplete() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            WDMlaCommonRegistration.instance().priorities.sort();
        }
        WDMlaCommonRegistration.instance().loadComplete();
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            WDMlaClientRegistration.instance().loadComplete();
        }
    }

    public void serverStarting(FMLServerStartingEvent event) {}

    public void registerServerPlugins(WDMlaCommonRegistration registration) {
        for (IWDMlaPlugin plugin : PluginScanner.INSTANCE.results) {
            plugin.register(registration);
        }
        if (WDMla.isDevEnv() && WDMla.testMode == TestMode.WDMla) {
            new TestPlugin().register(registration);
        }
    }

    public void registerClientPlugins(WDMlaClientRegistration registration) {
        for (IWDMlaPlugin plugin : PluginScanner.INSTANCE.results) {
            plugin.registerClient(registration);
        }
        if (WDMla.isDevEnv() && WDMla.testMode == TestMode.WDMla) {
            new TestPlugin().registerClient(registration);
        }
    }

    public static <T> Map.Entry<ResourceLocation, List<ViewGroup<T>>> getServerExtensionData(Accessor accessor,
            WrappedHierarchyLookup<IServerExtensionProvider<T>> lookup) {
        for (IServerExtensionProvider<T> provider : lookup.wrappedGet(accessor)) {
            List<ViewGroup<T>> groups;
            try {
                groups = provider.getGroups(accessor);
            } catch (Exception e) {
                WailaExceptionHandler.handleErr(e, provider.getClass().getName(), null);
                continue;
            }
            if (groups != null) {
                return new AbstractMap.SimpleEntry<>(provider.getUid(), groups);
            }
        }
        return null;
    }

    // We collect all inventory whether it is sided or not
    public static ItemCollector<?> createItemCollector(Accessor accessor,
            Cache<Object, ItemCollector<?>> containerCache) {
        if (accessor.getTarget() instanceof EntityHorse) {
            return new ItemCollector<>(new ItemIterator.IInventoryItemIterator(o -> {
                if (o.getTarget() instanceof EntityHorse horse) {
                    return horse.horseChest;
                }
                return null;
            }, 0)); // idk if any mod uses vanilla horse inventory (index > 1)
        }
        try {
            if (accessor.getTarget() instanceof IInventory container) {
                return containerCache
                        .get(container, () -> new ItemCollector<>(new ItemIterator.IInventoryItemIterator(0)));
            }
        } catch (ExecutionException e) {
            WailaExceptionHandler.handleErr(e, null, null);
        }
        return ItemCollector.EMPTY;
    }
}
