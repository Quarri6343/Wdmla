package mcp.mobius.waila.api.impl;

import java.util.*;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.utils.Constants;

public class ModuleRegistrar implements IWailaRegistrar {

    private static ModuleRegistrar instance = null;

    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> headBlockProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> bodyBlockProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> tailBlockProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> stackBlockProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> NBTDataProviders = new LinkedHashMap<>();

    public LinkedHashMap<Class, ArrayList<IWailaBlockDecorator>> blockClassDecorators = new LinkedHashMap<>();

    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> headEntityProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> bodyEntityProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> tailEntityProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> overrideEntityProviders = new LinkedHashMap<>();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> NBTEntityProviders = new LinkedHashMap<>();

    public LinkedHashMap<String, ArrayList<IWailaFMPProvider>> headFMPProviders = new LinkedHashMap<>();
    public LinkedHashMap<String, ArrayList<IWailaFMPProvider>> bodyFMPProviders = new LinkedHashMap<>();
    public LinkedHashMap<String, ArrayList<IWailaFMPProvider>> tailFMPProviders = new LinkedHashMap<>();

    public LinkedHashMap<String, ArrayList<IWailaFMPDecorator>> FMPClassDecorators = new LinkedHashMap<>();

    public LinkedHashMap<Class, HashSet<String>> syncedNBTKeys = new LinkedHashMap<>();

    public LinkedHashMap<String, String> IMCRequests = new LinkedHashMap<>();

    private ModuleRegistrar() {
        instance = this;
    }

    public static ModuleRegistrar instance() {
        if (ModuleRegistrar.instance == null) ModuleRegistrar.instance = new ModuleRegistrar();
        return ModuleRegistrar.instance;
    }

    /* IMC HANDLING */
    public void addIMCRequest(String method, String modname) {
        this.IMCRequests.put(method, modname);
    }

    /* CONFIG HANDLING */
    @Override
    public void addConfig(String modname, String key, String configname) {
        this.addConfig(modname, key, configname, Constants.CFG_DEFAULT_VALUE);
    }

    @Override
    public void addConfigRemote(String modname, String key, String configname) {
        this.addConfigRemote(modname, key, configname, Constants.CFG_DEFAULT_VALUE);
    }

    @Override
    public void addConfig(String modname, String key) {
        this.addConfig(modname, key, Constants.CFG_DEFAULT_VALUE);
    }

    @Override
    public void addConfigRemote(String modname, String key) {
        this.addConfigRemote(modname, key, Constants.CFG_DEFAULT_VALUE);
    }

    @Override
    public void addConfig(String modname, String key, String configname, boolean defvalue) {
        ConfigHandler.instance().addConfig(modname, key, LangUtil.translateG(configname), defvalue);
    }

    @Override
    public void addConfigRemote(String modname, String key, String configname, boolean defvalue) {
        ConfigHandler.instance().addConfigServer(modname, key, LangUtil.translateG(configname), defvalue);
    }

    @Override
    public void addConfig(String modname, String key, boolean defvalue) {
        ConfigHandler.instance().addConfig(modname, key, LangUtil.translateG("option." + key), defvalue);
    }

    @Override
    public void addConfigRemote(String modname, String key, boolean defvalue) {
        ConfigHandler.instance().addConfigServer(modname, key, LangUtil.translateG("option." + key), defvalue);
    }

    /* REGISTRATION METHODS */
    @Override
    public void registerHeadProvider(IWailaDataProvider dataProvider, Class block) {
        this.registerProvider(dataProvider, block, this.headBlockProviders);
    }

    @Override
    public void registerBodyProvider(IWailaDataProvider dataProvider, Class block) {
        this.registerProvider(dataProvider, block, this.bodyBlockProviders);
    }

    @Override
    public void registerTailProvider(IWailaDataProvider dataProvider, Class block) {
        this.registerProvider(dataProvider, block, this.tailBlockProviders);
    }

    @Override
    public void registerStackProvider(IWailaDataProvider dataProvider, Class block) {
        this.registerProvider(dataProvider, block, this.stackBlockProviders);
    }

    @Override
    public void registerNBTProvider(IWailaDataProvider dataProvider, Class entity) {
        this.registerProvider(dataProvider, entity, this.NBTDataProviders);
    }

    @Override
    public void registerHeadProvider(IWailaEntityProvider dataProvider, Class entity) {
        this.registerProvider(dataProvider, entity, this.headEntityProviders);
    }

