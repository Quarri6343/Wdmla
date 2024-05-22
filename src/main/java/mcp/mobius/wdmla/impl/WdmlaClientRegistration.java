package mcp.mobius.wdmla.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import mcp.mobius.wdmla.api.IBlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IWdmlaClientRegistration;

public class WdmlaClientRegistration implements IWdmlaClientRegistration {

    private static final WdmlaClientRegistration INSTANCE = new WdmlaClientRegistration();

    //We can't use HierarchyLookup in Java8
    private final LinkedHashMap<Class<?>, ArrayList<IComponentProvider<IBlockAccessor>>> dataProviders = new LinkedHashMap<>();
    //TODO: use Session

    public static WdmlaClientRegistration instance() {
        return INSTANCE;
    }

    public void registerBlockComponent(IComponentProvider<IBlockAccessor> provider, Class<?> clazz) {
        if (clazz == null || provider == null) {
            throw new RuntimeException(
                    "Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method.");
        }

        if (!dataProviders.containsKey(clazz)) {
            dataProviders.put(clazz, new ArrayList<>());
        }

        ArrayList<IComponentProvider<IBlockAccessor>> providers = dataProviders.get(clazz);
        if (providers.contains(provider)) {
            throw new RuntimeException("Trying to register the same provider to Wdmla twice !");
        }

        dataProviders.get(clazz).add(provider);
    }

    public boolean hasProviders(Object obj) {
        for (Class<?> clazz : dataProviders.keySet()) {
            if (clazz.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    public List<IComponentProvider<IBlockAccessor>> getProviders(Object instance) {
        List<IComponentProvider<IBlockAccessor>> returnList = new ArrayList<>();

        for (Class<?> clazz : dataProviders.keySet()) {
            if (clazz.isInstance(instance)) {
                returnList.addAll(dataProviders.get(clazz));
            }
        }

        return returnList;
    }
}
