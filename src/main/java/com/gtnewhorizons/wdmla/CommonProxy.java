package com.gtnewhorizons.wdmla;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import com.google.common.cache.Cache;
import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.view.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.impl.lookup.WrappedHierarchyLookup;
import com.gtnewhorizons.wdmla.plugin.core.CorePlugin;
import com.gtnewhorizons.wdmla.plugin.harvestability.HarvestabilityPlugin;
import com.gtnewhorizons.wdmla.plugin.harvestability.MissingHarvestInfo;
import com.gtnewhorizons.wdmla.plugin.universal.ItemCollector;
import com.gtnewhorizons.wdmla.plugin.universal.ItemIterator;
import com.gtnewhorizons.wdmla.plugin.universal.UniversalPlugin;
import com.gtnewhorizons.wdmla.plugin.vanilla.VanillaPlugin;
import com.gtnewhorizons.wdmla.test.TestMode;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.waila.utils.WailaExceptionHandler;

public class CommonProxy {

    private LinkedHashMap<String, String> imcRequests = new LinkedHashMap<>();

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        MissingHarvestInfo.init();
        FMLCommonHandler.instance().bus().register(this);

        if (WDMla.isDevEnv() && WDMla.testMode == TestMode.WDMla) {
            // testing IMC
            FMLInterModComms.sendMessage(WDMla.MODID, "registerPlugin", "com.gtnewhorizons.wdmla.test.TestPlugin");
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        WDMlaCommonRegistration common = WDMlaCommonRegistration.instance();
        common.startSession();
        registerBuiltInServerPlugins(common);
        registerExternalServerPlugins(common);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            WDMlaClientRegistration client = WDMlaClientRegistration.instance();
            client.startSession();
            registerBuiltInClientPlugins(client);
            registerExternalClientPlugins(client);
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

    public void registerBuiltInServerPlugins(WDMlaCommonRegistration commonRegistration) {
        new CorePlugin().register(commonRegistration);
        new UniversalPlugin().register(commonRegistration);
        new VanillaPlugin().register(commonRegistration);
    }

    public void registerBuiltInClientPlugins(WDMlaClientRegistration clientRegistration) {

        new CorePlugin().registerClient(clientRegistration);
        new UniversalPlugin().registerClient(clientRegistration);
        new VanillaPlugin().registerClient(clientRegistration);
        new HarvestabilityPlugin().registerClient(clientRegistration);
    }

    // TODO:use @WDMlaPlugin instead
    public void processIMC(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage imcMessage : event.getMessages()) {
            if (!imcMessage.isStringMessage()) continue;
            if (imcMessage.key.equalsIgnoreCase("registerPlugin")) {
                Waila.log.info(
                        String.format(
                                "Receiving registration request from [ %s ] for method %s",
                                imcMessage.getSender(),
                                imcMessage.getStringValue()));
                imcRequests.put(imcMessage.getStringValue(), imcMessage.getSender());
            }
        }
    }

    public void registerExternalServerPlugins(WDMlaCommonRegistration registration) {
        for (String s : imcRequests.keySet()) {
            this.callbackRegistrationServer(s, imcRequests.get(s), registration);
        }
    }

    public void callbackRegistrationServer(String className, String modname, WDMlaCommonRegistration registration) {
        String methodName = "register";

        Waila.log.info(String.format("Trying to reflect %s %s", className, methodName));

        try {
            Class<?> reflectClass = Class.forName(className);
            Method reflectMethod = reflectClass.getDeclaredMethod(methodName, IWDMlaCommonRegistration.class);
            reflectMethod.invoke(reflectClass.newInstance(), registration);

            Waila.log.info(String.format("Success in registering %s", modname));

        } catch (ClassNotFoundException e) {
            Waila.log.warn(String.format("Could not find class %s", className));
        } catch (NoSuchMethodException e) {
            Waila.log.warn(String.format("Could not find method %s", methodName));
        } catch (Exception e) {
            Waila.log.warn(String.format("Exception while trying to access the method : %s", e));
        }
    }

    public void registerExternalClientPlugins(WDMlaClientRegistration registration) {
        for (String s : imcRequests.keySet()) {
            this.callbackRegistrationClient(s, imcRequests.get(s), registration);
        }
    }

    public void callbackRegistrationClient(String className, String modname, WDMlaClientRegistration registration) {
        String methodName = "registerClient";

        Waila.log.info(String.format("Trying to reflect %s %s", className, methodName));

        try {
            Class<?> reflectClass = Class.forName(className);
            Method reflectMethod = reflectClass.getDeclaredMethod(methodName, IWDMlaClientRegistration.class);
            reflectMethod.invoke(reflectClass.newInstance(), registration);

            Waila.log.info(String.format("Success in registering %s", modname));

        } catch (ClassNotFoundException e) {
            Waila.log.warn(String.format("Could not find class %s", className));
        } catch (NoSuchMethodException e) {
            Waila.log.warn(String.format("Could not find method %s", methodName));
        } catch (Exception e) {
            Waila.log.warn(String.format("Exception while trying to access the method : %s", e));
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