    @Override
    public void registerBodyProvider(IWailaEntityProvider dataProvider, Class entity) {
        this.registerProvider(dataProvider, entity, this.bodyEntityProviders);
    }

    @Override
    public void registerTailProvider(IWailaEntityProvider dataProvider, Class entity) {
        this.registerProvider(dataProvider, entity, this.tailEntityProviders);
    }

    @Override
    public void registerNBTProvider(IWailaEntityProvider dataProvider, Class entity) {
        this.registerProvider(dataProvider, entity, this.NBTEntityProviders);
    }

    @Override
    public void registerHeadProvider(IWailaFMPProvider dataProvider, String name) {
        this.registerProvider(dataProvider, name, this.headFMPProviders);
    }

    @Override
    public void registerBodyProvider(IWailaFMPProvider dataProvider, String name) {
        this.registerProvider(dataProvider, name, this.bodyFMPProviders);
    }

    @Override
    public void registerTailProvider(IWailaFMPProvider dataProvider, String name) {
        this.registerProvider(dataProvider, name, this.tailFMPProviders);
    }

    @Override
    public void registerOverrideEntityProvider(IWailaEntityProvider dataProvider, Class entity) {
        this.registerProvider(dataProvider, entity, this.overrideEntityProviders);
    }

    /*
     * @Override public void registerShortDataProvider(IWailaSummaryProvider dataProvider, Class item) {
     * this.registerProvider(dataProvider, item, this.summaryProviders); }
     */

    @Override
    public void registerDecorator(IWailaBlockDecorator decorator, Class block) {
        this.registerProvider(decorator, block, this.blockClassDecorators);
    }

    @Override
    public void registerDecorator(IWailaFMPDecorator decorator, String name) {
        this.registerProvider(decorator, name, this.FMPClassDecorators);
    }

    private <T, V> void registerProvider(T dataProvider, V clazz, LinkedHashMap<V, ArrayList<T>> target) {
        if (clazz == null || dataProvider == null) throw new RuntimeException(
                String.format(
                        "Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method. [Provider : %s, Target : %s]",
                        dataProvider.getClass().getName(),
                        clazz));

        if (!target.containsKey(clazz)) target.put(clazz, new ArrayList<>());

        ArrayList<T> providers = target.get(clazz);
        if (providers.contains(dataProvider)) return;

        target.get(clazz).add(dataProvider);
    }

    @Deprecated
    @Override
    public void registerSyncedNBTKey(String key, Class target) {
        if (!this.syncedNBTKeys.containsKey(target)) this.syncedNBTKeys.put(target, new HashSet<>());

        this.syncedNBTKeys.get(target).add(key);
    }

    @Deprecated
    @Override
    public void registerTooltipRenderer(String name, IWailaTooltipRenderer renderer) {}

    /* PROVIDER GETTERS */

    public Map<Integer, List<IWailaDataProvider>> getHeadProviders(Object block) {
        return getProviders(block, this.headBlockProviders);
    }

    public Map<Integer, List<IWailaDataProvider>> getBodyProviders(Object block) {
        return getProviders(block, this.bodyBlockProviders);
    }

    public Map<Integer, List<IWailaDataProvider>> getTailProviders(Object block) {
        return getProviders(block, this.tailBlockProviders);
    }

    public Map<Integer, List<IWailaDataProvider>> getStackProviders(Object block) {
        return getProviders(block, this.stackBlockProviders);
    }

    public Map<Integer, List<IWailaDataProvider>> getNBTProviders(Object block) {
        return getProviders(block, this.NBTDataProviders);
    }

    public Map<Integer, List<IWailaEntityProvider>> getHeadEntityProviders(Object entity) {
        return getProviders(entity, this.headEntityProviders);
    }

    public Map<Integer, List<IWailaEntityProvider>> getBodyEntityProviders(Object entity) {
        return getProviders(entity, this.bodyEntityProviders);
    }

    public Map<Integer, List<IWailaEntityProvider>> getTailEntityProviders(Object entity) {
        return getProviders(entity, this.tailEntityProviders);
    }

    public Map<Integer, List<IWailaEntityProvider>> getOverrideEntityProviders(Object entity) {
        return getProviders(entity, this.overrideEntityProviders);
    }

    public Map<Integer, List<IWailaEntityProvider>> getNBTEntityProviders(Object entity) {
        return getProviders(entity, this.NBTEntityProviders);
    }

    public Map<Integer, List<IWailaFMPProvider>> getHeadFMPProviders(String name) {
        return getProviders(name, this.headFMPProviders);
    }

    public Map<Integer, List<IWailaFMPProvider>> getBodyFMPProviders(String name) {
        return getProviders(name, this.bodyFMPProviders);
    }

    public Map<Integer, List<IWailaFMPProvider>> getTailFMPProviders(String name) {
        return getProviders(name, this.tailFMPProviders);
    }

    public Map<Integer, List<IWailaBlockDecorator>> getBlockDecorators(Object block) {
        return getProviders(block, this.blockClassDecorators);
    }

    public Map<Integer, List<IWailaFMPDecorator>> getFMPDecorators(String name) {
        return getProviders(name, this.FMPClassDecorators);
    }

    @Deprecated
    public IWailaTooltipRenderer getTooltipRenderer(String name) {
        return null;
    }

    private <T> Map<Integer, List<T>> getProviders(Object obj, LinkedHashMap<Class, ArrayList<T>> target) {
        Map<Integer, List<T>> returnList = new TreeMap<>();
        Integer index = 0;

        for (Class clazz : target.keySet()) {
            if (clazz.isInstance(obj)) returnList.put(index, target.get(clazz));

            index++;
        }

        return returnList;
    }

    private <T> Map<Integer, List<T>> getProviders(String name, LinkedHashMap<String, ArrayList<T>> target) {
        Map<Integer, List<T>> returnList = new TreeMap<>();
        returnList.put(0, target.get(name));
        return returnList;
    }

    @Deprecated
    public HashSet<String> getSyncedNBTKeys(Object target) {
        HashSet<String> returnList = new HashSet<>();
        for (Class clazz : this.syncedNBTKeys.keySet())
            if (clazz.isInstance(target)) returnList.addAll(this.syncedNBTKeys.get(clazz));

        return returnList;
    }

    /* HAS METHODS */

    public boolean hasStackProviders(Object block) {
        return hasProviders(block, this.stackBlockProviders);
    }

    public boolean hasHeadProviders(Object block) {
        return hasProviders(block, this.headBlockProviders);
    }

    public boolean hasBodyProviders(Object block) {
        return hasProviders(block, this.bodyBlockProviders);
    }

    public boolean hasTailProviders(Object block) {
        return hasProviders(block, this.tailBlockProviders);
    }

    public boolean hasNBTProviders(Object block) {
        return hasProviders(block, this.NBTDataProviders);
    }

    public boolean hasHeadEntityProviders(Object entity) {
        return hasProviders(entity, this.headEntityProviders);
    }

    public boolean hasBodyEntityProviders(Object entity) {
        return hasProviders(entity, this.bodyEntityProviders);
    }

    public boolean hasTailEntityProviders(Object entity) {
        return hasProviders(entity, this.tailEntityProviders);
    }

    public boolean hasOverrideEntityProviders(Object entity) {
        return hasProviders(entity, this.overrideEntityProviders);
    }

    public boolean hasNBTEntityProviders(Object entity) {
        return hasProviders(entity, this.NBTEntityProviders);
    }

    public boolean hasHeadFMPProviders(String name) {
        return hasProviders(name, this.headFMPProviders);
    }

    public boolean hasBodyFMPProviders(String name) {
        return hasProviders(name, this.bodyFMPProviders);
    }

    public boolean hasTailFMPProviders(String name) {
        return hasProviders(name, this.tailFMPProviders);
    }

    public boolean hasBlockDecorator(Object block) {
        return hasProviders(block, this.blockClassDecorators);
    }

    public boolean hasFMPDecorator(String name) {
        return hasProviders(name, this.FMPClassDecorators);
    }

    private <T> boolean hasProviders(Object obj, LinkedHashMap<Class, ArrayList<T>> target) {
        for (Class clazz : target.keySet()) if (clazz.isInstance(obj)) return true;
        return false;
    }

    private <T> boolean hasProviders(String name, LinkedHashMap<String, ArrayList<T>> target) {
        return target.containsKey(name);
    }

    @Deprecated
    public boolean hasSyncedNBTKeys(Object target) {
        for (Class clazz : this.syncedNBTKeys.keySet()) if (clazz.isInstance(target)) return true;
        return false;
    }
}
